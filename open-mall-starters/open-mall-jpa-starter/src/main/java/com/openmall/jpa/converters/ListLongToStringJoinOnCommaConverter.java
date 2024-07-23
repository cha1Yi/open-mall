package com.openmall.jpa.converters;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wuxuan
 * @since 2024/7/23 16:00:19
 */
public class ListLongToStringJoinOnCommaConverter implements AttributeConverter<List<Long>, String> {
    private final static String COMMA = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return Optional.ofNullable(attribute)
                .map(item -> item.stream().map(String::valueOf).collect(Collectors.joining(COMMA)))
                .orElse(null);
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .filter(item -> !item.isEmpty())
                .map(item -> Arrays.stream(item.split(COMMA)).map(Long::parseLong).toList())
                .orElse(null);

    }
}
