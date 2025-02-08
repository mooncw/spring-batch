package com.example.demo.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DummyBulkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void updateAllFromSqlMap(Map<String, List<Long>> sqlMap) {

        for (Map.Entry<String, List<Long>> element : sqlMap.entrySet()) {
            jdbcTemplate.update(element.getKey(), new MapSqlParameterSource("idList", element.getValue()));
        }
    }
}
