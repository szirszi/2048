package com.szaszisoft._2048.controllers;

import com.szaszisoft._2048.models.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String getMoveBoard(@PathVariable String id, RedirectAttributes redirectAttributes) {
    if (id.equals("up") && board.canMoveUp()) {
      previous.populate(board);
      board.moveUp();
    }

    if (id.equals("down") && board.canMoveDown()) {
      previous.populate(board);
      board.moveDown();
    }

    if (id.equals("right") && board.canMoveRight()) {
      previous.populate(board);
      board.moveRight();
    }

    if (id.equals("left") && board.canMoveLeft()) {
      previous.populate(board);
      board.moveLeft();
    }

    if (!board.canMoveLeft() && !board.canMoveRight() && !board.canMoveUp() &&
        !board.canMoveDown()) {
      redirectAttributes.addFlashAttribute("status", "No more move. Game over.");
    }
    return "redirect:/";
  }

  @GetMapping("/reset")
  public String getReset() {
    board = new Board(4);
    return "redirect:/";
  }

  @GetMapping("/undo")
  public String getUndo() {
    board.populate(previous);
    return "redirect:/";
  }
}
