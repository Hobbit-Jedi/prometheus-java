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
	 * Получить координаты хода.
	 * @return - Координаты хода.
	 */
	public Coordinates getCoordinates()
	{
		return mCoordinates;
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
	
	/**
	 * Проверить совпадает ли данный ход с другим ходом.
	 * @param obj - Ход, с которым выполняется сравнение текущего хода.
	 * @return - Признак того, что указанный ход совпадает с текущим ходом.
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
		final Move other = (Move) obj;
		boolean result = this.mCoordinates.equals(other.mCoordinates) && this.mPlayer.equals(other.mPlayer);
		return result;
	}
	
	/**
	 * Вычислить хэш-код объекта.
	 * @return - хэш-код объекта.
	 */
	@Override
	public int hashCode()
	{
		int hash = (mCoordinates.hashCode()<<16) + (mPlayer.hashCode() & 0xFFFF);
		return hash;
	}
	
}
