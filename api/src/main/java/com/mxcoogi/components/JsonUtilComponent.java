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

    private final ObjectMapper objectMapper;

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
    public String fromConnection(ConnectionDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize ConnectionDto", e);
        }
    }

    public String fromMappings(List<MappingDto> mappings) {
        try {
            return objectMapper.writeValueAsString(mappings);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize MappingDto list", e);
        }
    }
}
