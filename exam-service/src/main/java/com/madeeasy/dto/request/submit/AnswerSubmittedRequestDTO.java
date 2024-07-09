package com.madeeasy.dto.request.submit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerSubmittedRequestDTO {

    private String id;
    private String text;
    private boolean isCorrect;
    private boolean isSelected;
}
