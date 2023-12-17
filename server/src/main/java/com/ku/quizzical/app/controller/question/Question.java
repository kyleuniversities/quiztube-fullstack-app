package com.ku.quizzical.app.controller.question;

import com.ku.quizzical.app.controller.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Quiz Questions+
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public final class Question {

    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "number_of_milliseconds", nullable = false)
    private int numberOfMilliseconds;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    public Quiz quiz;

    // To String Method
    @Override
    public String toString() {
        return String.format("Question(\"%s\", \"%s\", %d, \"%s\")", this.question, this.answer,
                this.numberOfMilliseconds, this.id);
    }
}
