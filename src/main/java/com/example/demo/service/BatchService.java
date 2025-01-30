package com.example.demo.service;

import com.example.demo.domain.Dummy;
import com.example.demo.domain.DummyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * for 문을 이용한 작업과 batch 작업 비교를 위한 클래스
 */
@Service
@RequiredArgsConstructor
public class BatchService {

    private final DummyRepository dummyRepository;
    private final JobLauncher jobLauncher;
    private final Map<String, Job> jobMap;

    @Transactional
    public void delete() {
        dummyRepository.deleteAllInBatch();
    }

    @Transactional
    public void insert(Long num) {
        for (int i = 0; i < num; i++) {
            Dummy dummy = Dummy.create();
            dummyRepository.save(dummy);
        }
    }

    public void batchInsert(Long num) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("num", num)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(jobMap.get("insertDummyFromNumJob"), jobParameters);

        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void update() {
        List<Dummy> dummyList = dummyRepository.findAll();

        dummyList.forEach(dummy -> {
            dummy.update();
            dummyRepository.save(dummy);
        });
    }

    public void batchUpdate() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(jobMap.get("updateDummyOneHundredThousandJob"), jobParameters);

        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}
