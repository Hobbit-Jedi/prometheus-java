package com.demo.tictactoe;

public class TheGame {
	
	public static void main(String[] args) throws Exception
	{
		Move move;
		MoveResult moveResult;
		
		Referee referee = new Referee(3);
		
		Board board = new Board(3, 3);
		
		//Player players[]  = new Player[5];
		Player players[]  = new Player[2];
		players[0] = new Player("Первый ("    + ActionFigure.CROSS  + ")", ActionFigure.CROSS);
		players[1] = new Player("Второй ("    + ActionFigure.NOUGHT + ")", ActionFigure.NOUGHT);
		//players[2] = new Player("Третий ("    + ActionFigure.STAR   + ")", ActionFigure.STAR);
		//players[3] = new Player("Четвертый (" + ActionFigure.DOG    + ")", ActionFigure.DOG);
		//players[4] = new Player("Пятый ("     + ActionFigure.HASH   + ")", ActionFigure.HASH);
		
		boolean gameOver = false;
		while (!gameOver)
		{
			for (Player player : players)
			{
				do {				
					move = player.turn(board);
					moveResult = referee.commitMove(move, board);
				} while (moveResult == null); // Пока Игроку есть куда ходить, но он делает некорректные хода.
				board.print();
				switch (moveResult)
				{
					case WIN:
						gameOver = true;
						System.out.println("Игрок " + player + " ВЫИГРАЛ!!!");
						break;
					case DEADLOCK:
						gameOver = true;
						System.out.println("НИЧЬЯ!!!");
						break;
				}
				if (gameOver) break;
			}
		}
	}

}
