package com.demo.tictactoe;

import java.util.Random;

public class Player {
	private final ActionFigure m_Figure; // Фигура, которой будет играть игрок.
	private final String m_Name; // Имя игрока.
	private final Random m_Random = new Random(); // Генератор случайных чисел.

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

	/**
	 * Выполнить ход.
	 * @param aBoard - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 */
	public Move turn(Board aBoard)
	{
		final int boardXSize = aBoard.getXSize();
		final int boardYSize = aBoard.getYSize();
		Move result = null;
		if (aBoard.hasMoreSpace())
		{
			int x = m_Random.nextInt(boardXSize);
			int y = m_Random.nextInt(boardYSize);
			if (aBoard.lookAt(x, y) != null)
			{
				// Если случайно не попали в пустую клетку, то ставим в первую свободную.
				boolean found = false;
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
			result = new Move(x, y, m_Figure);
		}
		return result;
	}

	@Override
	public String toString()
	{
		return m_Name;
	}
}
