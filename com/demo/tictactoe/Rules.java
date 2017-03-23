package com.demo.tictactoe;

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
	
	public Rules()
	{
		this(3, 3, 3, 10, 2);
	}
	
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
	
}
