package com.demo.tictactoe;

/**
 * Описывает фигуру, которой делаются хода.
 * @author Hobbit Jedi
 */
public enum ActionFigure {
	CROSS('X'),  //крестик
	NOUGHT('0'), //нолик
	STAR('*'),   //звездочка
	HASH('#'),   //решетка
	DOG('@')     //собачка
	;
	
	private final char mImage; // Отображаемый символ.
	
	private ActionFigure(char aImage)
	{
		mImage = aImage;
	}
	
	/**
	 * Получить строковое представление фигуры.
	 * @return - Строковое представление фигуры.
	 */
	@Override
	public String toString()
	{
		return String.valueOf(mImage);
	}
}
