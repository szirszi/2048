package com.szaszisoft._2048.repositories;

import com.szaszisoft._2048.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
  Board getBoardById(Long id);
}
