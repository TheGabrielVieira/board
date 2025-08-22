package br.com.boardproject.persistence.dao;

import br.com.boardproject.dto.CardDetails;
import lombok.AllArgsConstructor;

import java.sql.Connection;

@AllArgsConstructor
public class CardDao {

    private Connection connection;

    public CardDetails findById(final Long id){
        return null;
    }
}
