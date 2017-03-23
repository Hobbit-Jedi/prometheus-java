package com.demo.tictactoe;

/**
 * Описывает Игрока в целом (как абстрактную сущность).
 * Набор его интерфейсных методов и хранимых полей.
 * Конкретные реализации Игрока должны реализовать метод выполнения хода.
 * @author Hobbit Jedi
 */
public abstract class Player {
	protected final ActionFigure mFigure; // Фигура, которой будет играть игрок.
	protected final String mName;         // Имя игрока.

	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public Player(String aName, ActionFigure aFigure)
	{
		mName   = aName;
		mFigure = aFigure;
	}
	
	/**
	 * Получить фигуру, которой ходит игрок.
	 * @return - Фигура, которой ходит игрок.
	 */
	public ActionFigure getFigure()
	{
		return mFigure;
	}
	
	/**
	 * Получить строковое представление игрока.
	 * @return - Строковое представление игрока.
	 */
	@Override
	public String toString()
	{
		return mName;
	}
	
	/**
	 * Выполнить ход.
	 * @param aBoard - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 */
	abstract public Move turn(Board aBoard);
}
