package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import com.ku.quizzical.app.controller.comment.Comment;
import com.ku.quizzical.app.controller.like.Like;
import com.ku.quizzical.app.controller.question.Question;
import com.ku.quizzical.app.controller.subject.Subject;
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

    @Column(name = "picture", nullable = false)
    private String picture;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "number_of_likes", nullable = false)
    private int numberOfLikes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    public Subject subject;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Like> likes;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    // Accessor Methods
    public String getUserId() {
        return this.user.getId();
    }

    // To String Method
    @Override
    public String toString() {
        return String.format("Quiz(\"%s\", \"%s\", \"%s\", \"%s\")", this.title, this.description,
                this.id, this.getUserId());
    }
}
