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
	
	/**
	 * Создает копию игрового поля.
	 * @param aBoard - игровое поле, копия которого создается.
	 */
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
		int xCoordinateLength = Coordinates.indexToCoordinate(m_XSize-1).length();
		int yCoordinateLength = new StringBuilder().append(m_YSize-1).length();
		// Преобразуем числовые X-индексы в буквенные координаты.
		String[] xCoordinates = new String[m_XSize];
		for (int x = 0; x < m_XSize; x++)
		{
			xCoordinates[x] = Coordinates.indexToCoordinate(x);
		}
		// Сформируем горизонтальную разделительную линию таблицы.
		StringBuilder divideLine = new StringBuilder();
		for (int j = 0; j < yCoordinateLength; j++)
		{
			divideLine.append(lineHorizontal);
		}
		for (int x = 0; x < m_XSize; x++)
		{
			divideLine.append(lineCross).append(lineHorizontal);
		}
		divideLine.append(lineCross);
		// Начинаем вывод.
		System.out.println();
		// Выведем X-координаты.
		for (int i = 0; i < xCoordinateLength; i++)
		{
			for (int j = 0; j < yCoordinateLength; j++)
			{
				System.out.print(" ");
			}
			for (int x = 0; x < m_XSize; x++)
			{
				System.out.print(lineVertical);
				if ( i < xCoordinates[x].length())
				{
					System.out.print(xCoordinates[x].charAt(i));
				}
				else
				{
					System.out.print(" ");
				}
			}
			System.out.println(lineVertical);
		}
		// Выводим разделительную горизонтальную черту.
		System.out.println(divideLine);
		// Выводим само поле.
		for (int y = 0; y < m_YSize; y++)
		{
			System.out.format("%"+yCoordinateLength+"d", y); // Выводим Y-координату
			for (int x = 0; x < m_XSize; x++)
			{
				System.out.print(lineVertical);
				System.out.print(	m_Field[y][x] != null
									?
									m_Field[y][x]
									:
									" "
								);
			}
			System.out.println(lineVertical);
			System.out.println(divideLine);
		}
	}
	
}
