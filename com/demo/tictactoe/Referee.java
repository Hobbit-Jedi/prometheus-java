package com.demo.tictactoe;

/**
 * Описывает судью, следящего за выполнением правил игры.
 * @author Hobbit Jedi
 */
public class Referee {
	private final Rules mRules;          // Правила, по которым проводится игра.
	private Player mLastPlayer;          // Игрок, который ходил последним. Хранится между ходами.
	private int mPlayerTurnTriesCounter; // Счетчик попыток хода игрока. Если игрок тупит и долго не может сделать правильный ход, то его дисквалифицируют.
	
	/**
	 * Создает судью, который будет следить за выполнением правил игры.
	 * @param aRules - Правила, по которым проводится игра.
	 */
	public Referee(Rules aRules)
	{
		mRules = aRules;
		mLastPlayer = null;
		mPlayerTurnTriesCounter = 0;
	}
	
	/**
	 * Выполняет проверку хода игрока, и если ход корректный, то фиксирует его на доске.
	 * После чего проверяет результат этого хода.
	 * @param aPlayer - Игрок, который совершает ход.
	 * @param aMove   - Проверяемый ход.
	 * @param aBoard  - Доска, к которой будет применен ход.
	 * @param aPlayers - Еще оставшиеся в игре игроки.
	 * @return - Результат выполнения хода.
	 *           Если ход недопустим, то возвращает null.
	 */
	public MoveResult commitMove(Player aPlayer, Move aMove, Board aBoard, Player[] aPlayers)
	{
		MoveResult result = null;
		if (aPlayer != mLastPlayer)
		{
			mLastPlayer = aPlayer;
			mPlayerTurnTriesCounter = 1;
		}
		else
		{
			mPlayerTurnTriesCounter++;
		}
		if (aMove != null)
		{
			// Проверим допустимость хода.
			Coordinates cellToMove = aMove.getCoordinates();
			if (aBoard.isCoordinateAtBoard(cellToMove))
			{
				ActionFigure figure = aBoard.lookAt(cellToMove);
				if (figure == null)
				{
					// Если ход допустим, то отметим его на доске.
					aBoard.setAt(cellToMove, aMove.getFigure());
					// Определим результат хода.
					if (mRules.isWin(aMove, aBoard))
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
					System.out.println("Ход игрока " + aPlayer + " принят: " + aMove);
				}
				else
				{
					System.out.println("Указанная игроком " + aPlayer + " клеточка (" + aMove.getCoordinates() + ") уже занята фигурой " + figure);
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
		if (result == null && mRules.getNumErrorsAllowed() >= 0 && mPlayerTurnTriesCounter > mRules.getNumErrorsAllowed())
		{
			result = MoveResult.DISQUALIFICATION;
			for (int i = 0; i < aPlayers.length; i++)
			{
				if (aPlayers[i] == aPlayer)
				{
					aPlayers[i] = null;
					break;
				}
			}
		}
		// Оповестим всех игроков о проделанном ходе.
		for (Player player: mRules.getPlayers())
		{
			player.moveNotificationHandler(aMove, result, new Board(aBoard), aPlayers.clone());
		}
		return result;
	}
	
}
