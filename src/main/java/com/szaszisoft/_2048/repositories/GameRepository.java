package com.szaszisoft._2048.repositories;

import com.szaszisoft._2048.models.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
  Game getGameById(Long id);
  List<Game> findAll();
}
