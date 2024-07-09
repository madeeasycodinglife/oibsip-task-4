package com.madeeasy.repository;

import com.madeeasy.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,String> {
}
