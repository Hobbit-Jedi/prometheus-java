package com.demo.tictactoe;

/**
 * Описывает игровое поле.
 * @author Hobbit Jedi
 */
public class Board {
	private final int mXSize; // Горизонтальный размер игрового поля.
	private final int mYSize; // Вертикальный размер игрового поля.
	private final ActionFigure[][] mField; // Игровое поле.
	
	/**
	 * Создает игровое поле указанных размеров.
	 * @param aXSize - Горизонтальный размер создаваемого игрового поля.
	 * @param aYSize - Вертикальный размер создаваемого игрового поля.
	 */
	public Board(int aXSize, int aYSize)
	{
		mXSize = aXSize;
		mYSize = aYSize;
		mField = new ActionFigure[mYSize][mXSize];
	}
	
	/**
	 * Создает копию игрового поля.
	 * @param aBoard - игровое поле, копия которого создается.
	 */
	public Board(Board aBoard)
	{
		this(aBoard.getXSize(), aBoard.getYSize());
		for (int y = 0; y < mYSize; y++)
		{
			for (int x = 0; x < mXSize; x++)
			{
				mField[y][x] = aBoard.lookAt(x, y);
			}
		}
	}
	
	/**
	 * Получить горизонтальный размер игрового поля.
	 * @return - Горизонтальный размер игрового поля.
	 */
	public int getXSize()
	{
		return mXSize;
	}
	
	/**
	 * Получить вертикальный размер игрового поля.
	 * @return - Вертикальный размер игрового поля.
	 */
	public int getYSize()
	{
		return mYSize;
	}
	
	/**
	 * Определить попадает ли клеточка в игровое поле.
	 * @param aX - X-координата проверяемой клеточки поля.
	 * @param aY - Y-координата проверяемой клеточки поля.
	 * @return - Признак того, что клеточка с указанными координатами присутствует на игровом поле.
	 */
	public boolean isCoordinateAtBoard(int aX, int aY)
	{
		return (aX >= 0 && aX < mXSize && aY >= 0 && aY < mYSize);
	}
	
	/**
	 * Определить попадает ли клеточка в игровое поле.
	 * @param aCoordinates - Координаты проверяемой клеточки поля.
	 * @return - Признак того, что клеточка с указанными координатами присутствует на игровом поле.
	 */
	public boolean isCoordinateAtBoard(Coordinates aCoordinates)
	{
		return isCoordinateAtBoard(aCoordinates.getX(), aCoordinates.getY());
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
		if (isCoordinateAtBoard(aX, aY))
		{
			return mField[aY][aX];
		}
		else
		{
			throw new IllegalArgumentException("Некорректно переданы координаты в метод Board.lookAt()");
		}
	}
	
	/**
	 * Посмотреть на игровое поле (получить фигуру по координатам).
	 * @param aCoordinates - Координаты клеточки, в которую смотрим.
	 * @return - Фигура, которая находится на поле по указанным координатам.
	 * @throws IllegalArgumentException - Если координаты не попадают в поле, то вызывается исключение.
	 */
	public ActionFigure lookAt(Coordinates aCoordinates) throws IllegalArgumentException
	{
		return lookAt(aCoordinates.getX(), aCoordinates.getY());
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
		if (isCoordinateAtBoard(aX, aY))
		{
			mField[aY][aX] = aFigure;
		}
		else
		{
			throw new IllegalArgumentException("Некорректно переданы координаты в метод Board.setAt()");
		}
	}
	
	/**
	 * Установить фигуру на игровом поле.
	 * !!!ВНИМАНИЕ!!! Затирает расположенную в указанных координатах старую фигуру.
	 * @param aCoordinates - Координаты клеточки, в которой устанавливаем фигуру.
	 * @param aFigure - Фигура, которую устанавливаем в указанных координатах.
	 * @throws IllegalArgumentException - Если координаты выходят за пределы поля, то вызывает исключение.
	 */
	public void setAt(Coordinates aCoordinates, ActionFigure aFigure) throws IllegalArgumentException
	{
		setAt(aCoordinates.getX(), aCoordinates.getY(), aFigure);
	}
	
	/**
	 * Найти первую попавшуюся свободную клеточку на поле.
	 * @return - Координаты найденной свободной клеточки.
	 */
	public Coordinates searchFirstEmpty()
	{
		Coordinates result = null;
		for (int y = 0; result == null && y < mYSize; y++)
		{
			for (int x = 0; result == null && x < mXSize; x++)
			{
				if (mField[y][x] == null)
				{
					result = new Coordinates(x, y);
				}
			}
		}
		return result;
	}
	
	/**
	 * Проверить есть ли еще свободные клеточки?
	 * @return - Признак того, что свободные клеточки на поле еще есть.
	 */
	public boolean hasMoreSpace()
	{
		boolean result = (searchFirstEmpty() != null);
		return result;
	}
	
	/**
	 * Отображает игровое поле в консоли.
	 */
	public void print()
	{
		final char lineTopLeftCorner     = '\u250C'; // ┌
		final char lineTopRightCorner    = '\u2510'; // ┐
		final char lineBottomLeftCorner  = '\u2514'; // └
		final char lineBottomRightCorner = '\u2518'; // ┘
		final char lineHorizontal        = '\u2500'; // ─
		final char lineHorizontalDown    = '\u252C'; // ┬
		final char lineHorizontalUp      = '\u2534'; // ┴
		final char lineVertical          = '\u2502'; // │
		final char lineVerticalRight     = '\u251C'; // ├
		final char lineVerticalLeft      = '\u2524'; // ┤
		final char lineCross             = '\u253C'; // ┼
		final char spaceFullCell         = '\u3000'; // Специальный широкий пробел, который как раз по размеру в ячейку попадает.
		final char spaceAroundLetter     = '\u2006'; // Специальный узкий пробел, которым надо обрамить букву или цифру, чтобы они в ячейку вписались.
		
		int xCoordinateLength = Coordinates.indexToCoordinate(mXSize-1).length();
		int yCoordinateLength = new StringBuilder().append(mYSize-1).length();
		// Преобразуем числовые X-индексы в буквенные координаты.
		String[] xCoordinates = new String[mXSize];
		for (int x = 0; x < mXSize; x++)
		{
			xCoordinates[x] = Coordinates.indexToCoordinate(x);
		}
		StringBuilder divideLine = new StringBuilder();
		// Сформируем горизонтальную разделительную линию таблицы.
		divideLine.append(lineVerticalRight);
		for (int j = 0; j < yCoordinateLength; j++)
		{
			divideLine.append(lineHorizontal);
		}
		for (int x = 0; x < mXSize; x++)
		{
			divideLine.append(lineCross).append(lineHorizontal);
		}
		divideLine.append(lineVerticalLeft);
		// Начинаем вывод.
		System.out.println();
		// Выведем верхнюю рамку поля.
		System.out.print(lineTopLeftCorner);
		for (int j = 0; j < yCoordinateLength; j++)
		{
			System.out.print(lineHorizontal);
		}
		for (int x = 0; x < mXSize; x++)
		{
			System.out.print(lineHorizontalDown);
			System.out.print(lineHorizontal);
		}
		System.out.println(lineTopRightCorner);
		// Выведем X-координаты.
		for (int i = 0; i < xCoordinateLength; i++)
		{
			System.out.print(lineVertical);
			for (int j = 0; j < yCoordinateLength; j++)
			{
				System.out.print(spaceFullCell);
			}
			for (int x = 0; x < mXSize; x++)
			{
				int xCoordCurrentLen = xCoordinates[x].length();
				System.out.print(lineVertical);
				if (i >= xCoordinateLength - xCoordCurrentLen)
				{
					System.out.print(spaceAroundLetter);
					System.out.print(xCoordinates[x].charAt(i - xCoordinateLength + xCoordCurrentLen));
					System.out.print(spaceAroundLetter);
				}
				else
				{
					System.out.print(spaceFullCell);
				}
			}
			System.out.println(lineVertical);
		}
		// Выводим разделительную горизонтальную черту.
		System.out.println(divideLine);
		// Выводим само поле.
		for (int y = 0; y < mYSize; y++)
		{
			System.out.print(lineVertical);
			for (int spaceCounter = 0; spaceCounter < yCoordinateLength; spaceCounter++)
			{
				System.out.print(spaceAroundLetter);
			}
			System.out.format("%"+yCoordinateLength+"d", y); // Выводим Y-координату
			for (int spaceCounter = 0; spaceCounter < yCoordinateLength; spaceCounter++)
			{
				System.out.print(spaceAroundLetter);
			}
			for (int x = 0; x < mXSize; x++)
			{
				System.out.print(lineVertical);
				System.out.print(mField[y][x] != null
									?
									mField[y][x]
									:
									spaceFullCell
								);
			}
			System.out.println(lineVertical);
			if (y != mYSize - 1) // После последней строки разделительную линию не выводим.
			{
				System.out.println(divideLine);
			}
		}
		// Выведем нижнюю рамку поля.
		System.out.print(lineBottomLeftCorner);
		for (int j = 0; j < yCoordinateLength; j++)
		{
			System.out.print(lineHorizontal);
		}
		for (int x = 0; x < mXSize; x++)
		{
			System.out.print(lineHorizontalUp);
			System.out.print(lineHorizontal);
		}
		System.out.println(lineBottomRightCorner);
	}
	
}
