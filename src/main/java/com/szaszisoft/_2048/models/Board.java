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
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<BoardRow> playBoard;
  @ManyToOne
  private Game game;
  private Integer size;
  private BoardType boardType;
  private int score;

  public Board() {
    playBoard = new ArrayList<>();
    score = 0;
  }

  public Board(Integer size) {
    playBoard = new ArrayList<>();
    this.size = size;
    score = 0;
    for (int y = 0; y < size; y++) {
      BoardRow boardRow = new BoardRow(new ArrayList<>(), this);
      for (int x = 0; x < size; x++) {
        BoardCell boardCell = new BoardCell();
        boardCell.setBoardRow(boardRow);
        boardCell.setCellValue(0);
        boardRow.addToRow(boardCell);
      }
      playBoard.add(boardRow);
    }
    addNewNumber(2);

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<BoardRow> getPlayBoard() {
    return playBoard;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public BoardType getBoardType() {
    return boardType;
  }

  public void setBoardType(BoardType boardType) {
    this.boardType = boardType;
  }

  public Integer getElementXY(Integer x, Integer y) {
    return playBoard.get(y).getRowItem(x).getCellValue();
  }

  public void setElementXY(Integer x, Integer y, Integer value) {
    BoardRow boardRow = playBoard.get(y);
    BoardCell rowItem = boardRow.getRowItem(x);
    rowItem.setCellValue(value);
  }

  public int getScore() {
    return score;
  }

  boolean allContainsNumber() {
    int i = 0;
    int j = 0;
    while (getElementXY(i, j) != 0) {
      i++;
      if (i >= size) {
        i = 0;
        j++;
      }
      if (j >= size) {
        return true;
      }
    }
    return false;
  }

  public Integer[] addNewNumber(Integer number) {
    if (allContainsNumber()) {
      return null;
    }
    int x;
    int y;
    do {
      x = ((int) (Math.random() * size * 2) - size);
      x = (x < 0 ? 0 : (x >= size ? size - 1 : x));
      y = ((int) (Math.random() * size * 2) - size);
      y = (y < 0 ? 0 : (y >= size ? size - 1 : y));
    } while (getElementXY(x, y) != 0);
    setElementXY(x, y, number);
    Integer[] newCoordinatesXY = new Integer[2];
    newCoordinatesXY[0]=x;
    newCoordinatesXY[1]=y;
    return newCoordinatesXY;
  }

  public String representAsString() {
    StringBuilder boardAsString = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        boardAsString.append(getElementXY(x, y));
        boardAsString.append("\t\t");
      }
      boardAsString.append("\n");
    }
    return boardAsString.toString();
  }

  public boolean canMoveUp() {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size - 1; y++) {
        if (getElementXY(x, y).equals(0) && !getElementXY(x, y + 1).equals(0)) {
          return true;
        }
        if (getElementXY(x, y).equals(getElementXY(x, y + 1)) && !getElementXY(x, y).equals(0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean canMoveDown() {
    for (int x = 0; x < size; x++) {
      for (int y = size - 2 ; y >= 0; y--) {
        if (getElementXY(x, y + 1).equals(0) && !getElementXY(x, y).equals(0)) {
          return true;
        }
        if (getElementXY(x, y).equals(getElementXY(x, y + 1)) && !getElementXY(x, y).equals(0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean canMoveLeft() {
    for (int y = 0; y < size; y++) {
      for (int x = size - 2 ; x >= 0; x--) {
        if (getElementXY(x, y).equals(0) && !getElementXY(x + 1, y).equals(0)) {
          return true;
        }
        if (getElementXY(x, y).equals(getElementXY(x + 1, y)) && !getElementXY(x, y).equals(0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean canMoveRight() {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size - 1; x++) {
        if (getElementXY(x + 1, y).equals(0) && !getElementXY(x, y).equals(0)) {
          return true;
        }
        if (getElementXY(x, y).equals(getElementXY(x + 1, y)) && !getElementXY(x, y).equals(0)) {
          return true;
        }
      }
    }
    return false;
  }

  public void moveUp() {
    for (int x = 0; x < size; x++) {
      List<Integer> tempList = new ArrayList<>();
      for (int y = 0; y < size; y++) {
        if (getElementXY(x, y) != 0) {
          tempList.add(getElementXY(x, y));
        }
      }
      for (int i = 1; i < tempList.size(); i++) {
        if (tempList.get(i).equals(tempList.get(i - 1))) {
          tempList.set(i - 1, tempList.get(i) * 2);
          score += tempList.get(i - 1);
          tempList.remove(i);
        }
      }
      for (int y = 0; y < size; y++) {
        if (y < tempList.size()) {
          setElementXY(x, y, tempList.get(y));
        } else {
          setElementXY(x, y, 0);
        }
      }
    }
  }

  public void moveDown() {
    for (int x = 0; x < size; x++) {
      List<Integer> tempList = new ArrayList<>();
      for (int y = 0; y < size; y++) {
        if (getElementXY(x, y) != 0) {
          tempList.add(getElementXY(x, y));
        }
      }
      for (int i = tempList.size() - 2; i >= 0; i--) {
        if (tempList.get(i).equals(tempList.get(i + 1))) {
          tempList.set(i, tempList.get(i) * 2);
          score += tempList.get(i);
          tempList.remove(i + 1);
        }
      }
      for (int y = size - 1, i = tempList.size() - 1; y >= 0; y--, i --) {
        if (i >= 0) {
          setElementXY(x, y, tempList.get(i));
        } else {
          setElementXY(x, y, 0);
        }
      }
    }
  }

  public void moveLeft() {
    for (int y = 0; y < size; y++) {
      List<Integer> tempList = new ArrayList<>();
      for (int x = 0; x < size; x++) {
        if (getElementXY(x, y) != 0) {
          tempList.add(getElementXY(x, y));
        }
      }
      for (int i = 1; i < tempList.size(); i++) {
        if (tempList.get(i).equals(tempList.get(i - 1))) {
          tempList.set(i - 1, tempList.get(i) * 2);
          score += tempList.get(i - 1);
          tempList.remove(i);
        }
      }
      for (int x = 0; x < size; x++) {
        if (x < tempList.size()) {
          setElementXY(x, y, tempList.get(x));
        } else {
          setElementXY(x, y, 0);
        }
      }
    }
  }

  public void moveRight() {
    for (int y = 0; y < size; y++) {
      List<Integer> tempList = new ArrayList<>();
      for (int x = 0; x < size; x++) {
        if (getElementXY(x, y) != 0) {
          tempList.add(getElementXY(x, y));
        }
      }

      for (int i = tempList.size() - 1; i > 0; i--) {
        if (tempList.get(i).equals(tempList.get(i - 1))) {
          tempList.set(i, tempList.get(i) * 2);
          score += tempList.get(i);
          tempList.remove(i - 1);
          tempList.add(0,0);
        }
      }
      for (int x = size - 1, i = tempList.size() - 1; i >= 0; x--, i--) {
        setElementXY(x, y, tempList.get(i));
      }
      for (int x = 0; x < size - tempList.size(); x++) {
        setElementXY(x, y, 0);
      }
    }
  }

  public void populate(Board otherBoard) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        setElementXY(x, y, otherBoard.getElementXY(x, y));
      }
    }
    score = otherBoard.score;
  }
}
