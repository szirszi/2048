package com.szaszisoft._2048.repositories;

import com.szaszisoft._2048.models.BoardCell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCellRepository extends JpaRepository<BoardCell, Long> {

}
