package com.demo.tictactoe;

/**
 * Описывает результат хода игрока.
 * @author Hobbit Jedi
 */
public enum MoveResult {
	CONTINUE,         // Игра продолжается.
	WIN,              // Игрок выиграл.
	DISQUALIFICATION, // Игрок дисквалифицирован.
	DEADLOCK          // Ничья.
}
