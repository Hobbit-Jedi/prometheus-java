package com.demo.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Описывает Игрока, которым управляет человек с консоли.
 * @author Hobbit Jedi
 */
public class PlayerHuman extends Player {
	
	/**
	 * Создает игрока, назначая ему игровую фигуру.
	 * @param aName - Имя игрока.
	 * @param aFigure - Фигура, которой будет играть игрок.
	 */
	public PlayerHuman(String aName, ActionFigure aFigure)
	{
		super(aName, aFigure);
	}

	@Override
	public Move turn(Board aBoard)
	{
		System.out.println();
		System.out.println();
		//TODO: Реализовать.
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
