package com.demo.tictactoe;

public class TheGame {
	
	public static void main(String[] args)
	{
		Move move;
		MoveResult moveResult;
		
		Referee referee = new Referee(3, 5);
		
		Board board = new Board(3, 3);
		
		//Player players[]  = new Player[5];
		Player players[]  = new Player[2];
		players[0] = new PlayerRandom("Первый ("    + ActionFigure.CROSS  + ")", ActionFigure.CROSS);
		players[1] = new PlayerRandom("Второй ("    + ActionFigure.NOUGHT + ")", ActionFigure.NOUGHT);
		//players[2] = new PlayerRandom("Третий ("    + ActionFigure.STAR   + ")", ActionFigure.STAR);
		//players[3] = new PlayerRandom("Четвертый (" + ActionFigure.DOG    + ")", ActionFigure.DOG);
		//players[4] = new PlayerRandom("Пятый ("     + ActionFigure.HASH   + ")", ActionFigure.HASH);
		
		boolean gameOver = false;
		while (!gameOver)
		{
			for (Player player : players)
			{
				do {			
					move = player.turn(new Board(board));
					moveResult = referee.commitMove(player, move, board);
				} while (moveResult == null); // Пока Игроку есть куда ходить, но он делает некорректные хода.
				board.print();
				switch (moveResult)
				{
					case WIN:
						gameOver = true;
						System.out.println("ВЫИГРАЛ Игрок " + player + "!!!");
						break;
					case DEADLOCK:
						gameOver = true;
						System.out.println("НИЧЬЯ!!!");
						break;
					case DISQUALIFICATION:
						gameOver = true;
						System.out.println("ДИСКВАЛИФИЦИРОВАН Игрок " + player + "!!!");
						break;
				}
				if (gameOver) break;
			}
		}
	}

}
