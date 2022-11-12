package com.dev.tictactoe.service;

import java.util.List;

import com.dev.tictactoe.model.TicTacToeInput;
import com.dev.tictactoe.model.TicTacToeResponse;

public interface TicTacToeService {

	public TicTacToeResponse validateUserInput(List<TicTacToeInput> ticTacToeUserInput);

}
