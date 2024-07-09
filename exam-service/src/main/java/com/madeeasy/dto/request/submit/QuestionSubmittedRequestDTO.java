package com.madeeasy.dto.request.submit;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionSubmittedRequestDTO {
    private String id;
    private String text;
    private List<AnswerSubmittedRequestDTO> answers;
}
