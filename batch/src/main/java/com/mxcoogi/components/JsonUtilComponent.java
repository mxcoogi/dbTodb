package com.mxcoogi.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.dtos.MappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonUtilComponent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConnectionDto toConnection(String json) {
        try {
            return objectMapper.readValue(json, ConnectionDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid connection json", e);
        }
    }

    public List<MappingDto> toMappings(String json) {
        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<MappingDto>>() {}
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid mapping json", e);
        }
    }
}
