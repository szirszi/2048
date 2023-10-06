package com.szaszisoft._2048;

//import com.szaszisoft._2048.models.Board;
//import com.szaszisoft._2048.models.BoardRow;
//import com.szaszisoft._2048.repositories.BoardRepository;
//import com.szaszisoft._2048.repositories.BoardRowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
//  private final BoardRepository boardRepository;
//  private final BoardRowRepository boardRowRepository;
//
//  public Application(BoardRepository boardRepository, BoardRowRepository boardRowRepository) {
//    this.boardRepository = boardRepository;
//    this.boardRowRepository = boardRowRepository;
//  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

//    Board board = new Board(4);
//    Board saved = boardRepository.save(board);
//
//    BoardRow boardRow = new BoardRow();
//    boardRow.setSize(4);
//    for (int i = 0; i < 4; i++) {
//      for (int j = 0; j < 4; j++) {
//        boardRow.addToRow(0);
//      }
//      saved.addRowToPlayBoard(boardRow);
//      boardRow.setBoard(saved);
//      boardRowRepository.save(boardRow);
//    }

  }
}

