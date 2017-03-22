package com.demo.tictactoe;

public class Referee {
	private final int m_NumToLineForWin;    // Количество фигур в линию для победы.
	private final int m_MaxBadTurnsAllowed; // Допустимое количество попыток неправильно походить до дисквалификации игрока (<0 - неограничено).
	private Player m_LastPlayer;            // Игрок, который ходил последним. Хранится между ходами.
	private int m_PlayerTurnTriesCounter;   // Счетчик попыток хода игрока. Если игрок тупит и долго не может сделать правильный ход, то его дисквалифицируют.
	
	/**
	 * Создает судью, который будет следить за выполнением правил игры.
	 * @param aNumToLineForWin - Количество фигур, которые нужно выстроить в линию, чтобы выиграть.
	 * @param aMaxBadTurnsAllowed - Количество попыток некорректно походить, до дисквалификации игрока.
	 */
	public Referee(int aNumToLineForWin, int aMaxBadTurnsAllowed)
	{
		m_NumToLineForWin = aNumToLineForWin;
		m_MaxBadTurnsAllowed = aMaxBadTurnsAllowed;
		m_LastPlayer = null;
		m_PlayerTurnTriesCounter = 0;
	}
	
	/**
	 * Выполняет проверку хода игрока, и если ход корректный, то фиксирует его на доске.
	 * После чего проверяет результат этого хода.
	 * @param aPlayer - Игрок, который совершает ход.
	 * @param aMove   - Проверяемый ход.
	 * @param aBoard  - Доска, к которой будет применен ход.
	 * @return - Результат выполнения хода.
	 *           Если ход недопустим, то возвращает null.
	 */
	public MoveResult commitMove(Player aPlayer, Move aMove, Board aBoard)
	{
		MoveResult result = null;
		if (aPlayer != m_LastPlayer)
		{
			m_LastPlayer = aPlayer;
			m_PlayerTurnTriesCounter = 1;
		}
		else
		{
			m_PlayerTurnTriesCounter++;
		}
		if (aMove != null)
		{
			// Проверим допустимость хода.
			int x = aMove.getX();
			int y = aMove.getY();
			if (x >= 0 && x < aBoard.getXSize() && y >= 0 && y < aBoard.getXSize())
			{
				ActionFigure currentFigure = aMove.getFigure();
				if (aBoard.lookAt(x, y) == null)
				{
					// Если ход допустим, то отметим его на доске.
					aBoard.setAt(x, y, currentFigure);
					// Определим результат хода.
					if (isWin(aMove, aBoard))
					{
						result = MoveResult.WIN;
					}
					else if (aBoard.hasMoreSpace())
					{
						result = MoveResult.CONTINUE;
					}
					else
					{
						result = MoveResult.DEADLOCK;
					}
				}
			}
			else
			{
				System.out.println("Игрок " + aPlayer + " некорректно указал координаты хода: " + aMove);
			}
		}
		else
		{
			if (!aBoard.hasMoreSpace())
			{
				result = MoveResult.DEADLOCK;
			}
			else
			{
				System.out.println("Игрок " + aPlayer + " не знает куда ему пойти.");
			}
		}
		if (result == null && m_MaxBadTurnsAllowed >= 0 && m_PlayerTurnTriesCounter >= m_MaxBadTurnsAllowed)
		{
			result = MoveResult.DISQUALIFICATION;
		}
		return result;
	}
	
	/**
	 * Проверяет принес ли ход победу тому, кто ходил.
	 * @param aLastMove - Последний ход на доске.
	 * @param aBoard - Доска, на которой ведется игра.
	 * @return - Признак того, что последний ход оказался победным.
	 */
	public boolean isWin(Move aLastMove, Board aBoard)
	{
		boolean result;
		int xSize = aBoard.getXSize();
		int ySize = aBoard.getYSize();
		int lastMoveX = aLastMove.getX();
		int lastMoveY = aLastMove.getY();
		ActionFigure lastMoveFigure = aLastMove.getFigure();
		
		// Посчитаем сколько соответствующих фигуре хода фигур в разных направлениях от точки хода.
		int directions[][] =	{	{0, 0, 0},
									{0, 1, 0},
									{0, 0, 0}
								};
		for (int dx = -1; dx <= 1; dx++) // Направление движения по оси X (-1 - Влево, 0 - Стоим, +1 - Вправо)
		{
			for (int dy = -1; dy <= 1; dy++) // Направление движения по оси Y (-1 - Вверх, 0 - Стоим, +1 - Вниз)
			{
				if (dx != 0 || dy != 0) // Центральную клетку не изменяем.
				{
					int directionLength = m_NumToLineForWin;
					switch (dx)
					{
						case -1:
							directionLength = Math.min(directionLength, lastMoveX);
							break;
						case 1:
							directionLength = Math.min(directionLength, Math.max(xSize - lastMoveX - 1, 0));
							break;
					}
					switch (dy)
					{
						case -1:
							directionLength = Math.min(directionLength, lastMoveY);
							break;
						case 1:
							directionLength = Math.min(directionLength, Math.max(ySize - lastMoveY - 1, 0));
							break;
					}
					for (int k = 1; k <= directionLength; k++)
					{
						if (aBoard.lookAt(lastMoveX + dx * k, lastMoveY + dy * k) != lastMoveFigure)
						{
							break;
						}
						directions[dy+1][dx+1]++;
					}
				}
			}
		}
		
		// Проверим есть ли выигрышные строки, колонки или диагонали.
		int figuresInRow = 0;
		int figuresInColumn = 0;
		int figuresInDiagonal1 = 0;
		int figuresInDiagonal2 = 0;
		for (int i = 0; i <= 2; i++)
		{
			figuresInRow       += directions[1][i];
			figuresInColumn    += directions[i][1];
			figuresInDiagonal1 += directions[i][i];
			figuresInDiagonal2 += directions[i][2-i];
		}
		int maxLine = Math.max(figuresInRow, figuresInColumn);
		maxLine = Math.max(maxLine, figuresInDiagonal1);
		maxLine = Math.max(maxLine, figuresInDiagonal2);
		result = (maxLine >= m_NumToLineForWin);
		
		return result;
	}

}
