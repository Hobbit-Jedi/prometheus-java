package com.demo.tictactoe;

/**
 * Описывает ход игрока.
 * @author Hobbit Jedi
 */
public class Move {
	private final Coordinates mCoordinates; // Координаты, в которые делается ход.
	private final Player mPlayer;           // Игрок, который делает ход.
	
	/**
	 * Создать ход по указанным по одтельности координатам.
	 * @param aX - X-координата, в которую делается ход.
	 * @param aY - Y-координата, в которую делается ход.
	 * @param aPlayer - Игрок, который делает ход.
	 */
	public Move(int aX, int aY, Player aPlayer)
	{
		mCoordinates = new Coordinates(aX, aY);
		mPlayer = aPlayer;
	}

	/**
	 * Создать ход по указанному объекту координат.
	 * @param aCoordinates - Координаты, в которые делается ход.
	 * @param aPlayer - Игрок, который делает ход.
	 */
	public Move(Coordinates aCoordinates, Player aPlayer)
	{
		mCoordinates = aCoordinates;
		mPlayer = aPlayer;
	}

	/**
	 * Получить x-координату хода.
	 * @return - x-координата (числовая) хода.
	 */
	public int getX()
	{
		return mCoordinates.getX();
	}

	/**
	 * Получить y-координату хода.
	 * @return - y-координата (числовая) хода.
	 */
	public int getY()
	{
		return mCoordinates.getY();
	}

	/**
	 * Получить фигуру хода.
	 * @return - Фигура, которой сделан ход.
	 */
	public ActionFigure getFigure()
	{
		return mPlayer.getFigure();
	}
	
	/**
	 * Получить походившего игрока.
	 * @return - Игрок, который сделал ход.
	 */
	public Player getPlayer()
	{
		return mPlayer;
	}

	/**
	 * Получить строковое представление хода.
	 * @return - Строковое представление хода.
	 */
	@Override
	public String toString()
	{
		return new StringBuilder(getFigure().toString()).append(" -> (").append(mCoordinates).append(")").toString();
	}
}
