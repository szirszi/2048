package com.szaszisoft._2048.dtos;

public class SelectGameDto {
  private Long gameId;
  private Integer size;

  public SelectGameDto() {
  }

  public SelectGameDto(Long gameid) {
    this.gameId = gameid;
  }

  public Long getGameId() {
    return gameId;
  }

  public void setGameId(Long gameid) {
    this.gameId = gameid;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
