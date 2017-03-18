package com.demo.tictactoe;

public class Board {
	private final int m_XSize; // Горизонтальный размер игрового поля.
	private final int m_YSize; // Вертикальный размер игрового поля.
	private final ActionFigure[][] m_Field; // Игровое поле.
	
	/**
	 * Создает игровое поле указанных размеров.
	 * @param aXSize - Горизонтальный размер создаваемого игрового поля.
	 * @param aYSize - Вертикальный размер создаваемого игрового поля.
	 */
	public Board(int aXSize, int aYSize)
	{
		m_XSize = aXSize;
		m_YSize = aYSize;
		m_Field = new ActionFigure[m_YSize][m_XSize];
	}
	
	public Board(Board aBoard)
	{
		this(aBoard.getXSize(), aBoard.getYSize());
		for (int y = 0; y < m_YSize; y++)
		{
			for (int x = 0; x < m_XSize; x++)
			{
				m_Field[y][x] = aBoard.lookAt(x, y);
			}
		}
	}
	
	/**
	 * Получить горизонтальный размер игрового поля.
	 * @return - Горизонтальный размер игрового поля.
	 */
	public int getXSize()
	{
		return m_XSize;
	}
	
	/**
	 * Получить вертикальный размер игрового поля.
	 * @return - Вертикальный размер игрового поля.
	 */
	public int getYSize()
	{
		return m_YSize;
	}
	
	/**
	 * Посмотреть на игровое поле (получить фигуру по координатам).
	 * @param aX - x-координата клеточки, в которую смотрим.
	 * @param aY - y-координата клеточки, в которую смотрим.
	 * @return - Фигура, которая находится на поле по указанным координатам.
	 * @throws IllegalArgumentException - Если координаты не попадают в поле, то вызывается исключение.
	 */
	public ActionFigure lookAt(int aX, int aY) throws IllegalArgumentException
	{
		if (aX >= 0 && aX < m_XSize && aY >= 0 && aY < m_YSize)
		{
			return m_Field[aY][aX];
		}
		else
		{
			throw new IllegalArgumentException("Некорректно переданы координаты в метод Board.lookAt()");
		}
	}
	
	/**
	 * Установить фигуру на игровом поле.
	 * !!!ВНИМАНИЕ!!! Затирает расположенную в указанных координатах старую фигуру.
	 * @param aX - x-координата клеточки, в которой устанавливаем фигуру.
	 * @param aY - y-координата клеточки, в которой устанавливаем фигуру.
	 * @param aFigure - Фигура, которую устанавливаем в указанных координатах.
	 * @throws IllegalArgumentException - Если координаты выходят за пределы поля, то вызывает исключение.
	 */
	public void setAt(int aX, int aY, ActionFigure aFigure) throws IllegalArgumentException
	{
		if (aX >= 0 && aX < m_XSize && aY >= 0 && aY < m_YSize)
		{
			m_Field[aY][aX] = aFigure;
		}
		else
		{
			throw new IllegalArgumentException("Некорректно переданы координаты в метод Board.setAt()");
		}
	}
	
	/**
	 * Проверить есть ли еще свободные клеточки?
	 * @return - Признак того, что свободные клеточки на поле еще есть.
	 */
	public boolean hasMoreSpace()
	{
		boolean result = false;
		for (int y = 0; !result && y < m_YSize; y++)
		{
			for (int x = 0; !result && x < m_XSize; x++)
			{
				if (m_Field[y][x] == null)
				{
					result = true;
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
		final char lineCross      = '+';
		final char lineHorizontal = '-';
		final char lineVertical   = '|';
		System.out.println();
		String divideLine = "";
		for (int x = 0; x < m_XSize; x++)
		{
			divideLine += "" + lineCross + lineHorizontal;
		}
		divideLine += lineCross;
		for (int y = 0; y < m_YSize; y++)
		{
			String fieldLine = "";
			for (int x = 0; x < m_XSize; x++)
			{
				fieldLine	+= "" + lineVertical
							+	(	m_Field[y][x] != null
									?
									m_Field[y][x]
									:
									" "
								);
			}
			fieldLine  += lineVertical;
			System.out.println(divideLine);
			System.out.println(fieldLine);
		}
		System.out.println(divideLine);
	}

}
