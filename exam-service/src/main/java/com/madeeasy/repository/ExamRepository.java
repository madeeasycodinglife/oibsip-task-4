package com.madeeasy.repository;

import com.madeeasy.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam,String> {
    List<Exam> findByTitleIgnoreCase(String title);

    Optional<Exam> findById(String examId);
    @Query("SELECT e FROM Exam e WHERE e.id NOT LIKE 'SUBMITTED_%'")
    List<Exam> findAllNotSubmittedExams();
}
