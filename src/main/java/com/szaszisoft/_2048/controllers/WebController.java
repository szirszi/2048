package com.szaszisoft._2048.controllers;

import com.szaszisoft._2048.dtos.SelectGameDto;
import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.models.BoardType;
import com.szaszisoft._2048.models.Game;
import com.szaszisoft._2048.services.BoardService;
import com.szaszisoft._2048.services.GameService;
import io.micrometer.core.annotation.Timed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {
  private final BoardService boardService;
  private final GameService gameService;

  @Autowired
  public WebController(BoardService boardService, GameService gameService) {
    this.boardService = boardService;
    this.gameService = gameService;
  }

  @GetMapping("/")
  public String getMain(@CookieValue(value = "gameId", defaultValue = "0") Long gameId,
                        Model model,
                        SelectGameDto selectGameDto) {
    model.addAttribute("games", gameService.findAll());
    model.addAttribute(selectGameDto);
    model.addAttribute("gameId", gameId);
    model.addAttribute("jvmRoute", System.getenv("TOMCAT_JVMROUTE"));
    return "index";
  }

  @PostMapping("/game")
  public String postGame(@ModelAttribute SelectGameDto selectGameDto,
                         HttpServletResponse httpServletResponse) {
    Long gameId = selectGameDto.getGameId();
    if (gameId == 0) {
      gameId =
          gameService.startNewGame(selectGameDto.getSize(), selectGameDto.getGameName()).getId();
    }
    Cookie cookie = new Cookie("gameId", "" + gameId);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    httpServletResponse.addCookie(cookie);
    return "redirect:/game";
  }

  @GetMapping("/game")
  public String getBoard(Model model,
                         @CookieValue(value = "gameId", defaultValue = "0") Long gameId) {
    if (gameId == 0) {
      return "redirect:/";
    }
    Game game = gameService.getGameById(gameId);
    if (game == null) {
      return "redirect:/";
    }
    Board board = selectBoard(game, BoardType.PLAY);
    model.addAttribute("board", board);
    model.addAttribute("gameName", game.getGameName());
    model.addAttribute("jvmRoute", System.getenv("TOMCAT_JVMROUTE"));

    System.out.println(board.representAsString());
    System.out.println("SCORE = " + board.getScore());
    return "game";
  }

  @GetMapping("/move/{id}")
  @Timed(value = "move.controller", description = "Time taken to move")
  public String getMoveBoard(@PathVariable String id,
                             @CookieValue(value = "gameId", defaultValue = "0") Long gameId,
                             RedirectAttributes redirectAttributes) {
    if (gameId == 0) {
      return "redirect:/";
    }
    Game game = gameService.getGameById(gameId);
    if (game == null) {
      return "redirect:/";
    }

    Board board = selectBoard(game, BoardType.PLAY);

    Integer[] newCellXY = new Integer[2];
    Board previous = selectBoard(game, BoardType.UNDO);

    if (id.equals("up") && board.canMoveUp()) {
      previous.populate(board);
      board.moveUp();
      newCellXY = board.addNewNumber(2);
    }

    if (id.equals("down") && board.canMoveDown()) {
      previous.populate(board);
      board.moveDown();
      newCellXY = board.addNewNumber(2);
    }

    if (id.equals("right") && board.canMoveRight()) {
      previous.populate(board);
      board.moveRight();
      newCellXY = board.addNewNumber(2);
    }

    if (id.equals("left") && board.canMoveLeft()) {
      previous.populate(board);
      board.moveLeft();
      newCellXY = board.addNewNumber(2);
    }

    boardService.save(board);
    boardService.save(previous);
    redirectAttributes.addFlashAttribute("newCellX", newCellXY[0]);
    redirectAttributes.addFlashAttribute("newCellY", newCellXY[1]);
    redirectAttributes.addFlashAttribute("gameName", game.getGameName());
    return "redirect:/game";
  }

  @GetMapping("/reset")
  public String getReset(@CookieValue(value = "gameId", defaultValue = "0") Long gameId) {
    if (gameId == 0) {
      return "redirect:/";
    }
    Game game = gameService.getGameById(gameId);
    if (game == null) {
      return "redirect:/";
    }

    Board board = selectBoard(game, BoardType.PLAY);
    Board previous = selectBoard(game, BoardType.UNDO);
    previous.populate(board);
    boardService.save(previous);
    board = boardService.resetBoard(board);
    return "redirect:/game";
  }

  @GetMapping("/undo")
  public String getUndo(@CookieValue(value = "gameId", defaultValue = "0") Long gameId,
                        RedirectAttributes redirectAttributes) {
    if (gameId == 0) {
      return "redirect:/";
    }
    Game game = gameService.getGameById(gameId);
    if (game == null) {
      return "redirect:/";
    }

    Board board = selectBoard(game, BoardType.PLAY);
    Board previous = selectBoard(game, BoardType.UNDO);
    board.populate(previous);
    boardService.save(board);
    redirectAttributes.addFlashAttribute("canUndo", 0);
    return "redirect:/game";
  }

  private Board selectBoard(Game game, BoardType boardType) {
    return game.getBoards().stream()
        .filter(b -> b.getBoardType() == boardType)
        .findFirst().orElse(null);
  }
}
