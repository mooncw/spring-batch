package com.example.demo.controller;

import com.example.demo.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
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

    // 시간 측정을 위한 객체
    private final StopWatch stopWatch = new StopWatch();

    // 사용하기 쉬운 Get 사용
    @GetMapping("/delete")
    public String delete() {
        batchService.delete();
        return "delete OK";
    }

    // 사용하기 쉬운 Get 사용
    @GetMapping("/insert/{num}")
    public String insert(
        @PathVariable Long num
    ) {
        stopWatch.start();
        batchService.insert(num);
        stopWatch.stop();
        log.info(">>> 개별 insert {}개 걸린 시간: {} ms", num, stopWatch.getTotalTimeMillis());
        return "insert OK";
    }

    // 사용하기 쉬운 Get 사용
    @GetMapping("/insert/batch/{num}")
    public String batchInsert(
        @PathVariable Long num
    ) {
        stopWatch.start();
        batchService.batchInsert(num);
        stopWatch.stop();
        log.info(">>> batch insert {}개 걸린 시간: {} ms", num, stopWatch.getTotalTimeMillis());
        return "batch insert OK";
    }

    // 사용하기 쉬운 Get 사용
    @GetMapping("/update")
    public String update() {
        stopWatch.start();
        batchService.update();
        stopWatch.stop();
        log.info(">>> 개별 update 100000개 걸린 시간: {} ms", stopWatch.getTotalTimeMillis());
        return "update OK";
    }

    // 사용하기 쉬운 Get 사용
    @GetMapping("/update/batch")
    public String batchUpdate() {
        stopWatch.start();
        batchService.batchUpdate();
        stopWatch.stop();
        log.info(">>> batch update 100000개 걸린 시간: {} ms", stopWatch.getTotalTimeMillis());
        return "batch update OK";
    }
}
