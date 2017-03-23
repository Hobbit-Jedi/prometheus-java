package com.demo.tictactoe;

/**
 * Описывает игрока, который применяет безпроигрышную стратегию.
 * @author Hobbit Jedi
 */
public class PlayerInvincible extends Player {
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerInvincible(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
	}

	/**
	 * Выполнить ход.
	 * @param aBoard - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 */
	@Override
	public Move turn(Board aBoard)
	{
		//TODO: реализовать.
		Move result = new Move(0,0, m_Figure); //TODO: убрать эту отладочную заглушку.
		return result;
	}
}
