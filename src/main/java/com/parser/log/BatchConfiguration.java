package com.parser.log;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    public static final int CHUNK_SIZE = 10;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Event> reader() {
    	CustomJsonLineMapper lineMapper = new CustomJsonLineMapper();

    	
        return new FlatFileItemReaderBuilder<Event>()
            .name("eventItemReader")
            .resource(new ClassPathResource("sample.log"))
            .lineMapper(lineMapper)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Event>() {{
                setTargetType(Event.class);
            }})
            .build();
    }

    @Bean
    public EventProcessor processor() {
        return new EventProcessor();
    }

    //fixme: in current implimentation writer step is skipped and its done as part of processor
    @Bean
    public JdbcBatchItemWriter<Event> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Event>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO event (id, state) VALUES (:id, :state)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Event> writer) {
        return stepBuilderFactory.get("step1- Porcessing started")
            .<Event, Event> chunk(CHUNK_SIZE)
            .reader(reader())
            .processor(processor())
           // .writer(writer)
            .build();
    }
    // end::jobstep[]
}
