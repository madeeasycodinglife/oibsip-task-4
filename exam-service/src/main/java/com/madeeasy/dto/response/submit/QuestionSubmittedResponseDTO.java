package com.madeeasy.dto.response.submit;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionSubmittedResponseDTO {

    private String id;
    private String text;
    private List<AnswerSubmittedResponseDTO> answers;
}
