package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Game;
import com.szaszisoft._2048.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImp implements GameService {
  private final GameRepository gameRepository;

  @Autowired
  public GameServiceImp(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @Override
  public Game getGameById(long id) {
    return gameRepository.getGameById(id);
  }
}
