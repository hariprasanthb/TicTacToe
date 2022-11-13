package com.dev.tictactoe.service.impl;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dev.tictactoe.constants.TicTacToeAppRunningStatus;
import com.dev.tictactoe.constants.TicTacToePlayer;
import com.dev.tictactoe.constants.TicTacToePosition;
import com.dev.tictactoe.model.TicTacToeInput;
import com.dev.tictactoe.model.TicTacToeResponse;
import com.dev.tictactoe.service.TicTacToeService;

@Service
public class TicTacToeServiceImpl implements TicTacToeService 
{
	private static final String NO_MATCH_FOUND = "No Match Found";
	private static final Logger logger = LogManager.getLogger(TicTacToeServiceImpl.class);
	
	@Override
	public TicTacToeResponse validateUserInput(List<TicTacToeInput> ticTacToeUserInputs) {
		TicTacToeResponse ticTacToeResponse = new TicTacToeResponse();
		String message = StringUtils.EMPTY;
		TicTacToeAppRunningStatus status = TicTacToeAppRunningStatus.SUCCESS;
		
		Map<TicTacToePosition, List<TicTacToeInput>> ticTacToeInputByPosition = ticTacToeUserInputs.stream()
				.collect(Collectors.groupingBy(TicTacToeInput::getPosition));
		boolean isSamePositionFilled = ticTacToeInputByPosition.entrySet().stream().anyMatch(input -> input.getValue().size() > 1);
		
		String firstPlayerInput = ticTacToeUserInputs.get(0).getPlayer().toString();
		if (firstPlayerInput.equals(TicTacToePlayer.O.toString())) {
			logger.error("X always goes first");
			message = "X always goes first";
			status = TicTacToeAppRunningStatus.FAIL;
		} else if (isSamePositionFilled) {
			logger.error("Players cannot play on a played position.");
			message = "Players cannot play on a played position.";
			status = TicTacToeAppRunningStatus.FAIL;
		} else {
			Long playerXCount = ticTacToeUserInputs.stream()
					.filter(ticTacToeUserInput -> ticTacToeUserInput.getPlayer().toString().equals(TicTacToePlayer.X.toString()))
					.count();
			Long playerOCount = ticTacToeUserInputs.stream()
					.filter(ticTacToeUserInput -> ticTacToeUserInput.getPlayer().toString().equals(TicTacToePlayer.O.toString()))
					.count();
			if (playerXCount >= 3 || playerOCount >= 3)
				message = checkWinningStatus(ticTacToeUserInputs);
			else 
				message = "Match still going on";
		}
		logger.info("Match Status: {}" , message);
		ticTacToeResponse.setMessage(message);
		ticTacToeResponse.setStatus(status);
		return ticTacToeResponse;
	}

	private String checkWinningStatus(List<TicTacToeInput> ticTacToeUserInputs) {
		
		Map<String, String> ticTacToeDefaultValueMap = new HashMap<>();
		ticTacToeDefaultValueMap.put(TicTacToePosition.A1.toString(), TicTacToePosition.A1.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.A2.toString(), TicTacToePosition.A2.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.A3.toString(), TicTacToePosition.A3.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.B1.toString(), TicTacToePosition.B1.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.B2.toString(), TicTacToePosition.B2.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.B3.toString(), TicTacToePosition.B3.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.C1.toString(), TicTacToePosition.C1.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.C2.toString(), TicTacToePosition.C2.toString());
		ticTacToeDefaultValueMap.put(TicTacToePosition.C3.toString(), TicTacToePosition.C3.toString());
		
		Map<String, String> ticTacToeUserInputMap = ticTacToeUserInputs.stream()
				.collect(Collectors.toMap(ticTacToeUserInput -> ticTacToeUserInput.getPosition().toString(), 
						ticTacToeUserInput -> ticTacToeUserInput.getPlayer().toString()));
		
		Map<String, String> ticTacToeFinalValueMap = new HashMap<>();
		ticTacToeFinalValueMap.putAll(ticTacToeDefaultValueMap);
		ticTacToeFinalValueMap.putAll(ticTacToeUserInputMap);
		
		boolean isAnyPlayerWon = false;
		String horizontalMatchStringFromRowA = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A1.toString(), TicTacToePosition.A2.toString(),
						TicTacToePosition.A3.toString()));
		
		if (!checkAllRowMatchedForPlayer(horizontalMatchStringFromRowA).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(horizontalMatchStringFromRowA);
		}
		
		String horizontalMatchStringFromRowB = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.B1.toString(), TicTacToePosition.B2.toString(),
						TicTacToePosition.B3.toString()));
		
		if (!checkAllRowMatchedForPlayer(horizontalMatchStringFromRowB).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(horizontalMatchStringFromRowB);
		}
		
		String horizontalMatchStringFromRowC = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.C1.toString(), TicTacToePosition.C2.toString(),
						TicTacToePosition.C3.toString())); 
		if (!checkAllRowMatchedForPlayer(horizontalMatchStringFromRowC).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(horizontalMatchStringFromRowC);
		}
		
		String verticalMatchStringFromRowA = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A1.toString(), TicTacToePosition.B1.toString(),
						TicTacToePosition.C1.toString()));
		if (!checkAllRowMatchedForPlayer(verticalMatchStringFromRowA).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(verticalMatchStringFromRowA);
		}
		
		
		String verticalMatchStringFromRowB = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A2.toString(), TicTacToePosition.B2.toString(),
						TicTacToePosition.C2.toString())); 
		if (!checkAllRowMatchedForPlayer(verticalMatchStringFromRowB).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(verticalMatchStringFromRowB);
		}
		
		String verticalMatchStringFromRowC = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A3.toString(), TicTacToePosition.B3.toString(),
						TicTacToePosition.C3.toString())); 
		if (!checkAllRowMatchedForPlayer(verticalMatchStringFromRowC).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(verticalMatchStringFromRowC);
		}
		
		String diagonalMatchStringFromLeftCorner = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A1.toString(), TicTacToePosition.B2.toString(),
						TicTacToePosition.C3.toString())); 
		if (!checkAllRowMatchedForPlayer(diagonalMatchStringFromLeftCorner).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(diagonalMatchStringFromLeftCorner);
		}
		
		String diagonalMatchStringFromRightCorner = buidMatchStrings(ticTacToeUserInputMap, 
				Arrays.asList(TicTacToePosition.A3.toString(), TicTacToePosition.B2.toString(),
						TicTacToePosition.C1.toString())); 
		if (!checkAllRowMatchedForPlayer(diagonalMatchStringFromRightCorner).equals(NO_MATCH_FOUND)) {
			isAnyPlayerWon = true;
			return checkAllRowMatchedForPlayer(diagonalMatchStringFromRightCorner);
		}
		
		if (ticTacToeUserInputMap.size() >= 8 && !isAnyPlayerWon) {
			return "Match Drawn";
		}
		
		return "Match still going on";
	}

	private String checkAllRowMatchedForPlayer(String stringToCheck) {
		
		String status = StringUtils.EMPTY;
		
        if (stringToCheck.equals("XXX")) {
        	status = "X Won";
        } else if (stringToCheck.equals("OOO")) {
        	status = "O Won";
        } else {
        	status = NO_MATCH_FOUND;
        }
        
        return status;
	}

	private String buidMatchStrings(Map<String, String> ticTacToeUserInputMap, List<String> inputs) {
		StringBuilder matchStringBuilder = new StringBuilder();
		inputs.forEach(input -> matchStringBuilder.append(ticTacToeUserInputMap.get(input)));
		return matchStringBuilder.toString();
		
	}
}