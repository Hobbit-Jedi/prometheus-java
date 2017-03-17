package com.demo.tictactoe;

import java.util.Random;

public class Player {
	private final ActionFigure m_figure; // Фигура, которой будет играть игрок.
	private final String m_Name; // Имя игрока.
	private final Random m_rnd = new Random(); // Генератор случайных чисел.

	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param figure - Фигура, которой будет играть игрок.
	 */
	public Player(String aName, ActionFigure figure)
	{
		m_Name = aName;
		m_figure = figure;
	}

	/**
	 * Выполнить ход.
	 * @param board - Игровое поле, на котором идет игра.
	 * @return - Ход, который собирается делать игрок.
	 * @throws Exception - Если игрок, в процессе выбора хода, попытается
	 *                     посмотреть за пределы доски, то вызывает исключение.
	 */
	public Move turn(Board board) throws Exception
	{
		Move result = null;
		if (board.hasMoreSpace())
		{
			int x = m_rnd.nextInt(board.getXSize());
			int y = m_rnd.nextInt(board.getYSize());
			if (board.lookAt(x, y) != null)
			{
				// Если случайно не попали в пустую клетку, то ставим в первую свободную.
				outer:
				for (y = 0; y < board.getYSize(); y++)
					for (x = 0; x < board.getXSize(); x++)
					{
						if (board.lookAt(x, y) == null) break outer;
					}
			}
			result = new Move(x, y, m_figure);
		}
		return result;
	}

	@Override
	public String toString()
	{
		return m_Name;
	}
}
