package com.szaszisoft._2048;

import com.szaszisoft._2048.services.BoardService;
import com.szaszisoft._2048.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
  private final BoardService boardService;
  private final GameService gameService;

  @Autowired
  public Application(BoardService boardService, GameService gameService) {
    this.boardService = boardService;
    this.gameService = gameService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
/*
    gameService.startNewGame(8);
    gameService.startNewGame(5);
*/
  }
}
