package com.szaszisoft._2048.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BoardRow {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "boardRow", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<BoardCell> row;
  private Integer size;
  @ManyToOne
  private Board board;

  public BoardRow() {
    row = new ArrayList<BoardCell>();
  }

  public BoardRow(List<BoardCell> row, Integer size, Board board) {
    this.row = row;
    this.size = size;
    this.board = board;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public List<BoardCell> getRow() {
    return row;
  }

  public void setRow(List<BoardCell> row) {
    this.row = row;
  }
  public void addToRow(BoardCell boardCell) {
    row.add(boardCell);
  }
  public void setRowItem(Integer index, BoardCell boardCell) {
    row.set(index, boardCell);
  }
  public BoardCell getRowItem(Integer index) {
    return row.get(index);
  }
}
