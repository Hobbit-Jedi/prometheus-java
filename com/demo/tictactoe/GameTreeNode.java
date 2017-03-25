package com.demo.tictactoe;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Описывает узел дерева игры.
 * Дерево игры содержит все возможные в игре хода,
 * с оценкой вероятности победы каждого игрока,
 * в результате хода, который привел к данному узлу дерева.
 * Используется для определения стратегий ведения игры.
 * @author Hobbit Jedi
 */
public class GameTreeNode {
	private final boolean mIsGameOver;                       // Признак того, что данный узел игрового дерева является конечным (не имеет дочерних).
	private final EnumMap<ActionFigure, Double> mPositions;  // Оценки позиций каждой фигуры для данного узла дерева игры
	                                                         // (для каждой фигуры хранится победный весовой коэффициент комбинаций в иерархии данного узла).
	private final HashMap<Move, GameTreeNode> mMoves;        // Возможные ходы и порождаемые ими дочерние узлы дерева игры.
	
	/**
	 * Создает новый узел дерева игры.
	 * @param aBoard - Состояние игрового поля, которое будет в игре на момент создаваемого узла игрового дерева.
	 * @param aRules - Правила, по которым ведется игра.
	 * @param aMove - Ход, который привел к этому состоянию игры.
	 *                При создании корневого узла дерева игры = null.
	 * @param aMovingPlayerIndex - Индекс игрока, который сделал данный ход.
	 *                             При создании корневого узла дерева игры = -1.
	 */
	public GameTreeNode(Board aBoard, Rules aRules, Move aMove, int aMovingPlayerIndex)
	{
		mPositions = new EnumMap<>(ActionFigure.class);
		mMoves = new HashMap<>();
		if (aMove != null && aRules.isWin(aMove, aBoard))
		{
			mIsGameOver = true;
			mPositions.put(aMove.getFigure(), Double.POSITIVE_INFINITY);
		}
		else if (aBoard.hasMoreSpace())
		{
			mIsGameOver = false;
			int nextMovingPlayerIndex = aMovingPlayerIndex < aRules.getNumOfPlayers()-1
				?
				aMovingPlayerIndex + 1
				:
				0
				;
			Player moovingPlayer = aRules.getPlayers()[nextMovingPlayerIndex];
			final int boardXSize = aBoard.getXSize();
			final int boardYSize = aBoard.getYSize();

			for (int y = 0; y < boardYSize; y++)
			{
				for (int x = 0; x < boardXSize; x++)
				{
					if (aBoard.lookAt(x, y) == null)
					{
						// Создадим новый дочерний узел игрового дерева.
						Move move = new Move(x, y, moovingPlayer);
						Board newBoard = new Board(aBoard);
						newBoard.setAt(move.getCoordinates(), moovingPlayer.getFigure());
						GameTreeNode newGameTreeNode = new GameTreeNode(newBoard, aRules, move, nextMovingPlayerIndex);
						mMoves.put(move, newGameTreeNode);
						// Акуммулируем в текущем узле игрового дерева счетчики побед дочерих узлов.
						for (Map.Entry<ActionFigure, Double> entry: newGameTreeNode.mPositions.entrySet())
						{
							ActionFigure entryKey = entry.getKey();
							Double entryValue = entry.getValue();
							Double currValue = mPositions.getOrDefault(entryKey, 0.0);
							mPositions.put(entryKey, currValue + (entryValue.isInfinite() ? 1000000000.0 : entryValue)/10);
						}
					}
				}
			}
		}
		else
		{
			mIsGameOver = true;
			// НИЧЬЯ: Карту счетчиков побед оставляем пустой.
		}
	}
	
	public HashMap<Move, GameTreeNode> getMovesMap()
	{
		return (HashMap<Move, GameTreeNode>) mMoves.clone();
	}
	
	public double getPositionWeight(ActionFigure aFigure)
	{
		double result = 0.0;
		for (Map.Entry<ActionFigure, Double> entry: mPositions.entrySet())
		{
			result = result + (entry.getKey() == aFigure ? 1 : -1 ) * entry.getValue();
		}
		return result;
	}
	
	/**
	 * Получить строковое представление узла дерева игры.
	 * @return - Строковое представление узла дерева игры.
	 */
	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		if (mIsGameOver)
		{
			result.append("КОНЕЦ!");
		}
		// Отобразим победные коэффициенты для каждой фигуры.
		if (mPositions.size() > 0)
		{
			result.append(" Победы:");
			for (Map.Entry<ActionFigure, Double> entry: mPositions.entrySet())
			{
				result.append(" ");
				result.append(entry.getKey());
				result.append("(");
				result.append(entry.getValue());
				result.append(")");
			}
			// Пересчитаем победные коэффициенты в весовой коэффициент позиции с точки зрения каждой фигуры.
			EnumMap<ActionFigure, Double> calculatedPositons = new EnumMap<>(ActionFigure.class);
			for (ActionFigure figure: ActionFigure.values())
			{
				calculatedPositons.put(figure, getPositionWeight(figure));
			}
			// Выведем получившиеся весовые коэффициенты позиции с точки зрения каждой фигуры.
			result.append(" Веса:");
			for (Map.Entry<ActionFigure, Double> entry: calculatedPositons.entrySet())
			{
				result.append(" ");
				result.append(entry.getKey());
				result.append("(");
				result.append(entry.getValue());
				result.append(")");
			}
		}
		return result.toString();
	}
	
}
