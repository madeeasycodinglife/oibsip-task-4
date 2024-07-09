package com.madeeasy.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamResponseDTO {

    private String id;
    private String title;
    private Integer duration;
    private List<QuestionResponseDTO> questions;
}
