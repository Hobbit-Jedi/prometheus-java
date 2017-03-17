package com.demo.tictactoe;

public class Board {
	private final int m_xSize; // Горизонтальный размер игрового поля.
	private final int m_ySize; // Вертикальный размер игрового поля.
	private final ActionFigure m_field [][]; // Игровое поле.
	
	/**
	 * Создает игровое поле указанных размеров.
	 * @param aXSize - Горизонтальный размер создаваемого игрового поля.
	 * @param aYSize - Вертикальный размер создаваемого игрового поля.
	 */
	public Board(int aXSize, int aYSize)
	{
		m_xSize = aXSize;
		m_ySize = aYSize;
		m_field = new ActionFigure[m_xSize][m_ySize];
	}
	
	/**
	 * Получить горизонтальный размер игрового поля.
	 * @return - Горизонтальный размер игрового поля.
	 */
	public int getXSize()
	{
		return m_xSize;
	}
	
	/**
	 * Получить вертикальный размер игрового поля.
	 * @return - Вертикальный размер игрового поля.
	 */
	public int getYSize()
	{
		return m_ySize;
	}
	
	/**
	 * Посмотреть на игровое поле (получить фигуру по координатам).
	 * @param aX - x-координата клеточки, в которую смотрим.
	 * @param aY - y-координата клеточки, в которую смотрим.
	 * @return - Фигура, которая находится на поле по указанным координатам.
	 * @throws Exception - Если координаты не попадают в поле, то вызывается исключение.
	 */
	public ActionFigure lookAt(int aX, int aY) throws Exception
	{
		if (aX >= 0 && aX < m_xSize && aY >= 0 && aY < m_ySize)
		{
			return m_field[aX][aY];
		}
		else
		{
			throw new Exception("Некорректно переданы координаты в метод Board.lookAt()");
		}
	}
	
	/**
	 * Установить фигуру на игровом поле.
	 * @param aX - x-координата клеточки, в которой устанавливаем фигуру.
	 * @param aY - y-координата клеточки, в которой устанавливаем фигуру.
	 * @param aFigure - Фигура, которую устанавливаем в указанных координатах.
	 * @throws Exception - Если координаты выходят за пределы поля,
	 *                     либо по указанным координатам уже есть какая-либо фигура,
	 *                     то вызывает исключение.
	 */
	public void setAt(int aX, int aY, ActionFigure aFigure) throws Exception
	{
		if (aX >= 0 && aX < m_xSize && aY >= 0 && aY < m_ySize)
		{
			if (m_field[aX][aY] == null)
			{
				m_field[aX][aY] = aFigure;
			}
			else
			{
				throw new Exception("Клеточка (" + aX + "," + aY + ") уже занята!");
			}
		}
		else
		{
			throw new Exception("Некорректно переданы координаты в метод Board.setAt()");
		}
	}
	
	/**
	 * Проверить есть ли еще свободные клеточки?
	 * @return - Признак того, что свободные клеточки на поле еще есть.
	 */
	public boolean hasMoreSpace()
	{
		boolean result = false;
		outer:
		for (int x = 0; x < m_xSize; x++)
		{
			for (int y = 0; y < m_ySize; y++)
			{
				if (m_field[x][y] == null)
				{
					result = true;
					break outer;
				}
			}
		}
		return result;
	}

	/**
	 * Отображает игровое поле в консоли.
	 */
	public void print()
	{
		System.out.println();
		String divideLine = "";
		for (int x = 0; x < m_xSize; x++)
		{
			divideLine += "+-";
		}
		divideLine += "+";
		for (int y = 0; y < m_ySize; y++)
		{
			String fieldLine = "";
			for (int x = 0; x < m_xSize; x++)
			{
				fieldLine  += "|" + (m_field[x][y]!=null?m_field[x][y]:" ");
			}
			fieldLine  += "|";
			System.out.println(divideLine);
			System.out.println(fieldLine);
		}
		System.out.println(divideLine);
	}

}
