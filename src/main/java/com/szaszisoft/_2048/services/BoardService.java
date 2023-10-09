package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Board;

public interface BoardService {
  Board initializeBoard(Integer size);
  Board getBoardById(Long id);

  Board save(Board board);
  Board resetBoard(Board board);
}
