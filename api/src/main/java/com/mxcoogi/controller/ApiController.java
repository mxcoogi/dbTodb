package com.mxcoogi.controller;

import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.services.DatasourceConnectionTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final DatasourceConnectionTestService  datasourceConnectionTestService;

    @PostMapping("/connection")
    public ResponseEntity<?> connection(@RequestBody ConnectionDto request){
        datasourceConnectionTestService.testConnection(request);
        return ResponseEntity.ok(null);
    }
}
