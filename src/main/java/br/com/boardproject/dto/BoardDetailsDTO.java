package br.com.boardproject.dto;

import br.com.boardproject.persistence.dao.BoardDAO;

import java.util.List;

public record BoardDetailsDTO(Long id,
                              String name,
                              List<BoardColumnDTO> columns) {
}
