package com.tuanda.controller;

import com.tuanda.dto.request.CreateExamRequestDTO;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @prOjEct studentProject-main
 * @DAtE 4/2/2024
 * @tImE 11:01 AM
 * @AUthOr tuanda52
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController{

    @SneakyThrows
    @PostMapping("/createExam")
    @PreAuthorize("hasAuthority('TEACH')")
    public ResponseEntity<?> createExam(CreateExamRequestDTO createExamRequestDTO){

        return ResponseEntity.ok("aaaa");
    }

}
