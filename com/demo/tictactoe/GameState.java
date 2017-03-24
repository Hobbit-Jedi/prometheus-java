package com.demo.tictactoe;

import java.util.EnumMap;
import java.util.Map;
import java.util.HashMap;

/**
 * Описывает состояние игры в тот или иной момент времени.
 * Используется для определения стратегий ведения игры.
 * @author Hobbit Jedi
 */
public class GameState {
	private final Board mBoard;                             // Слепок расположения фигур на доске в данном состоянии игры.
	private final Map<ActionFigure, Integer> mWinsCounters; // Соответствие счетчиков побед каждой фигуры для данного состояния.
	private final Map<Move, GameState> mChilds;             // Соответствие дочерних состояний с ведущими к ним ходами.
	
	public GameState(Board aBoard)
	{
		mWinsCounters = new EnumMap<>(ActionFigure.class);
		mChilds = new HashMap<>();
		mBoard = aBoard;
	}
	
	public void growTree(Rules aRules, int aMovingPlayerIndex)
	{
		Player moovingPlayer = aRules.getPlayers()[aMovingPlayerIndex];
		final int boardXSize = mBoard.getXSize();
		final int boardYSize = mBoard.getYSize();
		
		if (mBoard.hasMoreSpace())
		{
			for (int y = 0; y < boardYSize; y++)
			{
				for (int x = 0; x < boardXSize; x++)
				{
					if (mBoard.lookAt(x, y) == null)
					{
						Move move = new Move(x, y, moovingPlayer);
						Board newBoard = new Board(mBoard);
						newBoard.setAt(move.getCoordinates(), moovingPlayer.getFigure());
						GameState newGameState = new GameState(newBoard);
						mChilds.put(move, newGameState);
						//TODO: Просуммировать счетчики побед из дочерних состояний в текущее.
					}
				}
			}
		}
	}
	
}
