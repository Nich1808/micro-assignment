package kh.edu.istad.platform.customer.persistence.converter;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class CustomerIdConverters {

    @ReadingConverter
    @Component
    public static class StringToCustomerIdConverter implements Converter<String, CustomerId> {
        @Override
        public CustomerId convert(String source) {
            return source != null ? new CustomerId(UUID.fromString(source)) : null;
        }
    }

    @WritingConverter
    @Component
    public static class CustomerIdToStringConverter implements Converter<CustomerId, String> {
        @Override
        public String convert(CustomerId source) {
            return source != null ? source.value().toString() : null;
        }
    }
}