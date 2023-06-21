package com.orgi.obt.batchtest.global.config;

import com.orgi.obt.batchtest.application.FoodItemProcessor;
import com.orgi.obt.batchtest.domain.Food;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FoodUpdateJobConfig {

    //??
    private final EntityManagerFactory entityManagerFactory;
    //batch에서 job가져옴
    private final JobRepository jobRepository;

    private final static int CHUNK_SIZE = 50;

    @Bean
    public Job foodStatusUpdateJob(Step foodUpdateStep) {
        //JobBuilder에 foodUpdateJob 메시지? 보냄
        return new JobBuilder("foodUpdateJob", jobRepository).start(foodUpdateStep).build();
    }

    @Bean
    @JobScope
    public Step FoodStatusUpdateStep(PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("foodStatusUpdateStep", jobRepository)
            .<Food, Food>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(foodStatusUpdateReader())
            .processor(foodStatusUpdateProcessor())
            .writer(foodStatusUpdateWriter())
            .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Food> foodStatusUpdateReader() {
        //JPQL 쿼리 사용해야 한다.
        String query = "select f from Food f where f.status = '정상' ";
        return new JpaCursorItemReaderBuilder<Food>()
            .name("foodStatusUpdateReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(query)
            .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Food> foodStatusUpdateWriter() {
        return new JpaItemWriterBuilder<Food>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public FoodItemProcessor foodStatusUpdateProcessor() {
        return new FoodItemProcessor();
    }


}
