package com.madeeasy.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponseDTO {
    private String id;
    private String text;
    private boolean isCorrect;
}
