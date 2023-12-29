package com.ku.quizzical.devtools.util.synthetic;

import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;
import com.ku.quizzical.common.util.string.StringList;

final class SyntheticQuizDataGenerator extends SyntheticSubDataGenerator {
    // Instance fields
    private int quizIndex;

    // New Instance Method
    public static SyntheticQuizDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticQuizDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticQuizDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "quiz";
    }

    public int getQuizIndex() {
        return this.quizIndex;
    }

    // Main Instance Methods
    @Override
    public void appendSqlLines() {
        this.reset();
        this.addLine(
                "INSERT INTO development.quiz (id, title, description, picture, thumbnail, user_id, subject_id)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getUsernames(), (Integer i, String username) -> {
            String userId = this.idHolder.getUserIds().get(i);
            String name = this.collectName(username);
            ListHelper.forEach(this.staticData.getSubjects(), (Integer j, String subject) -> {
                String subjectId = this.idHolder.getSubjectIds().get(j);
                String subjectTag =
                        StringReplacementHelper.replace(subject.toLowerCase(), " ", "-");
                this.appendQuizLine(userId, subjectId, name, subjectTag);
            });
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }

    private void appendQuizLine(String userId, String subjectId, String name, String subjectTag) {
        String id = this.idHolder.getQuizIds().get(this.quizIndex++);
        String title =
                StringReplacementHelper.replace(
                        this.collectFilledAttribute(
                                this.staticData.getQuizTitleTemplates().get(subjectTag), name),
                        "\'", "\\\'");
        String description = StringReplacementHelper.replace(
                this.collectFilledAttribute(
                        this.staticData.getQuizDescriptionTemplates().get(subjectTag), name),
                "\'", "\\\'");
        String pictureText = "static/quiz/quiz-picture-" + subjectTag + ".png";
        String recordText = StringHelper.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s'),", id,
                title, description, pictureText, pictureText, userId, subjectId);
        this.addLine(recordText);
    }

    // Major Methods
    private String collectName(String username) {
        StringBuilder name = StringHelper.newBuilder();
        StringHelper.forEach(username, (Character ch) -> {
            if (ch >= 'a' && ch <= 'z') {
                name.append(ch);
                return true;
            }
            return false;
        });
        return StringHelper.capitalizeFirstLetter(name.toString());
    }

    private String collectFilledAttribute(StringList templateList, String name) {
        String template = ListHelper.getRandomValue(templateList);
        String filledAttribute = StringReplacementHelper.replace(template, "${U}", name);
        return filledAttribute;
    }

    // Initialization Methods
    private void reset() {
        this.quizIndex = 0;
    }
}
