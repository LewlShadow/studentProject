package com.tuanda.controller;

import com.tuanda.dto.request.CreateExamRequestDTO;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @SneakyThrows
    @PostMapping("/createExam")
    public ResponseEntity<?> createExam(CreateExamRequestDTO createExamRequestDTO){
        return ResponseEntity.ok("aaaa");
    }
}
