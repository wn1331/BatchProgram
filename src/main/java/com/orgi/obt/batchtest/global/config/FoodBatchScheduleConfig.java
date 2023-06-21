package com.orgi.obt.batchtest.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class FoodBatchScheduleConfig {

    private final JobLauncher jobLauncher;
    private final Job foodProcessingJob;

    /**
     * "0 * * * * ?" -> 1분마다 "0 0 0 * * ?" -> 자정마다 "0/10 * * * * *" -> 10초마다
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void runFoodBatchJob()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("version", "1.0")
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(foodProcessingJob, jobParameters);
    }
}
