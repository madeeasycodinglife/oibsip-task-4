package com.madeeasy.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Exam {
    @Id
    private String id;
    private String title;
    private int duration; // duration in minutes

    @OneToOne(mappedBy = "exam")
    private UserExam userExam;

    @OneToMany(mappedBy = "exam", fetch = FetchType.EAGER)
    private List<Question> questions;

}
