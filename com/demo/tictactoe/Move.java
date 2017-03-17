package com.demo.tictactoe;

public class Move {
	private int x; // x-координата, в которую делается ход.
	private int y; // y-координата, в которую делается ход.
	private ActionFigure figure; // Фигура, которой делается ход.
	
	public Move(int aX, int aY, ActionFigure aFigure)
	{
		x = aX;
		y = aY;
		figure = aFigure;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public ActionFigure getFigure()
	{
		return figure;
	}

	public void setFigure(ActionFigure figure)
	{
		this.figure = figure;
	}
	
	@Override
	public String toString()
	{
		return "" + figure + " -> (" + x + "," + y + ")";
	}
}
