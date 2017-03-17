package com.demo.tictactoe;

public class Referee {
	private final int m_numToLineForWin; // Количество фигур в линию для победы.
	
	public Referee(int aNumToLineForWin)
	{
		m_numToLineForWin = aNumToLineForWin;
	}
	
	public void put(Move move, Board board) throws Exception
	{
		//(ToDo
		// Добавить проверку правомерности такого хода.
		//)ToDo
		board.setAt(move.getX(), move.getY(), move.getFigure());
	}

	public boolean isWin(Move move, Board board) throws Exception
	{
		boolean result = false;
		int xSize = board.getXSize();
		int ySize = board.getYSize();
		int columns [] = new int[xSize];
		outer:
		for (int y = 0; y < ySize; y++)
		{
			int numInRow = 0;
			for (int x = 0; x < xSize; x++)
			{
				ActionFigure currentFigure = board.lookAt(x, y);
				if (currentFigure == move.getFigure())
				{
					// Проверяем горизонталь.
					if (++numInRow >= m_numToLineForWin)
					{
						result = true;
						break outer;
					}
					// Проверяем вертикаль.
					if (++columns[x] >= m_numToLineForWin)
					{
						result = true;
						break outer;
					}
					// Проверяем диагонали.
					if (y + m_numToLineForWin - 1 < ySize)
					{
						// Вправо-вниз.
						if (x + m_numToLineForWin - 1 < xSize)
						{
							int nunInDiagonal = 1;
							for (int i = 1; i < m_numToLineForWin; i++)
							{
								if (board.lookAt(x + i, y + i) == move.getFigure())
								{
									if (++nunInDiagonal >= m_numToLineForWin)
									{
										result = true;
										break outer;
									}
								}
								else
								{
									break;
								}
							}
						}
						// Влево-вниз.
						if (x - (m_numToLineForWin - 1) >= 0)
						{
							int nunInDiagonal = 1;
							for (int i = 1; i < m_numToLineForWin; i++)
							{
								if (board.lookAt(x - i, y + i) == move.getFigure())
								{
									if (++nunInDiagonal >= m_numToLineForWin)
									{
										result = true;
										break outer;
									}
								}
								else
								{
									break;
								}
							}
						}
					}
				}
				else
				{
					numInRow = 0;
					columns[x] = 0;
				}
			}
		}
		return result;
	}

}
