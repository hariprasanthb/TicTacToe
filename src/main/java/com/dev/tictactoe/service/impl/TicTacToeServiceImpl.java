package com.dev.tictactoe.service.impl;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dev.tictactoe.constants.TicTacToeAppRunningStatus;
import com.dev.tictactoe.constants.TicTacToePlayer;
import com.dev.tictactoe.model.TicTacToeInput;
import com.dev.tictactoe.model.TicTacToeResponse;
import com.dev.tictactoe.service.TicTacToeService;

@Service
public class TicTacToeServiceImpl implements TicTacToeService 
{
	private static final Logger logger = LogManager.getLogger(TicTacToeServiceImpl.class);
	
	@Override
	public TicTacToeResponse validateUserInput(List<TicTacToeInput> ticTacToeUserInput) {
		TicTacToeResponse ticTacToeResponse = new TicTacToeResponse();
		String message = "Tic Tac Toe Application executed successfully";
		TicTacToeAppRunningStatus status = TicTacToeAppRunningStatus.SUCCESS;
		
		if (ticTacToeUserInput.get(0).getPlayer().equals(TicTacToePlayer.O)) {
			logger.error("X always goes first");
			message = "X always goes first";
			status = TicTacToeAppRunningStatus.FAIL;
		}
		
		ticTacToeResponse.setMessage(message);
		ticTacToeResponse.setStatus(status);
		return ticTacToeResponse;
	}
}