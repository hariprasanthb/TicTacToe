package com.dev.tictactoe.model;

import com.dev.tictactoe.constants.TicTacToePlayer;
import com.dev.tictactoe.constants.TicTacToePosition;

import lombok.Data;

@Data
public class TicTacToeInput {

	/**
	 * Position of player Eg: a1, b1 etc..,
	 */
	private TicTacToePosition position;
	
	/**
	 * Player name contains only X or O
	 */
	private TicTacToePlayer player;
	
	public TicTacToeInput(TicTacToePosition position, TicTacToePlayer player) {
		this.position = position;
		this.player = player;
	}

}
