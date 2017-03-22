package com.demo.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TheGame {
	enum PlayerType
	{
		HUMAN("Человек"),
		RANDOM("Компьютер: Случайный стрелок")
		;
		
		private final String mName; // Представление значения при выводе на экран.

		private PlayerType(String aName)
		{
			mName = aName;
		}
		
		@Override
		public String toString()
		{
			return mName;
		}
	}
	
	private static final Scanner SCANNER = new Scanner(System.in); // Поток ввода данных от пользователя.
	private static int mBoardXSize = 3;        // Ширина доски.
	private static int mBoardYSize = 3;        // Высота доски.
	private static int mWinLineLength = 3;     // Длина линии из одинаковых фигур для победы.
	private static int mNumErrorsAllowed = 10; // Допустимое количество ошибок до дисквалификации.
	private static int mNumOfPlayers;          // Количество играющих игроков.
	private static Player[] mPlayers;          // Играющие в данный момент Игроки.
	private static boolean mIsRulesClassic;    // Признак того, что пользователь выбрал игру по классическим правилам и лишние вопросы ему задавать не нужно.
	
	public static void main(String[] args)
	{
		Move move;                 // Текущий ход игрока.
		MoveResult moveResult;     // Результат проверки хода игрока судьей.
		
		mainMenu();
		Board board     = new Board(mBoardXSize, mBoardYSize);
		Referee referee = new Referee(mWinLineLength, mNumErrorsAllowed);
		createPlayers();
		System.out.println();
		System.out.println("Игра началась!");
		board.print();
		
		boolean gameOver = false;
		while (!gameOver)
		{
			for (Player player : mPlayers)
			{
				do {			
					move = player.turn(new Board(board));
					System.out.println();
					System.out.println("Игрок " + player + " ходит " + move);
					moveResult = referee.commitMove(player, move, board);
				} while (moveResult == null); // Пока Игроку есть куда ходить, но он делает некорректные хода.
				board.print();
				switch (moveResult)
				{
					case WIN:
						gameOver = true;
						System.out.println();
						System.out.println("ВЫИГРАЛ Игрок " + player + "!!!");
						break;
					case DEADLOCK:
						gameOver = true;
						System.out.println();
						System.out.println("НИЧЬЯ!!!");
						break;
					case DISQUALIFICATION:
						gameOver = true;
						System.out.println();
						System.out.println("ДИСКВАЛИФИЦИРОВАН Игрок " + player + "!!!");
						break;
				}
				if (gameOver) break;
			}
		}
		SCANNER.close();
	}
	
	/**
	 * Главное меню игры.
	 * Запрашивает у пользователя выбор варианта игры, либо предоставляет возможность выйти из игры.
	 * Определяет размеры доски, количество фигур в линии для одержания победы,
	 * а также количество некорректных ходов подряд для дисквалификации игрока.
	 */
	public static void mainMenu()
	{
		// Запросим у пользователя параметры игры.
		boolean isAnswerUnderstandable = false;
		while (!isAnswerUnderstandable)
		{
			System.out.println("+------------------------------------+");
			System.out.println("| Игра Крестики-Нолики-Йорики-Дорики |");
			System.out.println("+------------------------------------+");
			System.out.println("C - Играть по классическим правилам");
			System.out.println("P - Задать параметры игры");
			System.out.println("E - Выход из программы");
			System.out.print("Сделайте выбор:");
			String answer = SCANNER.next();
			switch (answer.toUpperCase())
			{
				// Классические правила
				case "C":
					isAnswerUnderstandable = true;
					mIsRulesClassic = true;
					break;
					
				// Настройка параметров игры
				case "P":
					isAnswerUnderstandable = true;
					mIsRulesClassic = false;
					mBoardXSize = scanIntBordered("Укажите ширину доски (3..100):", 3, 100);
					mBoardYSize = scanIntBordered("Укажите высоту доски (3..100):", 3, 100);
					mWinLineLength = scanIntBordered("Укажите длину линии для победы (3..100):", 3, 100);
					mNumErrorsAllowed = scanIntBordered("Укажите допустимое количество ошибочных ходов подряд до дисквалификации (0..100, или \"-1\" - неограничено):", -1, 100);
					break;
					
				// Выход
				case "E":
					exit();
					break;
				default:
					System.out.println();
					System.out.println("Ваш выбор не понятен. Попробуйте еще раз.");
			}
		}
	}
	
	/**
	 * Запросить у пользователя конфигурацию игроков.
	 */
	public static void createPlayers()
	{
		if (mIsRulesClassic)
		{
			mNumOfPlayers = 2;
		}
		else
		{
			mNumOfPlayers = scanIntBordered("Укажите количество игроков (2..5):", 2, 5);
		}
		mPlayers = new Player[mNumOfPlayers];
		ActionFigure[] figures = ActionFigure.values();
		for (int i = 0; i < mPlayers.length; i++)
		{
			System.out.println();
			System.out.println("-------------------------------");
			System.out.println("Создание игрока №" + i + ":");
			mPlayers[i] = createPlayer(figures);
			figures[mPlayers[i].getFigure().ordinal()] = null;
		}
	}
	
	public static Player createPlayer(ActionFigure[] figures)
	{
		Player player;
		int choose;
		String name;
		ActionFigure figure;
		PlayerType playerType;
		PlayerType[] playerTypes = PlayerType.values();
		int numOfPlayerTypes = playerTypes.length;
		
		///////////////////////////////////
		// Определение типа игрока.
		System.out.println();
		System.out.println("Выберите тип игрока:");
		for (int i = 0; i < numOfPlayerTypes; i++)
		{
			System.out.println("	" + i + " - " + playerTypes[i]);
		}
		choose = scanIntBordered("Сделайте выбор:", 0, numOfPlayerTypes-1);
		playerType = playerTypes[choose];
		
		///////////////////////////////////
		// Определение фигуры, которой будет ходить игрок.
		if (mIsRulesClassic)
		{
			figure = (figures[0] != null) ? figures[0] : figures[1];
		}
		else
		{
			while (true)
			{
				System.out.println();
				System.out.println("Выберите фигуру, которой будет ходить игрок:");
				for (int i = 0; i < figures.length; i++)
				{
					if (figures[i] != null)
					{
						System.out.println("	" + i + " - " + figures[i]);
					}
				}
				choose = scanInt("Сделайте выбор:");
				if (choose >=0 && choose < figures.length && figures[choose] != null)
				{
					break;
				}
				else
				{
					System.out.println();
					System.out.println("Сделайте правильный выбор.");
				}
			}
			figure = figures[choose];
		}
		
		///////////////////////////////////
		// Определение имени игрока.
		System.out.println();
		System.out.print("Введите имя игрока:");
		name = SCANNER.next();
		
		///////////////////////////////////
		// Создание и возврат соответствующего объекта.
		switch (playerType)
		{
			case HUMAN:
				player = new PlayerHuman(name, figure);
				break;
			case RANDOM:
				player = new PlayerRandom(name, figure);
				break;
			default:
				throw new UnknownError("Неизвестный выбранный тип игрока.");
		}
		System.out.println();
		System.out.println("Создан игрок " + name + " [" + playerType + "] играет фигурой \"" + figure + "\".");
		return player;
	}
	
	/**
	 * Запросить у пользователя ввод целого числа.
	 * @param aMsg - Сообщение с запросом пользователю на ввод числа.
	 * @return - Введенное пользователем число.
	 */
	public static int scanInt(String aMsg)
	{
		int result = 0;
		while (true)
		{
			System.out.println();
			System.out.print(aMsg);
			try
			{
				result = SCANNER.nextInt();
				break;
			}
			catch (InputMismatchException e)
			{
				SCANNER.next(); // Пропускаем ошибочный ввод.
				System.out.println();
				System.out.println("Введите числовое значение.");
			}
		}
		return result;
	}
	
	/**
	 * Запросить у пользователя ввод целого числа из диапазона.
	 * @param aMsg - Сообщение с запросом пользователю на ввод числа.
	 * @param aMin - Нижняя граница допустимого диапазона ввода числа (включительно).
	 * @param aMax - Верхняя граница допустимого диапазона ввода числа (включительно).
	 * @return - Введенное пользователем число.
	 */
	public static int scanIntBordered(String aMsg, int aMin, int aMax)
	{
		int result;
		while (true)
		{
			result = scanInt(aMsg);
			if (result >= aMin && result <= aMax)
			{
				break;
			}
			else
			{
				System.out.println();
				System.out.println("Введите числовое значение в указанном диапазоне.");
			}
		}
		return result;
	}
	
	/**
	 * Корректный выход из программы.
	 */
	public static void exit()
	{
		System.out.println();
		System.out.println("Всего доброго.");
		SCANNER.close();
		System.exit(0);
	}
}
