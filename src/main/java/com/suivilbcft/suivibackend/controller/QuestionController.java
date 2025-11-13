package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.model.Question;
import com.suivilbcft.suivibackend.dto.QuestionDTO;
import com.suivilbcft.suivibackend.service.QuestionService;
import com.suivilbcft.suivibackend.service.TypeReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TypeReponseService typeReponseService; 

    @GetMapping("/questions/section/{idSection}")
    public ResponseEntity<List<Question>> getQuestionsBySectionId(
            @PathVariable String idSection) {
        List<Question> questions = questionService.getQuestionsBySectionId(idSection);
        return ResponseEntity.ok(questions);
    }

   @GetMapping("/questions/sous-section/{idSousSection}")  
    public ResponseEntity<List<Question>> getQuestionsBySousSectionId(
            @PathVariable String idSousSection) {
        List<Question> questions = questionService.getQuestionsBySousSectionId(idSousSection);
        return ResponseEntity.ok(questions);
    }
    

    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO dto) {
        try {
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("=== DEBUG CREATION QUESTION ===");
            System.out.println("Utilisateur: " + user);
            System.out.println("DTO reçu: " + dto.toString());  

            Question created = questionService.createQuestion(dto);
            System.out.println("Question créée avec ID: " + created.getIdQuestion());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
           
            System.err.println("=== ERREUR RUNTIME EXCEPTION ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace(); 

   
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Erreur lors de l'insertion de la question",
                    "details", e.getMessage(),
                    "exceptionType", e.getClass().getSimpleName()
                )
            );
        } catch (Exception e) {
            System.err.println("=== ERREUR GÉNÉRALE ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error", "Erreur serveur interne", "details", e.getMessage())
            );
        }
    }

  
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String id, @RequestBody QuestionDTO dto) {
        try {
            Question updated = questionService.updateQuestion(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}