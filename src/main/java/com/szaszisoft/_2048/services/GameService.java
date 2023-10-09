package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Game;

public interface GameService {
  Game getGameById(long id);
}
