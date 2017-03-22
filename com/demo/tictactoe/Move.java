package com.demo.tictactoe;

public class Move {
	private final Coordinates mCoordinates; // Координаты, в которые делается ход.
	private final ActionFigure figure;      // Фигура, которой делается ход.
	
	public Move(int aX, int aY, ActionFigure aFigure)
	{
		mCoordinates = new Coordinates(aX, aY);
		figure = aFigure;
	}

	public int getX()
	{
		return mCoordinates.getX();
	}

	public int getY()
	{
		return mCoordinates.getY();
	}

	public ActionFigure getFigure()
	{
		return figure;
	}

	@Override
	public String toString()
	{
		return "" + figure + " -> (" + mCoordinates + ")";
	}
}
