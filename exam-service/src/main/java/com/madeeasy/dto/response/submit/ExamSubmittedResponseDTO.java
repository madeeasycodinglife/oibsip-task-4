package com.madeeasy.dto.response.submit;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamSubmittedResponseDTO {

    private String userId;
    private String examId;
    private String emailId;
    private String title;
    private Integer duration;
    private Integer durationTaken; // duration in seconds
    private Integer score;
    private List<QuestionSubmittedResponseDTO> questions;
}
