/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tictactoe;

/**
 * Результат хода игрока.
 * @author Hobbit Jedi
 */
public enum MoveResult {
	CONTINUE, // Игра продолжается.
	WIN,      // Игрок выиграл.
	DEADLOCK  // Ничья.
}
