package com.example.sendmail;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class JobRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired   
    private Job jobA;

    @Override    
    public void run(String... args) throws Exception {

    JobParameters jobParameters =
          new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

    jobLauncher.run(jobA, jobParameters);
    log.info("JOB Execution completed!");
    }
}