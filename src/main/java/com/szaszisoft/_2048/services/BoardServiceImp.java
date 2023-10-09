package com.szaszisoft._2048.services;

import com.szaszisoft._2048.models.Board;
import com.szaszisoft._2048.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImp implements BoardService {
  private final BoardRepository boardRepository;

  @Autowired
  public BoardServiceImp(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  @Override
  public Board initializeBoard(Integer size) {
    Board board = new Board(4);
  return boardRepository.save(board);
  }

  @Override
  public Board getBoardById(Long id) {
    return boardRepository.getBoardById(id);
  }

  @Override
  public Board save(Board board) {
    return boardRepository.save(board);
  }

  @Override
  public Board resetBoard(Board board) {
    Board emptyBoard = new Board(board.getSize());
    board.populate(emptyBoard);
    return boardRepository.save(board);
  }
}
