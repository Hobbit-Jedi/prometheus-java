package com.demo.tictactoe;

public class Coordinates {
	private final int mX; // x-координата.
	private final int mY; // y-координата.

	public Coordinates()
	{
		this(0, 0);
	}
	
	public Coordinates(int aX, int aY)
	{
		mX = aX;
		mY = aY;
	}
	
	public int getX()
	{
		return mX;
	}
	
	public int getY()
	{
		return mY;
	}
	
	public String getXAsString()
	{
		return indexToCoordinate(mX);
	}
	
	public String getYAsString()
	{
		return indexToCoordinate(mY);
	}
	
	@Override
	public String toString()
	{
		return "" + getXAsString() + "," + mY;
	}
	
/**
	 * Преобразует числовую координату в буквенную (индекс в пользовательскую координату).
	 * Буквенная координата представляет собой 26-ричное представление числовой координаты.
	 * Например:
	 *	0 = a
	 *	1 = b
	 *	25 = z
	 *	26 = ba
	 *	90 = dm
	 *	298 = lm
	 * @param aX - Числовая координата-индекс.
	 * @return - Соответствующая переданному значению строковая координата.
	 *           Пустая строка, если передано отрицательное значение.
	 */
	public static String indexToCoordinate(int aX)
	{
		StringBuilder result = new StringBuilder();
		if (aX >= 0)
		{
			int rest = aX;
			byte current;
			char coordinatePart;
			do {				
				current = (byte) (rest % 26);
				rest /= 26;
				coordinatePart = (char)('a' + current);
				result.insert(0, coordinatePart);
			} while (rest > 0);
		}
		return result.toString();
	}
	
	/**
	 * Преобразует буквенную координату в числовую координату-индекс.
	 * По своему действию обратна методу indexToCoordinate (см. его для получения подробностей).
	 * @param aCoordinate - Строкове пользовательское представление координаты.
	 * @return - Числовая координата-индекс, соответствующая переданному значению.
	 *           -1, если переданное значение не является строковым представлением координаты.
	 */
	public static int coordinateToIndex(String aCoordinate)
	{
		int result = -1;
		String clearCoordinate = aCoordinate.trim().toLowerCase();
		int length = clearCoordinate.length();
		if (length > 0)
		{
			result = 0;
			for (int i = 0; i < length; i++)
			{
				char current = clearCoordinate.charAt(i);
				if (current >= 'a' && current <= 'z')
				{
					result += (current - 'a') * Math.pow(26, length-1-i);
				}
				else
				{
					result = -1;
				}
			}
		}
		return result;
	}
	
	/**
	 * Метод для тестирования методов indexToCoordinate(...) и coordinateToIndex(...).
	 * @param aShowErrorsOnly - Признак того, что нужно выводить в консоль только сообщения об ошибках, а не весь лог тестирования.
	 */
	public static void test_indexToCoordinateAndReverse(boolean aShowErrorsOnly)
	{
		System.out.println("--> Start of Testing of indexToXCoordinate()");
		for (int i = -5; i < 300; i++)
		{
			String xCoordinate = indexToCoordinate(i);
			int reverse = coordinateToIndex(xCoordinate);
			boolean isError = (i < 0 && reverse != -1) || (i >= 0 && i != reverse);
			if (isError || !aShowErrorsOnly)
			{
				System.out.println((isError ? "!!! ОШИБКА !!! " : "") + i + " => " + xCoordinate + " => " + reverse);
			}
		}
		System.out.println("<-- End of Testing of indexToXCoordinate()");
	}
	
}
