package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.models.BoardType;
import com.szaszisoft._2048.models.Game;
import com.szaszisoft._2048.repositories.GameRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImp implements GameService {
  private final GameRepository gameRepository;
  private final BoardService boardService;

  @Autowired
  public GameServiceImp(GameRepository gameRepository, BoardService boardService) {
    this.gameRepository = gameRepository;
    this.boardService = boardService;
  }

  @Override
  public Game getGameById(long id) {
    return gameRepository.getGameById(id);
  }

  @Override
  public List<Game> findAll() {
    return gameRepository.findAll();
  }

  @Override
  public Game startNewGame(Integer size) {
    Game game = gameRepository.save(new Game());
    Board play = boardService.initializeBoard(size);
    play.setGame(game);
    play.setBoardType(BoardType.PLAY);
    Board undo = boardService.initializeBoard(size);
    undo.setGame(game);
    undo.setBoardType(BoardType.UNDO);
    List<Board> boards = new ArrayList<>();
    boards.add(play);
    boards.add(undo);
    game.setBoards(boards);
    return gameRepository.save(game);
  }
}
