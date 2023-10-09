package com.szaszisoft._2048.controllers;

import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {
  private final BoardService boardService;

  @Autowired
  public WebController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/")
  public String getBoard(Model model) {
    Board board = boardService.getBoardById(1L);
    model.addAttribute("board", board);
    return "index";
  }

  @GetMapping("/move/{id}")
  public String getMoveBoard(@PathVariable String id, RedirectAttributes redirectAttributes) {
    Board board = boardService.getBoardById(1L);
    Board previous = boardService.getBoardById(2L);
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
    boardService.save(board);
    boardService.save(previous);
    return "redirect:/";
  }

  @GetMapping("/reset")
  public String getReset() {
    Board board = boardService.getBoardById(1L);
    Board previous = boardService.getBoardById(2L);
    previous.populate(board);
    boardService.save(previous);
    board = boardService.resetBoard(board);
    return "redirect:/";
  }

  @GetMapping("/undo")
  public String getUndo() {
    Board board = boardService.getBoardById(1L);
    Board previous = boardService.getBoardById(2L);
    board.populate(previous);
    boardService.save(board);
    return "redirect:/";
  }
}
