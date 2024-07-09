package com.madeeasy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserAnswer {

    @Id
    private String id;
    private String text;
    private boolean isCorrect;
    private boolean isSelected;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
