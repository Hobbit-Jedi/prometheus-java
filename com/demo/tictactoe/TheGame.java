package com.demo.tictactoe;

public class TheGame {
	
	private static MoveResult playerTurn(Player aPlayer, Board aBoard, Referee aReferee) throws Exception
	{
		MoveResult result = MoveResult.DEADLOCK;
		if (aBoard.hasMoreSpace())
		{
			Move move = aPlayer.turn(aBoard);
			aReferee.put(move, aBoard); 
			aBoard.print(); 
			if (aReferee.isWin(move, aBoard))
			{ 
				result = MoveResult.WIN;
			}
			else
			{
				result = MoveResult.CONTINUE;
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception
	{
		Referee referee = new Referee(3);
		
		Board board = new Board(3, 3);
		
		//Player players[]  = new Player[5];
		Player players[]  = new Player[2];
		players[0] = new Player("Первый ("    + ActionFigure.CROSS  + ")", ActionFigure.CROSS);
		players[1] = new Player("Второй ("    + ActionFigure.NOUGHT + ")", ActionFigure.NOUGHT);
		//players[2] = new Player("Третий ("    + ActionFigure.STAR   + ")", ActionFigure.STAR);
		//players[3] = new Player("Четвертый (" + ActionFigure.DOG    + ")", ActionFigure.DOG);
		//players[4] = new Player("Пятый ("     + ActionFigure.HASH   + ")", ActionFigure.HASH);
		
		all:
		while (true)
		{
			for (Player player : players)
			{
				switch (playerTurn(player, board, referee))
				{
					case WIN:
						System.out.println("Игрок " + player + " ВЫИГРАЛ!!!");
						break all;
					case DEADLOCK:
						System.out.println("НИЧЬЯ!!!");
						break all;
				}
			}
		}
	}

}
