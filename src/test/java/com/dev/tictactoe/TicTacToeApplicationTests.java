package com.dev.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.tictactoe.constants.TicTacToeAppRunningStatus;
import com.dev.tictactoe.constants.TicTacToePlayer;
import com.dev.tictactoe.constants.TicTacToePosition;
import com.dev.tictactoe.model.TicTacToeInput;
import com.dev.tictactoe.model.TicTacToeResponse;
import com.dev.tictactoe.service.TicTacToeService;
import com.dev.tictactoe.service.impl.TicTacToeServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicTacToeApplicationTests {
	
	@Mock
    private TicTacToeService ticTacToeService;
	
	@BeforeEach 
	void setUp()
    {
        this.ticTacToeService = new TicTacToeServiceImpl();
    }
 
    @Test
    @Order(1)
    void caseA_isFirstInputValid() {
    	TicTacToeInput ticTacToeInput = new TicTacToeInput(TicTacToePosition.A1, TicTacToePlayer.O);
    	TicTacToeResponse response = ticTacToeService.validateUserInput(Arrays.asList(ticTacToeInput));
    	assertEquals(response.getStatus(), TicTacToeAppRunningStatus.FAIL);
    }
    
    @Test
    @Order(2)
    void caseB_isFiledPositionGiven() {
    	List<TicTacToeInput> ticTacToeInputs = new ArrayList<>();
    	ticTacToeInputs.add(new TicTacToeInput(TicTacToePosition.A1, TicTacToePlayer.X));
    	ticTacToeInputs.add(new TicTacToeInput(TicTacToePosition.A1, TicTacToePlayer.O));
    	TicTacToeResponse response = ticTacToeService.validateUserInput(ticTacToeInputs);
    	assertEquals(response.getStatus(), TicTacToeAppRunningStatus.FAIL);
    }
}
