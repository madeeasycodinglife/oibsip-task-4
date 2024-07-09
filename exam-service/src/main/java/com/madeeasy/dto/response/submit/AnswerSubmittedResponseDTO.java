package com.madeeasy.dto.response.submit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerSubmittedResponseDTO {

    private String id;
    private String text;
    private boolean isCorrect;
    private boolean isSelected;
}
