package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.ku.quizzical.app.controller.comment.Comment;
import com.ku.quizzical.app.controller.like.Like;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.helper.UserHelper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Quiz Users
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public final class User implements UserDetails {

    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    // Operant Methods
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserHelper.makeDefaultAuthorityList();
    }

    // To String Method
    @Override
    public String toString() {
        return String.format("User(\"%s\", \"%s\", %s, \"%s\")", this.username, this.email,
                this.password, this.id);
    }
}
