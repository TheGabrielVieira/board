package br.com.boardproject.service;

import br.com.boardproject.dto.BoardColumnInfoDTO;
import br.com.boardproject.exception.CardBlockedException;
import br.com.boardproject.exception.CardFinishedException;
import br.com.boardproject.exception.EntityNotFoundException;
import br.com.boardproject.persistence.dao.CardDAO;
import br.com.boardproject.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.boardproject.persistence.entity.BoardColumnKindEnum.FINAL;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity create(final CardEntity entity) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card id %s nao foi encontrado".formatted(cardId))
            );
            if (dto.blocked()){
                var message = "O card %s esta bloqueado, e ncessario desbloquea-lo para mover".formatted(cardId);
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst().orElseThrow();
            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }
}
