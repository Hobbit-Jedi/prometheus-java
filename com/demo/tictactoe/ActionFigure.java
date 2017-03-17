package com.demo.tictactoe;

public enum ActionFigure {
	NOUGHT('0'), //нолик
	CROSS('X'),  //крестик
	STAR('*'),   //звездочка
	DOG('@'),    //собачка
	HASH('#')    //решетка
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
