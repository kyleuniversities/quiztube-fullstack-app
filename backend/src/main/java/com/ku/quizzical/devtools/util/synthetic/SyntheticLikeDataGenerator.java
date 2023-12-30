package com.ku.quizzical.devtools.util.synthetic;

import static org.mockito.Mockito.description;
import java.util.List;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;
import com.ku.quizzical.common.util.entry.Entry;
import com.ku.quizzical.common.util.string.StringEntry;
import com.ku.quizzical.common.util.string.StringList;
import com.ku.quizzical.common.util.string.StringMap;

final class SyntheticLikeDataGenerator extends SyntheticSubDataGenerator {
    // Instance fields
    private int quizIndex;
    private int likeIndex;
    private List<Integer> indexList;

    // New Instance Method
    public static SyntheticLikeDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticLikeDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticLikeDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "like";
    }

    // Main Instance Methods
    @Override
    public void appendSqlLines() {
        this.reset();
        this.addLine("INSERT INTO development._like (id, quiz_id, user_id)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getUsernames(), (Integer i, String username) -> {
            String userId = this.idHolder.getUserIds().get(i);
            ListHelper.shuffle(this.indexList);
            IterationHelper.forEach(50, (Integer indexIndex) -> {
                int quizIndex = this.indexList.get(indexIndex);
                String quizId = this.idHolder.getQuizIds().get(quizIndex);
                String likeId = this.idHolder.getLikeIds().get(this.likeIndex++);
                String recordText =
                        StringHelper.format("('%s', '%s', '%s'),", likeId, quizId, userId);
                this.addLine(recordText);
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
            String questionId = this.idHolder.getQuestionIds().get(this.likeIndex++);
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
            String key = line.substring(0, colonIndex);
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
        this.likeIndex = 0;
        this.indexList = ListHelper.newIndexList(75);
    }
}
