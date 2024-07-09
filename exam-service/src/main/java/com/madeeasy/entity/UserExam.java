package com.madeeasy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserExam {

    @Id
    private String id;
    private String userEmail;
    private String examTitle;
    private Integer score;
    private boolean completed;
    private Integer duration;
    private Integer durationTaken;

    @OneToOne
    private Exam exam;
}
