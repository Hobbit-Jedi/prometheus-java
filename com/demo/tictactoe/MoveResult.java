package com.demo.tictactoe;

/**
 * Результат хода игрока.
 * @author Hobbit Jedi
 */
public enum MoveResult {
	CONTINUE,         // Игра продолжается.
	WIN,              // Игрок выиграл.
	DISQUALIFICATION, // Игрок дисквалифицирован.
	DEADLOCK          // Ничья.
}
