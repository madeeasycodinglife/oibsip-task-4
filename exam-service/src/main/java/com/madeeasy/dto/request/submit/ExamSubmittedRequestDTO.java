package com.madeeasy.dto.request.submit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamSubmittedRequestDTO {
    private String emailId;
    private String examId;
    private String title;
    private Integer duration;
    private Integer durationTaken; // duration in seconds
    private List<QuestionSubmittedRequestDTO> questions;
}
