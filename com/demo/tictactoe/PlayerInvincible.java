package com.demo.tictactoe;

/**
 * Описывает игрока, который применяет безпроигрышную стратегию.
 * @author Hobbit Jedi
 */
public class PlayerInvincible extends Player {

	GameState mRootState; // Дерево ходов игры.
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerInvincible(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
		mRootState = null;
	}

	/**
	 * Ознакомиться с правилами.
	 * @param aRules - Правила, по которым будет вестись игра.
	 */
	@Override
	public void checkOutRules(Rules aRules) {
		super.checkOutRules(aRules);
		if (mRootState == null || !mRules.equals(aRules))
		{
			mRootState = new GameState(new Board(aRules.getBoardXSize(), aRules.getBoardYSize()));
			mRootState.growTree(aRules, 0);
		}
	}
	
	/**
	 * Выполнить ход.
	 * @param aBoard - Слепок текущей ситуации на игровом поле.
	 * @return - Ход, который собирается делать игрок.
	 */
	@Override
	public Move turn(Board aBoard)
	{
		//TODO: реализовать.
		Move result = new Move(0,0, this); //TODO: убрать эту отладочную заглушку.
		return result;
	}
}
