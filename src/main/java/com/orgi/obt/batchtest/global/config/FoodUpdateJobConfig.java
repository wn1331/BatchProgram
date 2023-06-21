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

    // Entity Manager Factory를 사용하여 JPA를 통한 데이터베이스 접근을 관리합니다.
    private final EntityManagerFactory entityManagerFactory;

    // Spring Batch의 Job을 저장하고 관리하는데 사용되는 JobRepository입니다.
    private final JobRepository jobRepository;

    // 한 번에 처리될 데이터 항목의 수를 설정합니다. 이 숫자가 너무 크면 메모리 이슈를 겪을 수 있고, 너무 작으면 성능이 느려질 수 있습니다.
    private final static int CHUNK_SIZE = 50;

    // "foodUpdateJob"이라는 이름의 Job을 생성하는 Bean입니다.
    @Bean
    public Job foodStatusUpdateJob(Step foodUpdateStep) {
        //JobBuilder에 foodUpdateJob 메시지? 보냄
        return new JobBuilder("foodUpdateJob", jobRepository).start(foodUpdateStep).build();
    }

    // "foodStatusUpdateStep"이라는 이름의 Step을 생성하는 Bean입니다.
    // Step은 Job을 이루는 주요한 단계로서, Reader, Processor, Writer를 포함합니다
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

    // JPA를 사용하여 데이터베이스에서 Food 엔티티를 읽는 Reader를 생성하는 Bean입니다.
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


    // JPA를 사용하여 데이터베이스에 Food 엔티티를 쓰는 Writer를 생성하는 Bean입니다.
    @Bean
    @StepScope
    public ItemWriter<Food> foodStatusUpdateWriter() {
        return new JpaItemWriterBuilder<Food>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    // Food 엔티티를 처리하는 Processor를 생성하는 Bean입니다.
    @Bean
    public FoodItemProcessor foodStatusUpdateProcessor() {
        return new FoodItemProcessor();
    }


}
