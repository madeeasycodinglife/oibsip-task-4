package com.madeeasy.repository;

import com.madeeasy.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer,String> {
}
