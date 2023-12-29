package com.ku.quizzical.app.controller.comment;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(String userId, String quizId, CommentDto comment);

    List<CommentDto> getAllCommentsByQuizId(String quizId);

    CommentDto getCommentById(String quizId, String id);

    CommentDto updateComment(String userId, String quizId, String id, CommentDto comment);

    void deleteComment(String userId, String quizId, String id);
}
