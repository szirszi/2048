package com.szaszisoft._2048.repositories;

import com.szaszisoft._2048.models.BoardRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRowRepository extends JpaRepository<BoardRow, Long> {
}
