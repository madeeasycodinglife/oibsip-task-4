package com.madeeasy.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponseDTO {
    private String id;
    private String text;
    private List<AnswerResponseDTO> answers;
}
