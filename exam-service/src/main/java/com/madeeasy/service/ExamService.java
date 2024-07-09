package com.madeeasy.service;

import com.madeeasy.dto.request.ExamRequestDTO;
import com.madeeasy.dto.request.submit.ExamSubmittedRequestDTO;
import com.madeeasy.dto.response.ExamResponseDTO;
import com.madeeasy.dto.response.submit.ExamSubmittedResponseDTO;

import java.util.List;

public interface ExamService {
    List<ExamResponseDTO> getAllExams();

    ExamResponseDTO getExamById(Long id);

    void createExam(ExamRequestDTO exam);

    ExamResponseDTO updateExam(Long id, ExamRequestDTO examDetails);

    void deleteExam(Long id);

    List<ExamResponseDTO> getExamByTitle(String title);

    ExamResponseDTO getExamByExamId(String examId);

    void submitAnswer(String examId, ExamSubmittedRequestDTO examRequestDTO);

    List<ExamSubmittedResponseDTO> getSubmittedAnswer(String examId, String emailId);

    List<ExamSubmittedResponseDTO> getSubmittedAnswerByEmailId(String emailId);
}
