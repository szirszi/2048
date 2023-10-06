package com.szaszisoft._2048.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//@Entity
public class BoardRow {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
//  @ElementCollection
  private List<Integer> row = new ArrayList<>();
  private Integer size;
//  @ManyToOne
  private Board board;

  public BoardRow() {
  }

  public BoardRow(List<Integer> row, Integer size, Board board) {
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

  public List<Integer> getRow() {
    return row;
  }

  public void setRow(List<Integer> row) {
    this.row = row;
  }
  public void addToRow(Integer integer) {
    row.add(integer);
  }
  public void setRowItem(Integer index, Integer integer) {
    row.set(index, integer);
  }
  public Integer getRowItem(Integer index) {
    return row.get(index);
  }
}
