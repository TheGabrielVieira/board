package br.com.boardproject.dto;

import java.time.OffsetDateTime;

public record CardDetails(Long id,
                          boolean blocked,
                          OffsetDateTime blockedAt,
                          String blockReason,
                          int blockAmount,
                          Long columnId,
                          String columnName
                          ) {
}
