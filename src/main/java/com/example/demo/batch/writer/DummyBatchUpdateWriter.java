package com.example.demo.batch.writer;

import com.example.demo.batch.dto.DummyDto;
import com.example.demo.domain.DummyBulkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DummyBatchUpdateWriter implements ItemWriter<DummyDto> {

    private final DummyBulkRepository dummyBulkRepository;

    @Override
    public void write(Chunk<? extends DummyDto> chunk) throws Exception {
        String updateUpperSql = """
            UPDATE dummy 
            SET one = 'ONE', two = 'TWO', three = 'THREE', four = 'FOUR', five = 'FIVE', six = 'SIX', seven = 'SEVEN' 
            WHERE id IN (:idList)
            """;

        String updateLowerSql = """
            UPDATE dummy 
            SET one = 'one', two = 'two', three = 'three', four = 'four', five = 'five', six = 'six', seven = 'seven' 
            WHERE id IN (:idList)
            """;

        Map<String, List<Long>> sqlMap = new HashMap<>();

        for (DummyDto dummyDto : chunk.getItems()) {
            if (dummyDto.isUpper()) {
                sqlMap.computeIfAbsent(updateUpperSql, k -> new ArrayList<>()).add(dummyDto.getId());
            } else {
                sqlMap.computeIfAbsent(updateLowerSql, k -> new ArrayList<>()).add(dummyDto.getId());
            }

        }

        dummyBulkRepository.updateAllFromSqlMap(sqlMap);
    }
}
