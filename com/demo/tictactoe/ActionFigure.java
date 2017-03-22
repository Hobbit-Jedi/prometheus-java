package com.demo.tictactoe;

public enum ActionFigure {
	CROSS('X'),  //крестик
	NOUGHT('0'), //нолик
	STAR('*'),   //звездочка
	HASH('#'),   //решетка
	DOG('@')     //собачка
	;
	
	private final char m_Image; // Отображаемый символ.
	
	private ActionFigure(char aImage)
	{
		m_Image = aImage;
	}
	
	@Override
	public String toString()
	{
		return "" + m_Image;
	}
}
