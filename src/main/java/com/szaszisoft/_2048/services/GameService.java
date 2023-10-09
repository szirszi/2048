package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Game;
import java.util.List;

public interface GameService {
  Game getGameById(long id);
  List<Game> findAll();
  Game startNewGame(Integer size);
}
