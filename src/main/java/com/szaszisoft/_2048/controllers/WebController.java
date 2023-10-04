package com.szaszisoft._2048.controllers;

import com.szaszisoft._2048.models.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class WebController {
  private Board board = new Board(4);
  private Board previous = new Board(4);

  @GetMapping("/")
  public String getBoard(Model model) {
    model.addAttribute("board", board);
    return "index";
  }

  @GetMapping("/move/{id}")
  public String getMoveBoard(@PathVariable String id) {
    int boardSize = board.getSize();
    if (id.equals("up")) {
      if (board.canMoveUp()) {
        previous.populate(board);
        board.moveUp();
      }
    }
    if (id.equals("down")) {
      board.rotateLeft();
      board.rotateLeft();
      if (board.canMoveUp()) {
        previous.populate(board);
        board.moveUp();
        previous.rotateLeft();
        previous.rotateLeft();
      }
      board.rotateLeft();
      board.rotateLeft();
    }
    if (id.equals("right")) {
      board.rotateLeft();
      if (board.canMoveUp()) {
        previous.populate(board);
        board.moveUp();
        previous.rotateLeft();
        previous.rotateLeft();
        previous.rotateLeft();
      }
      board.rotateLeft();
      board.rotateLeft();
      board.rotateLeft();
    }
    if (id.equals("left")) {
      board.rotateLeft();
      board.rotateLeft();
      board.rotateLeft();
      if (board.canMoveUp()) {
        previous.populate(board);
        board.moveUp();
        previous.rotateLeft();
      }
      board.rotateLeft();
    }

    return "redirect:/";
  }

  @GetMapping("/reset")
  public String getReset() {
    board = new Board(4);
/*
    for (int i = 4; i < 21; i++) {
      board.addNewNumber((int)Math.pow(2,i));
    }
*/
    return "redirect:/";
  }

  @GetMapping("/undo")
  public String getUndo() {
    board.populate(previous);
    return "redirect:/";
  }

}
