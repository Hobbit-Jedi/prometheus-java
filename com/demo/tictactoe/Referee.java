package com.demo.tictactoe;

/**
 * Описывает судью, следящего за выполнением правил игры.
 * @author Hobbit Jedi
 */
public class Referee {
	private final Rules mRules;          // Правила, по которым проводится игра.
	private Player mLastPlayer;          // Игрок, который ходил последним. Хранится между ходами.
	private int mPlayerTurnTriesCounter; // Счетчик попыток хода игрока. Если игрок тупит и долго не может сделать правильный ход, то его дисквалифицируют.
	
	/**
	 * Создает судью, который будет следить за выполнением правил игры.
	 * @param aRules - Правила, по которым проводится игра.
	 */
	public Referee(Rules aRules)
	{
		mRules = aRules;
		mLastPlayer = null;
		mPlayerTurnTriesCounter = 0;
	}
	
	/**
	 * Выполняет проверку хода игрока, и если ход корректный, то фиксирует его на доске.
	 * После чего проверяет результат этого хода.
	 * @param aPlayer - Игрок, который совершает ход.
	 * @param aMove   - Проверяемый ход.
	 * @param aBoard  - Доска, к которой будет применен ход.
	 * @return - Результат выполнения хода.
	 *           Если ход недопустим, то возвращает null.
	 */
	public MoveResult commitMove(Player aPlayer, Move aMove, Board aBoard)
	{
		MoveResult result = null;
		if (aPlayer != mLastPlayer)
		{
			mLastPlayer = aPlayer;
			mPlayerTurnTriesCounter = 1;
		}
		else
		{
			mPlayerTurnTriesCounter++;
		}
		if (aMove != null)
		{
			// Проверим допустимость хода.
			Coordinates cellToMove = aMove.getCoordinates();
			if (aBoard.isCoordinateAtBoard(cellToMove))
			{
				if (aBoard.lookAt(cellToMove) == null)
				{
					// Если ход допустим, то отметим его на доске.
					aBoard.setAt(cellToMove, aMove.getFigure());
					// Определим результат хода.
					if (mRules.isWin(aMove, aBoard))
					{
						result = MoveResult.WIN;
					}
					else if (aBoard.hasMoreSpace())
					{
						result = MoveResult.CONTINUE;
					}
					else
					{
						result = MoveResult.DEADLOCK;
					}
				}
			}
			else
			{
				System.out.println("Игрок " + aPlayer + " некорректно указал координаты хода: " + aMove);
			}
		}
		else
		{
			if (!aBoard.hasMoreSpace())
			{
				result = MoveResult.DEADLOCK;
			}
			else
			{
				System.out.println("Игрок " + aPlayer + " не знает куда ему пойти.");
			}
		}
		if (result == null && mRules.getNumErrorsAllowed() >= 0 && mPlayerTurnTriesCounter > mRules.getNumErrorsAllowed())
		{
			result = MoveResult.DISQUALIFICATION;
		}
		return result;
	}
	
}
