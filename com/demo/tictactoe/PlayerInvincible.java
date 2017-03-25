package com.demo.tictactoe;

import java.util.ArrayList;
import java.util.Map;

/**
 * Описывает игрока, который применяет безпроигрышную стратегию.
 * @author Hobbit Jedi
 */
public class PlayerInvincible extends Player {

	/**
	 * Дерево ходов игры.
	 * Хранит возможные хода игры с оценками возможности победы того или иного игрока на каждом ходу.
	 */
	protected static GameTreeNode mGameTree;
	
	private GameTreeNode mCurrentNode; // Узел дерева ходов игры, соответствующий текущей игровой ситуации.
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerInvincible(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
		mGameTree = null;
		mCurrentNode = mGameTree;
	}

	/**
	 * Ознакомиться с правилами.
	 * @param aRules - Правила, по которым будет вестись игра.
	 */
	@Override
	public void checkOutRules(Rules aRules) {
		super.checkOutRules(aRules);
		if (mGameTree == null || !mRules.equals(aRules))
		{
			mGameTree = new GameTreeNode(new Board(aRules.getBoardXSize(), aRules.getBoardYSize()), new Rules(aRules), null, -1);
		}
		mCurrentNode = mGameTree;
	}
	
	/**
	 * Принять оповещение о сделанном в игре ходе.
	 * @param aMove - Ход, который сделан во время игры.
	 * @param aMoveResult - Результат сделанного хода.
	 * @param aBoard - Состояние игровой доски после выполнения хода.
	 * @param aPlayers - Массив еще оставшихся в игре игроков.
	 */
	@Override
	public void moveNotificationHandler(Move aMove, MoveResult aMoveResult, Board aBoard, Player[] aPlayers)
	{
		super.moveNotificationHandler(aMove, aMoveResult, aBoard, aPlayers);
		if (aMoveResult != null)
		{
			switch (aMoveResult)
			{
				case CONTINUE:
					mCurrentNode = mCurrentNode.getMovesMap().get(aMove);
					break;
				case DISQUALIFICATION:
					int prevPlayerIndex = -1;
					for (Player player: aPlayers)
					{
						if (this.equals(player))
						{
							break;
						}
						if (player != null)
						{
							prevPlayerIndex++;
						}
					}
					mCurrentNode = new GameTreeNode(aBoard, mRules, aMove, prevPlayerIndex);
					break;
			}
		}
	}
	
	/**
	 * Выполнить ход.
	 * @param aBoard - Слепок текущей ситуации на игровом поле.
	 * @return - Ход, который собирается делать игрок.
	 */
	@Override
	public Move makeMove(Board aBoard)
	{
		Move result = null;
		ArrayList<Map.Entry<Move, GameTreeNode>> bestEntries = new ArrayList<>();
		double bestWeight = Double.NEGATIVE_INFINITY;
		for (Map.Entry<Move, GameTreeNode> entry: mCurrentNode.getMovesMap().entrySet())
		{
			if (bestEntries.isEmpty())
			{
				bestEntries.add(entry);
				bestWeight = entry.getValue().getPositionWeight(mFigure);
			}
			else
			{
				double currentWeight = entry.getValue().getPositionWeight(mFigure);
				if (bestWeight == currentWeight)
				{
					bestEntries.add(entry);
				}
				else if (bestWeight < currentWeight)
				{
					bestEntries.clear();
					bestEntries.add(entry);
					bestWeight = entry.getValue().getPositionWeight(mFigure);
				}
			}
		}
		if (!bestEntries.isEmpty())
		{
			int moveIndex = (int)(Math.random()*bestEntries.size());
			result = bestEntries.get(moveIndex).getKey();
		}
		return result;
	}
}
