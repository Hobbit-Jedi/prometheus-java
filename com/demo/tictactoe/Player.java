package com.demo.tictactoe;

/**
 * Описывает Игрока в целом (как абстрактную сущность).
 * Набор его интерфейсных методов и хранимых полей.
 * Конкретные реализации Игрока должны реализовать метод выполнения хода.
 * @author Hobbit Jedi
 */
public abstract class Player {
	protected final ActionFigure m_Figure; // Фигура, которой будет играть игрок.
	protected final String m_Name;         // Имя игрока.

	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public Player(String aName, ActionFigure aFigure)
	{
		m_Name   = aName;
		m_Figure = aFigure;
	}

	@Override
	public String toString()
	{
		return m_Name;
	}
	
	/**
	 * Выполнить ход.
	 * @param aBoard - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 */
	abstract public Move turn(Board aBoard);
}
