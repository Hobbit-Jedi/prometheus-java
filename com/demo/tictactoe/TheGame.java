package com.demo.tictactoe;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TheGame {
	enum PlayerType
	{
		HUMAN("Человек"),
		RANDOM("Компьютер: Случайный стрелок"),
		INVINCIBLE("Компьютер: Непобедимый")
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
	private static boolean mIsRulesClassic;   // Признак того, что пользователь выбрал игру по классическим правилам и лишние вопросы ему задавать не нужно.
	private static boolean mIsRulesRepeating; // Признак того, что пользователь выбрал игру по тем же правилам, по которым играл до того.
	
	public static void main(String[] args)
	{
		Move move;                 // Текущий ход игрока.
		MoveResult moveResult;     // Результат проверки хода игрока судьей.
		Rules rules = null;       // Правила, по которым ведется игра (размеры поля, длина победной линии, количество игроков и т.п.)
		
		while (true) // Пока пользователь в меню не выберет выход из игры.
		{			
			rules = mainMenu(rules);
			Board board     = new Board(rules.getBoardXSize(), rules.getBoardYSize());
			Referee referee = new Referee(rules);
			createPlayers(rules);
			System.out.println();
			System.out.println("--------------");
			System.out.println("Игра началась!");
			board.print();
			
			ArrayList<Player> players = new ArrayList<>();
			for (Player rulesPlayer : rules.getPlayers())
			{
				if (rulesPlayer != null)
				{
					players.add(rulesPlayer);
				}
			}
			boolean gameOver = (players.size() < 2); // Если в игре меньше двух игроков, то играть смысла нет.
			while (!gameOver)
			{
				for (int i = 0; i < players.size(); i++)
				{
					Player player = players.get(i);
					if (player != null)
					{
						do {			
							move = player.makeMove(new Board(board));
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
								players.remove(player);
								System.out.println();
								System.out.println("ДИСКВАЛИФИЦИРОВАН Игрок " + player + "!!!");
								if (players.size() <= 1)
								{
									gameOver = true;
									System.out.println();
									System.out.println("ВЫИГРАЛ Игрок " + players.get(0) + "!!!");
								}
								break;
						}
						if (gameOver) break;
					}
				}
			}
		}
	}
	
	/**
	 * Главное меню игры.
	 * Запрашивает у пользователя выбор варианта игры, либо предоставляет возможность выйти из игры.
	 * Определяет правила игры.
	 * @param aPreviousRules - Предыдущие правила игры (правила, по которым проводилась предыдущая игра).
	 * @return - Созданные правила игры.
	 */
	public static Rules mainMenu(Rules aPreviousRules)
	{
		Rules result = aPreviousRules;
		// Запросим у пользователя параметры игры.
		boolean isAnswerUnderstandable = false;
		while (!isAnswerUnderstandable)
		{
			System.out.println();
			System.out.println("+------------------------------------+");
			System.out.println("| Игра Крестики-Нолики-Йорики-Дорики |");
			System.out.println("+------------------------------------+");
			if (aPreviousRules != null)
			{
				System.out.println("R - Повторить игру с предыдущими параметрами.");
			}
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
					mIsRulesRepeating = false;
					result = new Rules();
					break;
					
				// Настройка параметров игры
				case "P":
					isAnswerUnderstandable = true;
					mIsRulesClassic = false;
					mIsRulesRepeating = false;
					int boardXSize       = scanIntBordered("Укажите ширину доски (3..100):", 3, 100);
					int boardYSize       = scanIntBordered("Укажите высоту доски (3..100):", 3, 100);
					int winLineLength    = scanIntBordered("Укажите длину линии для победы (3..100):", 3, 100);
					int numErrorsAllowed = scanIntBordered("Укажите допустимое количество ошибочных ходов подряд до дисквалификации (0..100, или \"-1\" - неограничено):", -1, 100);
					int numOfPlayers     = scanIntBordered("Укажите количество игроков (2..5):", 2, 5);
					result = new Rules(boardXSize, boardYSize, winLineLength, numErrorsAllowed, numOfPlayers);
					break;
					
				// Выход
				case "E":
					exit();
					break;
					
				// Повторить игру с предыдущими параметрами.
				case "R":
					if (aPreviousRules != null)
					{
						isAnswerUnderstandable = true;
						// Ничего в правилах не меняем.
						mIsRulesRepeating = true;
						break;
					}
					//break; //Здесь брейк не нужен, т.к. в случае несоблюдения условия, нужно вывести сообщение о непонятности выбора.
				default:
					System.out.println();
					System.out.println("Ваш выбор не понятен. Попробуйте еще раз.");
			}
		}
		return result;
	}
	
	/**
	 * Запросить у пользователя конфигурацию игроков.
	 * @param aRules - Правила игры, для которых создается конфигурация игроков.
	 */
	public static void createPlayers(Rules aRules)
	{
		Player[] players = aRules.getPlayers();
		ActionFigure[] figures = ActionFigure.values();
		for (int i = 0; i < players.length; i++)
		{
			if (mIsRulesRepeating && players[i] != null)
			{
				System.out.println();
				System.out.print("Инициализация игрока №" + i + " " + players[i] + "(" + players[i].getFigure() + ")");
				// Пока ничего в игроках менять не нужно.
				// Но в будущем здесь нужно будет вставлять код инициализации уже созданных ранее игроков.
				System.out.println(" - Готово!");
			}
			else
			{
				System.out.println();
				System.out.println("-------------------------------");
				System.out.println("Создание игрока №" + i + (mIsRulesClassic ? " (" + figures[i] + ")" : "") + ":");
				players[i] = createPlayer(figures);
			}
			figures[players[i].getFigure().ordinal()] = null;
		}
		// Знакомство с правилами делаем отдельным циклом, т.к. в правилах должны быть прописаны уже все участвующие игроки.
		System.out.println();
		for (int i = 0; i < players.length; i++)
		{
			System.out.print("Игрок №" + i + " " + players[i] + "(" + players[i].getFigure() + ") знакомится с правилами");
			players[i].checkOutRules(new Rules(aRules));
			System.out.println(" - Готово!");
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
			int figuresCount = 0;
			for (int i = 0; i < figures.length; i++)
			{
				if (figures[i] != null)
				{
					figuresCount++;
					choose = i;
				}
			}
			if (figuresCount > 1)
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
			}
			else
			{
				System.out.println();
				System.out.println("Осталась единственная фигура: " + figures[choose]);
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
			case INVINCIBLE:
				player = new PlayerInvincible(name, figure);
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
