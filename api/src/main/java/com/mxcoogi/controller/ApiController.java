package com.mxcoogi.controller;

import com.mxcoogi.dtos.ConnectionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/connection")
    public ResponseEntity<?> connection(@RequestBody ConnectionDto request){
        System.out.println(request.toString());
        return ResponseEntity.ok(null);
    }
}
