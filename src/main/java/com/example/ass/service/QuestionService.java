package com.example.ass.service;

import com.example.ass.domain.Question;
import com.example.ass.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAllQuestions() {
        List<Question> questions = questionRepository.findAllWithAnswer();
        Collections.shuffle(questions);
        return questions;
    }

    public List<Question> findStarredQuestions() {
        List<Question> questions = questionRepository.findStarredWithAnswer();
        Collections.shuffle(questions);
        return questions;
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findByIdWithAnswer(id).orElse(null);
    }

    public void updateStarredStatus(Long id, boolean starred) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setStarred(starred);
            questionRepository.save(question);
        }
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void updateQuestionContent(Long id, String content) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            question.setContent(content);
            questionRepository.save(question);
        }
    }
}