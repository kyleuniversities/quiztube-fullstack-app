package com.ku.quizzical.app.controller.like;

import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Likes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_like")
public final class Like {

    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    public Quiz quiz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User user;

    // Accessor Methods
    public String getUserId() {
        return this.user.getId();
    }

    // To String Method
    @Override
    public String toString() {
        return String.format("Like(\"%s\")", this.id);
    }
}
