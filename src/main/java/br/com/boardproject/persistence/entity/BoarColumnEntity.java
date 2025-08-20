package br.com.boardproject.persistence.entity;

import lombok.Data;

@Data
public class BoarColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
}
