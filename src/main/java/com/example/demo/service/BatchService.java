package com.example.demo.service;

import com.example.demo.domain.Dummy;
import com.example.demo.domain.DummyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

/**
 * for 문을 이용한 작업과 batch 작업 비교를 위한 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {

    private final DummyRepository dummyRepository;
    private final JobLauncher jobLauncher;
    private final Job insertDummyFromNumJob;

    // 시간 측정을 위한 객체
    private final StopWatch stopWatch = new StopWatch();

    @Transactional
    public void delete() {
        dummyRepository.deleteAllInBatch();
    }

    @Transactional
    public void insert(Long num) {
        stopWatch.start();

        for (int i = 0; i < num; i++) {
            Dummy dummy = Dummy.create();
            dummyRepository.save(dummy);
        }

        stopWatch.stop();

        log.info(">>> 개별 insert {}개 걸린 시간: {} ms", num, stopWatch.getTotalTimeMillis());
    }

    public void batchInsert(Long num) {
        stopWatch.start();

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("num", num)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(insertDummyFromNumJob, jobParameters);

        } catch (JobExecutionException e) {
            e.printStackTrace();
        }

        stopWatch.stop();

        log.info(">>> batch insert {}개 걸린 시간: {} ms", num, stopWatch.getTotalTimeMillis());
    }
}
