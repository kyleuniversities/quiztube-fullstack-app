package com.ku.quizzical.app.controller.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.ku.quizzical.app.controller.comment.Comment;
import com.ku.quizzical.app.controller.like.Like;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.common.helper.list.ListHelper;

@Component
public class UserRowMapper implements RowMapper<User> {
        private UserRepository userRepository;

        public UserRowMapper(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public User mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                Optional<User> userQuery = this.userRepository.findById(resultSet.getString("id"));
                List<Quiz> quizzes = userQuery.isEmpty() ? ListHelper.newArrayList()
                                : userQuery.get().getQuizzes();
                List<Like> likes = userQuery.isEmpty() ? ListHelper.newArrayList()
                                : userQuery.get().getLikes();
                List<Comment> comments = userQuery.isEmpty() ? ListHelper.newArrayList()
                                : userQuery.get().getComments();
                return new User(resultSet.getString("id"), resultSet.getString("username"),
                                resultSet.getString("email"), resultSet.getString("password"),
                                resultSet.getString("picture"), resultSet.getString("thumbnail"),
                                quizzes, likes, comments);
        }
}
