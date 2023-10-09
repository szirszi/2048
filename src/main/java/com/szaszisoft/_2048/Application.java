package com.szaszisoft._2048;

import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.services.BoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
  private final BoardService boardService;

  public Application(BoardService boardService) {
    this.boardService = boardService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    Board board = boardService.initializeBoard(4);
    System.out.println("Play: " + board.getId());
    Board previous = boardService.initializeBoard(4);
    System.out.println("Prev: " + previous.getId());
  }
}

