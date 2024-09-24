package com.ku.quizzical.app.controller.subject;

import java.util.List;
import java.util.function.Function;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.list.ListHelper;

/**
 * Service class for Service Database related operations
 */
@Service
public class SubjectOrdinaryDatabaseService implements SubjectDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final SubjectDtoRowMapper dtoRowMapper;

    // Constructor Method
    public SubjectOrdinaryDatabaseService(JdbcTemplate jdbcTemplate,
            SubjectDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public SubjectDto saveSubject(SubjectDto subjectDto) {
        var sql = """
                INSERT INTO subject(id, text, picture, thumbnail)
                VALUES (?, ?, ?, ?)
                """;
        int result = this.jdbcTemplate.update(sql, subjectDto.id(), subjectDto.text(),
                subjectDto.picture(), subjectDto.thumbnail());
        System.out.println("POST SUBJECT RESULT = " + result);
        return new SubjectDto(subjectDto.id(), subjectDto.text(), subjectDto.picture(),
                subjectDto.thumbnail());
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        var sql = """
                SELECT id, text, picture, thumbnail
                FROM subject
                LIMIT 100
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper);
    }

    @Override
    public SubjectDto getSubject(String id) {
        var sql = """
                SELECT id, text, picture, thumbnail
                FROM subject
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public SubjectDto getSubjectByText(String text) {
        var sql = """
                SELECT id, text, picture, thumbnail
                FROM subject
                WHERE text = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, text),
                0);
    }

    @Override
    public SubjectDto updateSubject(String id, SubjectUpdateRequest update) {
        this.updateSubjectAttribute(update, "text", SubjectUpdateRequest::text);
        this.updateSubjectAttribute(update, "picture", SubjectUpdateRequest::picture);
        this.updateSubjectAttribute(update, "thumbnail", SubjectUpdateRequest::thumbnail);
        return this.getSubject(id);
    }

    @Override
    public void deleteSubject(String id) {
        var sql = """
                DELETE
                FROM subject
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE SUBJECT RESULT = " + result);
    }

    private void updateSubjectAttribute(SubjectUpdateRequest update, String attributeName,
            Function<SubjectUpdateRequest, String> attributeCollector) {
        String attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE subject SET %s = ? WHERE id = ?", attributeName);
            int result =
                    this.jdbcTemplate.update(sql, attributeCollector.apply(update), update.id());
            System.out.println("UPDATE SUBJECT " + attributeName + " RESULT = " + result);
        });
    }
}
