package com.szaszisoft._2048.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "game", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Board> boards;
  private String gameName;

  public Game() {
  }

  public Game(List<Board> boards) {
    this.boards = boards;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String name) {
    this.gameName = name;
  }
}
