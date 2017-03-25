package com.demo.tictactoe;

import java.util.Objects;

/**
 * Описывает Игрока в целом (как абстрактную сущность).
 * Набор его интерфейсных методов и хранимых полей.
 * Конкретные реализации Игрока должны реализовать метод выполнения хода.
 * @author Hobbit Jedi
 */
public abstract class Player {
	protected final ActionFigure mFigure; // Фигура, которой будет играть игрок.
	protected final String mName;         // Имя игрока.
	protected Rules mRules;               // Правила, по которым ведется игра.
	
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
	 * Проверить совпадает ли данный игрок с другим игроком.
	 * @param obj - Игрок, с которым выполняется сравнение текущего игрока.
	 * @return - Признак того, что указанный игрок совпадает с текущим игроком.
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
		final Player other = (Player) obj;
		boolean result = (this.mFigure == other.mFigure) && this.mName.equals(other.mName);
		return result;
	}
	
	/**
	 * Вычислить хэш-код объекта.
	 * @return - хэш-код объекта.
	 */
	@Override
	public int hashCode()
	{
		int hash = mName.hashCode() + Objects.hashCode(mFigure);
		return hash;
	}
	
	/**
	 * Ознакомиться с правилами.
	 * @param aRules - Правила, по которым будет вестись игра.
	 */
	public void checkOutRules(Rules aRules)
	{
		mRules = aRules;
	}
	
	/**
	 * Принять оповещение о сделанном в игре ходе.
	 * @param aMove - Ход, который сделан во время игры.
	 */
	public void moveNotificationHandler(Move aMove)
	{
		// Реализация по умолчанию игнорирует информацию о проделанных ходах.
	}
	
	/**
	 * Выполнить ход.
	 * @param aBoard - Слепок текущей ситуации на игровом поле.
	 * @return - Ход, который собирается делать игрок.
	 */
	abstract public Move makeMove(Board aBoard);
}
