package com.example.demo.controller;

import com.example.demo.annotation.LogExecutionTime;
import com.example.demo.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배치 트리거용 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    // 사용하기 쉬운 Get 사용
    @GetMapping("/delete")
    public String delete() {
        batchService.delete();
        return "delete OK";
    }

    // 사용하기 쉬운 Get 사용
    @LogExecutionTime(value = "individual insert")
    @GetMapping("/insert/{num}")
    public String insert(
        @PathVariable Long num
    ) {
        batchService.insert(num);
        return "insert OK";
    }

    // 사용하기 쉬운 Get 사용
    @LogExecutionTime(value = "batch insert")
    @GetMapping("/insert/batch/{num}")
    public String batchInsert(
        @PathVariable Long num
    ) {
        batchService.batchInsert(num);
        return "batch insert OK";
    }

    // 사용하기 쉬운 Get 사용
    @LogExecutionTime(value = "individual update")
    @GetMapping("/update")
    public String update() {
        batchService.update();
        return "update OK";
    }

    // 사용하기 쉬운 Get 사용
    @LogExecutionTime(value = "batch update")
    @GetMapping("/update/batch")
    public String batchUpdate() {
        batchService.batchUpdate();
        return "batch update OK";
    }
}
