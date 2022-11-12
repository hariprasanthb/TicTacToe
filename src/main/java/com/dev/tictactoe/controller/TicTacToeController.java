package com.dev.tictactoe.controller;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.tictactoe.constants.EndPoint;
import com.dev.tictactoe.model.TicTacToeInput;
import com.dev.tictactoe.model.TicTacToeResponse;
import com.dev.tictactoe.service.TicTacToeService;

@RestController
@RequestMapping(value = {EndPoint.TIC_TAC_TOE})
public class TicTacToeController 
{
	
	private static final Logger logger = LogManager.getLogger(TicTacToeController.class);
	
	@Autowired
	TicTacToeService ticTacToeService;
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> validateUserInput(
			@RequestBody(required = true) LinkedList<TicTacToeInput> ticTacToeUserInput) 
	{
		logger.info("Requsting Tic Tac Toe user input validation starts");
		TicTacToeResponse response = ticTacToeService.validateUserInput(ticTacToeUserInput);
		logger.info("Requsting Tic Tac Toe user input validation ends");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
