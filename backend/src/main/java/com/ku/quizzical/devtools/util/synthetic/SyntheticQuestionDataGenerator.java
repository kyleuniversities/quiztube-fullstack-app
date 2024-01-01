package com.ku.quizzical.devtools.util.synthetic;

import java.util.List;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;
import com.ku.quizzical.common.util.string.StringEntry;
import com.ku.quizzical.common.util.string.StringList;

final class SyntheticQuestionDataGenerator extends SyntheticSubDataGenerator {
    // Instance fields
    private int quizIndex;
    private int questionIndex;
    private List<Integer> indexList;

    // New Instance Method
    public static SyntheticQuestionDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticQuestionDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticQuestionDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "question";
    }

    // Main Instance Methods
    @Override
    public void appendSqlLines() {
        this.reset();
        this.addLine(
                "INSERT INTO development.question (id, question, answer, number_of_milliseconds, quiz_id)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getUsernames(), (Integer i, String username) -> {
            String userId = this.idHolder.getUserIds().get(i);
            String name = this.collectName(username);
            ListHelper.forEach(this.staticData.getSubjects(), (Integer j, String subject) -> {
                String subjectId = this.idHolder.getSubjectIds().get(j);
                String subjectTag =
                        StringReplacementHelper.replace(subject.toLowerCase(), " ", "-");
                this.appendQuestionLines(userId, subjectId, name, subjectTag);
            });
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }

    private void appendQuestionLines(String userId, String subjectId, String name,
            String subjectTag) {
        String quizId = this.idHolder.getQuizIds().get(this.quizIndex++);
        List<StringEntry> entryList = this.getQuestionEntryList(subjectTag);
        ListHelper.forEach(entryList, (StringEntry entry) -> {
            String questionId = this.idHolder.getQuestionIds().get(this.questionIndex++);
            String question = this.collectFilledAttribute(
                    this.staticData.getQuestionTemplates().get(subjectTag), entry.getKey());
            String answer = entry.getValue();
            String recordText = StringHelper.format("('%s', '%s', '%s', %d, '%s'),", questionId,
                    question, answer, 10000, quizId);
            this.addLine(recordText);
        });
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

    private List<StringEntry> getQuestionEntryList(String subjectTag) {
        List<StringEntry> entryList = ListHelper.newArrayList();
        StringList associationList = this.staticData.getQuestionAssociations().get(subjectTag);
        ListHelper.shuffle(this.indexList);
        IterationHelper.forEach(3, (Integer i) -> {
            String line = associationList.get(this.indexList.get(i));
            int colonIndex = StringHelper.indexOf(line, ':');
            String key = StringReplacementHelper.replace(line.substring(0, colonIndex), "'", "\\'");
            String value = line.substring(colonIndex + 2);
            ListHelper.add(entryList, StringEntry.newInstance(key, value));
        });
        return entryList;
    }

    private String collectFilledAttribute(StringList templateList, String question) {
        String template = ListHelper.getRandomValue(templateList);
        String filledAttribute = StringReplacementHelper.replace(template, "${Q}", question);
        return filledAttribute;
    }

    // Initialization Methods
    private void reset() {
        this.quizIndex = 0;
        this.questionIndex = 0;
        this.indexList = ListHelper.newIndexList(10);
    }
}
