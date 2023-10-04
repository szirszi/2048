package com.szaszisoft._2048.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private Integer[][] board;
  private Integer size;

  public Board() {
  }

  public Board(Integer size) {
    this.size = size;
    board = new Integer[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = 0;
      }
    }
    addNewNumber(2);
  }

  public Integer[][] getBoard() {
    return board;
  }

  public void setBoard(Integer[][] board) {
    this.board = board;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getElementXY(Integer x, Integer y) {
    return board[y][x];
  }

  public void setElementXY(Integer x, Integer y, Integer value) {
    board[y][x] = value;
  }

  boolean allContainsNumber() {
    int i = 0;
    int j = 0;
    while (board[i][j] != 0) {
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

  public Board addNewNumber(Integer number) {
    if (allContainsNumber()) {
      return this;
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
    return this;
  }

  public String representAsString() {
    String boardAsString = "";
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        boardAsString += "" + getElementXY(x, y);
      }
      boardAsString += "\n";
    }
    return boardAsString;
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
    boolean canMoveDown = false;
    rotateLeft();
    rotateLeft();
    canMoveDown = canMoveUp();
    rotateLeft();
    rotateLeft();
    return canMoveDown;
  }
  public boolean canMoveLeft() {
    boolean canMoveLeft = false;
    rotateLeft();
    rotateLeft();
    rotateLeft();
    canMoveLeft = canMoveUp();
    rotateLeft();
    return canMoveLeft;
  }
  public boolean canMoveRight() {
    boolean canMoveRight = false;
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
    Integer[][] current = getBoard();
    Integer[][] leftRotated = new Integer[size][size];
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        leftRotated[y][x] = current[x][size - 1 - y];
      }
    }
    board = leftRotated;
    System.out.println(representAsString());
  }

  public void populate(Board otherBoard) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        setElementXY(x, y, otherBoard.getElementXY(x, y));
      }
    }
  }
}