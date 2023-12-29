package com.ku.quizzical.app.controller.subject;

import java.util.List;
import com.ku.quizzical.app.controller.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Comments
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public final class Subject {

    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE)
    private List<Quiz> quizzes;

    // To String Method
    @Override
    public String toString() {
        return String.format("Subject(\"%s\", \"%s\")", this.text, this.id);
    }
}
