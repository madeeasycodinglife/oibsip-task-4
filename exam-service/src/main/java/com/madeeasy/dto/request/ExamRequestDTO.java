package com.madeeasy.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamRequestDTO {

    private String title;
    private Integer duration; // duration in minutes
    private List<QuestionRequestDTO> questions;
}
