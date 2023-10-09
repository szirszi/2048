package com.szaszisoft._2048.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BoardCell {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer cellValue;
  @ManyToOne
  private BoardRow boardRow;

  public BoardCell() {
    cellValue = 0;
  }

  public BoardCell(Integer cellValue, BoardRow boardRow) {
    this.cellValue = cellValue;
    this.boardRow = boardRow;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Integer getCellValue() {
    return cellValue;
  }

  public void setCellValue(Integer cellValue) {
    this.cellValue = cellValue;
  }

  public BoardRow getBoardRow() {
    return boardRow;
  }

  public void setBoardRow(BoardRow boardRow) {
    this.boardRow = boardRow;
  }
}
