package com.ku.quizzical.app.controller.comment;

import java.util.List;

public interface CommentDatabaseService {
    void saveComment(String userId, String quizId, CommentDto comment);

    List<CommentDto> getAllCommentsByQuizId(String quizId);

    CommentDto getComment(String quizId, String id);

    void deleteComment(String userId, String quizId, String id);
}
