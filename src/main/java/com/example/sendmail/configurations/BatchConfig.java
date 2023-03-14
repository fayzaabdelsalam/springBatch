package com.example.sendmail.configurations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.sendmail.BlankLineRecordSeparatorPolicy;
import com.example.sendmail.UserProcessor;
import com.example.sendmail.entities.User;
import com.example.sendmail.models.UserDetails;
import com.example.sendmail.repositories.UserRepository;

import lombok.extern.log4j.Log4j2;

@Configuration
//@EnableBatchProcessing
@Log4j2
public class BatchConfig {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager transactionManager;

	LineMapper<UserDetails> createUserLineMapper()
	{
        DefaultLineMapper<UserDetails> userLineMapper = new DefaultLineMapper<>();
        LineTokenizer userLineTokenizer = createUserLineTokenizer();
        userLineMapper.setLineTokenizer(userLineTokenizer);
        FieldSetMapper<UserDetails> userInformationMapper =	createUserInformationMapper();
        userLineMapper.setFieldSetMapper(userInformationMapper);
        return userLineMapper;
	}
   LineTokenizer createUserLineTokenizer() 
    {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(",");
        studentLineTokenizer.setNames("id","firstName","lastName","email");
        return studentLineTokenizer;
    }
    FieldSetMapper<UserDetails> createUserInformationMapper() 
    {
        BeanWrapperFieldSetMapper<UserDetails> userInformationMapper = new BeanWrapperFieldSetMapper<>();
        userInformationMapper.setTargetType(UserDetails.class);
        return userInformationMapper;
    }
    /*
    @Bean
	public FlatFileItemReader<UserDetails> reader() {
		FlatFileItemReader<UserDetails> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("/test.csv")); 
		reader.setStrict(false);
		createUserLineMapper();
		createUserLineTokenizer();
		createUserInformationMapper();
		reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
		return reader;
		*/
		//////////////////
		
		@Bean
		public FlatFileItemReader<UserDetails> reader() {
		BeanWrapperFieldSetMapper<UserDetails> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(UserDetails.class);
		return new FlatFileItemReaderBuilder<UserDetails>().name("personItemReader")
		.resource(new ClassPathResource("test.csv")).delimited().names("firstName", "lastName","email")
		.fieldSetMapper(mapper).build();
		}
		//////////////////
//		reader.setLinesToSkip(1);
/*
		DefaultLineMapper<UserDetails> userLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("ID", "First Name", "Last Name", "Email");
		userLineMapper.setLineTokenizer(tokenizer);
		FieldSetMapper<UserDetails> userFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		userLineMapper.setFieldSetMapper(userFieldSetMapper);
		reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
		return reader;
		*/

	@Bean
	public ItemProcessor<UserDetails, User> processor() {
		return new UserProcessor();
	}

	@Bean
	public ItemWriter<User> writer() {
		return users -> {
			log.info("Saving User Records: " + users);
			userRepository.saveAll(users);
		};
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository).<UserDetails, User>chunk(5, transactionManager).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public Job jobA() {
		return new JobBuilder("jobA", jobRepository).start(step1()).build();
	}
}
