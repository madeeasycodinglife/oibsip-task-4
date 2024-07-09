package com.madeeasy.repository;

import com.madeeasy.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, String> {
    Optional<Question> findById(String questionId);
}
