package com.ku.quizzical.app.controller.comment;

import java.util.List;

public interface CommentDatabaseService {
    CommentDto saveComment(String quizId, CommentDto comment);

    List<CommentDto> getAllCommentsByQuizId(String quizId);

    CommentDto getComment(String quizId, String id);

    void deleteComment(String quizId, String id);
}
