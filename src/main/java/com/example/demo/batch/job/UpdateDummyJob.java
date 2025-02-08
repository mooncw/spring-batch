package com.example.demo.batch.job;

import com.example.demo.batch.dto.DummyDto;
import com.example.demo.batch.writer.DummyBatchUpdateWriter;
import com.example.demo.domain.Dummy;
import com.example.demo.domain.DummyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UpdateDummyJob {

    private final PlatformTransactionManager transactionManager;
    private final DummyRepository dummyRepository;
    private final DummyBatchUpdateWriter dummyBatchUpdateWriter;

    @Bean
    public Job updateDummyOneHundredThousandJob(JobRepository jobRepository, Step updateDummyOneHundredThousandStep) {
        return new JobBuilder("updateDummyOneHundredThousandJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(updateDummyOneHundredThousandStep)
            .build();
    }

    @Bean
    public Step updateDummyOneHundredThousandStep(
        JobRepository jobRepository,
        RepositoryItemReader<Dummy> updateDummyOneHundredThousandReader,
        ItemProcessor<Dummy, DummyDto> updateDummyOneHundredThousandProcessor
    ) {
        return new StepBuilder("updateDummyOneHundredThousandStep", jobRepository)
            .<Dummy, DummyDto>chunk(10000, transactionManager)
            .reader(updateDummyOneHundredThousandReader)
            .processor(updateDummyOneHundredThousandProcessor)
            .writer(dummyBatchUpdateWriter)
            .build();
    }

    @Bean
    public RepositoryItemReader<Dummy> updateDummyOneHundredThousandReader() {
        return new RepositoryItemReaderBuilder<Dummy>()
            .name("updateDummyOneHundredThousandReader")
            .repository(dummyRepository)
            .methodName("findAll")
            .pageSize(10000)
            .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
            .build();
    }

    @Bean
    public ItemProcessor<Dummy, DummyDto> updateDummyOneHundredThousandProcessor() {
        return item -> {
            DummyDto dummyDto = DummyDto.from(item);
            dummyDto.update();
            return dummyDto;
        };
    }
}
