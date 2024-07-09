package com.madeeasy.controller;

import com.madeeasy.dto.request.ExamRequestDTO;
import com.madeeasy.dto.request.submit.ExamSubmittedRequestDTO;
import com.madeeasy.dto.response.ExamResponseDTO;
import com.madeeasy.dto.response.submit.ExamSubmittedResponseDTO;
import com.madeeasy.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/exam-service")
@RequiredArgsConstructor
public class ExamServiceController {

    private final ExamService examService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createExam(@RequestBody ExamRequestDTO exam) {
        this.examService.createExam(exam);
        return ResponseEntity.status(HttpStatus.CREATED).body("Exam created successfully !!");
    }

    @GetMapping(path = "/get-exam-by-title/{title}")
    public ResponseEntity<?> getExamByTitle(@PathVariable("title") String title) {
        List<ExamResponseDTO> savedExamByTitle = this.examService.getExamByTitle(title);
        if (!savedExamByTitle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(savedExamByTitle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found !");
    }

    @GetMapping(path = "/get-all-exams")
    public ResponseEntity<?> getAllExams() {
        List<ExamResponseDTO> allExams = this.examService.getAllExams();
        if (!allExams.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(allExams);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found !");
    }

    @GetMapping(path = "/get-exam-by-id/{examId}")
    public ResponseEntity<?> getExamByExamId(
            @PathVariable("examId") String examId
    ) {
        ExamResponseDTO examByExamId = this.examService.getExamByExamId(examId);
        if (examByExamId != null) {
            System.out.println("examByExamId = " + examByExamId);
            return ResponseEntity.status(HttpStatus.OK).body(examByExamId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found !");
    }

    // method to submit answer
    @PostMapping(path = "/submit-answer/{examId}")
    public ResponseEntity<?> submitAnswer(@PathVariable("examId") String examId, @RequestBody ExamSubmittedRequestDTO examSubmitRequestDTO) {
        this.examService.submitAnswer(examId, examSubmitRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Answer submitted successfully !!");
    }

    @GetMapping(path = "/get-submitted-answer/{examId}/{emailId}")
    public ResponseEntity<?> getSubmittedAnswer(@PathVariable("examId") String examId, @PathVariable("emailId") String emailId) {
        List<ExamSubmittedResponseDTO> examSubmittedResponseDTO = this.examService.getSubmittedAnswer(examId, emailId);
        if (examSubmittedResponseDTO != null && !examSubmittedResponseDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(examSubmittedResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found !");
    }

    // get-submitted-answer by emailId
    @GetMapping(path = "/get-submitted-answer-by-emailId/{emailId}")
    public ResponseEntity<?> getSubmittedAnswerByEmailId(@PathVariable("emailId") String emailId) {
        List<ExamSubmittedResponseDTO> examSubmittedResponseDTO = this.examService.getSubmittedAnswerByEmailId(emailId);
        if (examSubmittedResponseDTO != null && !examSubmittedResponseDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(examSubmittedResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found !");
    }
}
