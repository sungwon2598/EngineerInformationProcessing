package com.example.ass.repository;

import com.example.ass.domain.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByStarredTrue();

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answer")
    List<Question> findAllWithAnswer();

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answer WHERE q.starred = true")
    List<Question> findStarredWithAnswer();

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answer WHERE q.id = :id")
    Optional<Question> findByIdWithAnswer(Long id);
}