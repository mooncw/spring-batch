package com.example.demo.batch.job;

import com.example.demo.batch.dto.DummyDto;
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
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UpdateDummyJob {

    private final PlatformTransactionManager transactionManager;
    private final DummyRepository dummyRepository;

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
        ItemProcessor<Dummy, DummyDto> updateDummyOneHundredThousandProcessor,
        JdbcBatchItemWriter<DummyDto> updateDummyOneHundredThousandWriter
    ) {
        return new StepBuilder("updateDummyOneHundredThousandStep", jobRepository)
            .<Dummy, DummyDto>chunk(10000, transactionManager)
            .reader(updateDummyOneHundredThousandReader)
            .processor(updateDummyOneHundredThousandProcessor)
            .writer(updateDummyOneHundredThousandWriter)
            .build();
    }

    @Bean()
    public RepositoryItemReader<Dummy> updateDummyOneHundredThousandReader() {
        return new RepositoryItemReaderBuilder<Dummy>()
            .name("updateDummyOneHundredThousandReader")
            .repository(dummyRepository)
            .methodName("findAll")
            .pageSize(10000)
            .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
            .build();
    }

    @Bean()
    public ItemProcessor<Dummy, DummyDto> updateDummyOneHundredThousandProcessor() {
        return item -> {
            DummyDto dummyDto = DummyDto.from(item);
            dummyDto.update();
            return dummyDto;
        };
    }

    @Bean()
    public JdbcBatchItemWriter<DummyDto> updateDummyOneHundredThousandWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<DummyDto>()
            .dataSource(dataSource)
            .sql("UPDATE dummy SET one = :one, two = :two, three = :three, four = :four, five = :five, six = :six, seven = :seven " +
                "WHERE id = :id")
            .beanMapped()
            .build();
    }
}
