package com.example.ass.controller;

import com.example.ass.domain.Question;
import com.example.ass.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public String getAllQuestions(Model model) {
        List<Question> questions = questionService.findAllQuestions();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("/questions/starred")
    public String getStarredQuestions(Model model) {
        List<Question> questions = questionService.findStarredQuestions();
        model.addAttribute("questions", questions);
        return "starredQuestions";
    }

    @GetMapping("/questions/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "question";
    }

    @PostMapping("/questions/{id}/star")
    @ResponseBody
    public void updateStarredStatus(@PathVariable Long id, @RequestParam boolean starred) {
        questionService.updateStarredStatus(id, starred);
    }

    @DeleteMapping("/questions/{id}")
    @ResponseBody
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}