package com.szaszisoft._2048.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class Board {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
//  @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<BoardRow> playBoard;
  private Integer size;

  public Board() {
    playBoard = new ArrayList<>();
  }

  public Board(Integer size) {
    playBoard = new ArrayList<>();
    this.size = size;
/*
    Integer[][] playBoardArray = new Integer[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        playBoardArray[i][j] = 0;
      }
    }
    playBoard = arrayToPlayBoard(playBoardArray);
*/


    for (int y = 0; y < size; y++) {
      List<Integer> rowContent = new ArrayList<>();
      for (int x = 0; x < size; x++) {
        rowContent.add(0);
      }
      BoardRow boardRow = new BoardRow(rowContent, size, this);
      playBoard.add(boardRow);
    }
    addNewNumber(2);

  }

  public List<BoardRow> getPlayBoard() {
    return playBoard;
  }

  public void setPlayBoard(List<BoardRow> playBoard) {
    this.playBoard = playBoard;
  }

  public void setPlayBoardFromArray(List<BoardRow> playBoard) {
    this.playBoard = playBoard;
  }

  public Integer[][] getPlayBoardAsArray() {
    return this.playBoardTo2DArray();
  }

  public void setPlayBoardFromArray(Integer[][] playBoard) {

    this.playBoard = arrayToPlayBoard(playBoard);
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getElementXY(Integer x, Integer y) {
    return playBoard.get(y).getRowItem(x);
  }

  public void setElementXY(Integer x, Integer y, Integer value) {
    playBoard.get(y).setRowItem(x, value);
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

  public void addNewNumber(Integer number) {
    if (allContainsNumber()) {
      return;
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
        if (getElementXY(x, y).equals(0) && getElementXY(x, y + 1) != 0) {
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
    boolean canMoveDown;
    rotateLeft();
    rotateLeft();
    canMoveDown = canMoveUp();
    rotateLeft();
    rotateLeft();
    return canMoveDown;
  }

  public boolean canMoveLeft() {
    boolean canMoveLeft;
    rotateLeft();
    rotateLeft();
    rotateLeft();
    canMoveLeft = canMoveUp();
    rotateLeft();
    return canMoveLeft;
  }

  public boolean canMoveRight() {
    boolean canMoveRight;
    rotateLeft();
    canMoveRight = canMoveUp();
    rotateLeft();
    rotateLeft();
    rotateLeft();
    return canMoveRight;
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
    addNewNumber(2);
  }

  public void moveDown() {
    rotateLeft();
    rotateLeft();
    moveUp();
    rotateLeft();
    rotateLeft();
  }

  public void moveLeft() {

    rotateLeft();
    rotateLeft();
    rotateLeft();
    moveUp();
    rotateLeft();
  }

  public void moveRight() {
    rotateLeft();
    moveUp();
    rotateLeft();
    rotateLeft();
    rotateLeft();
  }

  public void rotateLeft() {
    Integer[][] current = getPlayBoardAsArray();
    Integer[][] leftRotated = new Integer[size][size];
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        leftRotated[y][x] = current[x][size - 1 - y];
      }
    }
    playBoard = arrayToPlayBoard(leftRotated);
  }

  public void populate(Board otherBoard) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        setElementXY(x, y, otherBoard.getElementXY(x, y));
      }
    }
  }

  public Integer[][] playBoardTo2DArray() {
    Integer[][] outputArray = new Integer[size][size];
    for (int y = 0; y < size; y++) {
      BoardRow currentRow = playBoard.get(y);
      for (int x = 0; x < size; x++) {
        outputArray[y][x] = currentRow.getRow().get(x);
      }

    }
    return outputArray;
  }

  public Board arrayToBoard(Integer[][] boardAsArray) {
    for (int y = 0; y < size; y++) {
      BoardRow currentRow = playBoard.get(y);
      for (int x = 0; x < size; x++) {
        currentRow.setRowItem(x, boardAsArray[y][x]);
      }
      playBoard.set(y, currentRow);
    }
    return this;
  }

  public List<BoardRow> arrayToPlayBoard(Integer[][] boardAsArray) {
    Board board = arrayToBoard(boardAsArray);
    return board.getPlayBoard();
  }
  public void addRowToPlayBoard(BoardRow boardRow) {
    playBoard.add(boardRow);
  }
}
