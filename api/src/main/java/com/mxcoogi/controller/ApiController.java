package com.mxcoogi.controller;

import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.services.DatasourceConnectionTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final DatasourceConnectionTestService  datasourceConnectionTestService;

    @PostMapping("/connection")
    public ResponseEntity<?> connection(@RequestBody ConnectionDto request){
        Object data = datasourceConnectionTestService.testConnection(request);
        return ResponseEntity.ok(data);
    }
    @PostMapping("/job")
    public ResponseEntity<?> job(@RequestBody List<Map<String, Object>> request){
        System.out.println(request);
        return ResponseEntity.ok(request);
    }
}
