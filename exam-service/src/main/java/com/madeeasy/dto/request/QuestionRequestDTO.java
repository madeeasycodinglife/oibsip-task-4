package com.madeeasy.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionRequestDTO {

    private String text;
    private List<AnswerRequestDTO> answers;
}
