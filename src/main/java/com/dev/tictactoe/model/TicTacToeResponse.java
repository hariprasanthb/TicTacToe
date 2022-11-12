package com.dev.tictactoe.model;

import com.dev.tictactoe.constants.TicTacToeAppRunningStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class TicTacToeResponse {

	private String message;
	private TicTacToeAppRunningStatus status;
}
