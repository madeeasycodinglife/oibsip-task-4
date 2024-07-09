package com.madeeasy.repository;

import com.madeeasy.entity.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserExamRepository extends JpaRepository<UserExam, String> {
    Optional<UserExam> findById(String userExamId);

    Optional<UserExam> findByExamTitleAndUserEmail(String examTitle, String userEmail);

    @Query("select u from UserExam u where u.userEmail = :emailId")
    List<UserExam> findByUserEmail(@Param("emailId") String userEmail);

}
