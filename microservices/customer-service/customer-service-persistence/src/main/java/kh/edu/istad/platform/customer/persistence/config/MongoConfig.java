package kh.edu.istad.platform.customer.persistence.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import kh.edu.istad.common.domain.valueobject.CustomerId;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;
import java.util.UUID;

@Configuration
@EnableReactiveMongoAuditing
public class MongoConfig {

    @Bean
    public MongoClient reactiveMongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://mongod:mongod@localhost:2027/istad_customer?authSource=admin");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new CustomerIdToStringConverter(),
                new StringToCustomerIdConverter()
        ));
    }

    @WritingConverter
    public static class CustomerIdToStringConverter implements Converter<CustomerId, String> {
        @Override
        public String convert(CustomerId source) {
            return source != null && source.value() != null ? source.value().toString() : null;
        }
    }

    @ReadingConverter
    public static class StringToCustomerIdConverter implements Converter<String, CustomerId> {
        @Override
        public CustomerId convert(String source) {
            return source != null && !source.isEmpty() ? new CustomerId(UUID.fromString(source)) : null;
        }
    }
}