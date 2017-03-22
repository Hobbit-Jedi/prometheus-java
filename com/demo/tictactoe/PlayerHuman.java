package com.demo.tictactoe;

import java.util.Scanner;

/**
 * Описывает Игрока, которым управляет человек с консоли.
 * @author Hobbit Jedi
 */
public class PlayerHuman extends Player {
	private static final Scanner SCANNER = new Scanner(System.in); // Поток ввода данных от пользователя.
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerHuman(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
	}

	@Override
	public Move turn(Board aBoard)
	{
		System.out.println();
		System.out.println("Ходит игрок " + m_Name);
		Coordinates coordinates = scanCoordinates(aBoard.getXSize(), aBoard.getYSize());
		return new Move(coordinates.getX(), coordinates.getY(), m_Figure);
	}
	
	private Coordinates scanCoordinates(int aXSize, int aYSize)
	{
		Coordinates result;
		String scanInput;
		while (true)
		{
			System.out.println();
			System.out.print("Введите координаты хода через запятую в виде \"x,y\" (буквенная координата, числовая координата):");
			scanInput = SCANNER.next();
			String[] parts = scanInput.split(",");
			if (parts.length == 2)
			{
				int x = Coordinates.coordinateToIndex(parts[0]);
				if (x >= 0)
				{
					int y;
					try
					{
						y = new Integer(parts[1]);
					}
					catch (NumberFormatException e)
					{
						System.out.println("Некорректно указана y-координата.");
						continue;
					}
					result = new Coordinates(x, y);
					break;
				}
				else
				{
					System.out.println("Некорректно указана x-координата.");
				}
			}
		}
		return result;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize(); //To change body of generated methods, choose Tools | Templates.
		SCANNER.close();
	}
	
}
