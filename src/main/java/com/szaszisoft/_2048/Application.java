package com.szaszisoft._2048;

import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.models.BoardType;
import com.szaszisoft._2048.models.Game;
import com.szaszisoft._2048.repositories.GameRepository;
import com.szaszisoft._2048.services.BoardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
  private final BoardService boardService;
  private final GameRepository gameRepository;

  @Autowired
  public Application(BoardService boardService, GameRepository gameRepository) {
    this.boardService = boardService;
    this.gameRepository = gameRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Game game = gameRepository.save(new Game());
    Board board = boardService.initializeBoard(4);
    board.setGame(game);
    board.setBoardType(BoardType.PLAY);
    System.out.println("Play: " + board.getId());
    Board previous = boardService.initializeBoard(4);
    previous.setGame(game);
    previous.setBoardType(BoardType.UNDO);
    System.out.println("Prev: " + previous.getId());
    List<Board> boards = new ArrayList<>();
    boards.add(board);
    boards.add(previous);
    game.setBoards(boards);
    gameRepository.save(game);
  }
}
