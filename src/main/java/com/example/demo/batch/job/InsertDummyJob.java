package com.example.demo.batch.job;

import com.example.demo.batch.reader.DummyFromNumReader;
import com.example.demo.domain.Dummy;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class InsertDummyJob {

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job insertDummyFromNumJob(JobRepository jobRepository, Step readerProcessorWriterStep) {
        return new JobBuilder("InsertDummyFromNumJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(readerProcessorWriterStep)
            .build();
    }

    @Bean
    public Step insertDummyFromNumStep(JobRepository jobRepository,
                                          ItemReader<Dummy> reader,
                                          ItemWriter<Dummy> writer) {
        return new StepBuilder("InsertDummyFromNumStep", jobRepository)
            .<Dummy, Dummy>chunk(1000, transactionManager)
            .reader(reader)
            .writer(writer)
            .build();
    }

    @Bean
    @StepScope
    public ItemReader<Dummy> reader(
        @Value("#{jobParameters['num']}") Integer num
    ) {
        return new DummyFromNumReader(num);
    }

    @Bean
    public JdbcBatchItemWriter<Dummy> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Dummy>()
            .dataSource(dataSource)
            .sql("INSERT INTO dummy (one, two, three, four, five, six, seven) " +
                "VALUES (:one, :two, :three, :four, :five, :six, :seven)")
            .beanMapped()
            .build();
    }
}
