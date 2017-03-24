package com.demo.tictactoe;

import java.util.Arrays;

/**
 * Описывает правила, по которым ведется игра.
 * @author Hobbit Jedi
 */
public class Rules {
	private final int mBoardXSize;       // Ширина доски.
	private final int mBoardYSize;       // Высота доски.
	private final int mWinLineLength;    // Длина линии из одинаковых фигур для победы.
	private final int mNumErrorsAllowed; // Допустимое количество ошибок до дисквалификации.
	private final int mNumOfPlayers;     // Количество играющих игроков.
	private final Player[] mPlayers;     // Порядок хода игроков.
	
	/**
	 * Создать правила по умолчанию.
	 * Классические крестики-нолики.
	 * Поле 3х3.
	 * Для победы нужно поставить три фигуры в ряд.
	 * Играют два игрока.
	 * Разрешено 10 попыток подряд некорректно походить.
	 */
	public Rules()
	{
		this(3, 3, 3, 10, 2);
	}
	
	/**
	 * Создать правила игры.
	 * @param aBoardXSize - Ширина игровой доски.
	 * @param aBoardYSize - Высота игровой доски.
	 * @param aWinLineLength - Количество фигур в линии, чтобы одержать победу.
	 * @param aNumErrorsAllowed - Допустимое количество попыток подряд сделать некорректный ход до получения дисквалификации.
	 * @param aNumOfPlayers - Количество играющих игроков.
	 */
	public Rules(int aBoardXSize, int aBoardYSize, int aWinLineLength, int aNumErrorsAllowed, int aNumOfPlayers)
	{
		mBoardXSize       = aBoardXSize;
		mBoardYSize       = aBoardYSize;
		mWinLineLength    = aWinLineLength;
		mNumErrorsAllowed = aNumErrorsAllowed;
		mNumOfPlayers     = aNumOfPlayers;
		mPlayers = new Player[aNumOfPlayers];
	}
	
	/**
	 * Скопировать правила игры.
	 * @param aRules - Правила игры с которых создается копия.
	 */
	public Rules(Rules aRules)
	{
		this(aRules.getBoardXSize(), aRules.getBoardYSize(), aRules.getWinLineLength(), aRules.getNumErrorsAllowed(), aRules.getNumOfPlayers());
		Player[] rulesPlayers = aRules.getPlayers();
		System.arraycopy(rulesPlayers, 0, mPlayers, 0, rulesPlayers.length);
	}
	
	/**
	 * Получить ширину игровой доски.
	 * @return - Ширина игровой доски.
	 */
	public int getBoardXSize()
	{
		return mBoardXSize;
	}
	
	/**
	 * Получить высоту игровой доски.
	 * @return - Высота игровой доски.
	 */
	public int getBoardYSize()
	{
		return mBoardYSize;
	}
	
	/**
	 * Получить длину линии для победы.
	 * @return - Длина линии из одинаковых фигур для победы.
	 */
	public int getWinLineLength()
	{
		return mWinLineLength;
	}
	
	/**
	 * Получить допустимое количество попыток некорректно походить до того,
	 * как игрок будет дисквалифицирован.
	 * @return - Допустимое количество ошибок до дисквалификации.
	 */
	public int getNumErrorsAllowed()
	{
		return mNumErrorsAllowed;
	}
	
	/**
	 * Получить количество игроков.
	 * @return - Количество игроков.
	 */
	public int getNumOfPlayers()
	{
		return mNumOfPlayers;
	}
	
	/**
	 * Получить массив участвующих в игре игроков.
	 * @return - Ссылка на массив игроков.
	 */
	public Player[] getPlayers()
	{
		return mPlayers;
	}
	
	/**
	 * Проверить совпадают ли данные правила игры с другими правилами игры.
	 * @param obj - Правила игры, с которыми выполняется сравнение текущих правил игры.
	 * @return - Признак того, что указанные правила игры совпадают с текущими правилами игры.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Rules other = (Rules) obj;
		boolean result	= (this.mBoardXSize == other.mBoardXSize)
						&& (this.mBoardYSize == other.mBoardYSize)
						&& (this.mWinLineLength == other.mWinLineLength)
						&& (this.mNumErrorsAllowed == other.mNumErrorsAllowed)
						&& (this.mNumOfPlayers == other.mNumOfPlayers)
						&& (Arrays.deepEquals(this.mPlayers, other.mPlayers))
						;
		return result;
	}
	
	/**
	 * Вычислить хэш-код объекта.
	 * @return - хэш-код объекта.
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 53 * hash + mBoardXSize;
		hash = 53 * hash + mBoardYSize;
		hash = 53 * hash + mWinLineLength;
		hash = 53 * hash + mNumErrorsAllowed;
		hash = 53 * hash + mNumOfPlayers;
		hash = 53 * hash + Arrays.deepHashCode(mPlayers);
		return hash;
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
					int directionLength = mWinLineLength;
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
		result = (maxLine >= mWinLineLength);
		
		return result;
	}

}
