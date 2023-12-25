package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import com.ku.quizzical.app.controller.question.Question;
import com.ku.quizzical.app.controller.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Quiz Quizzes+
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz")
public final class Quiz {

    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User user;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    // To String Method
    @Override
    public String toString() {
        return String.format("Quiz(\"%s\", \"%s\", \"%s\")", this.title, this.description, this.id);
    }
}
