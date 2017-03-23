package com.demo.tictactoe;

import java.util.Random;

/**
 * Описывает Игрока, который ходит, в основном, случайным образом.
 * @author Hobbit Jedi
 */
public class PlayerRandom extends Player {
	private final Random m_Random; // Генератор случайных чисел.
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerRandom(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
		m_Random = new Random();
	}

	/**
	 * Выполнить ход.
	 * @param aBoard - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 */
	@Override
	public Move turn(Board aBoard)
	{
		final int boardXSize = aBoard.getXSize();
		final int boardYSize = aBoard.getYSize();
		Move result = null;
		if (aBoard.hasMoreSpace())
		{
			int x;
			int y;
			boolean found = false;
			int numOfRandomTries = 10;
			do {				
				x = m_Random.nextInt(boardXSize);
				y = m_Random.nextInt(boardYSize);
				found = (aBoard.lookAt(x, y) == null);
			} while (!found && --numOfRandomTries > 0);
			if (!found)
			{
				// Если испробовали все попытки попасть в пустую клетку случайно,
				// то ходим в первую свободную.
				for (y = 0; y < boardYSize; y++)
				{
					for (x = 0; x < boardXSize; x++)
					{
						if (aBoard.lookAt(x, y) == null)
						{
							found = true;
							break;
						}
					}
					if (found) break;
				}
			}
			result = new Move(x, y, this);
		}
		return result;
	}
}
