package com.ku.quizzical.devtools.util.synthetic;

import java.util.List;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;

final class SyntheticCommentDataGenerator extends SyntheticSubDataGenerator {
    // Instance fields
    private int commentIndex;
    private List<Integer> indexList;

    // New Instance Method
    public static SyntheticCommentDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticCommentDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticCommentDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "comment";
    }

    // Main Instance Methods
    @Override
    public void appendSqlLines() {
        this.reset();
        this.addLine("INSERT INTO development.comment (id, text, quiz_id, user_id)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getUsernames(), (Integer i, String username) -> {
            String userId = this.idHolder.getUserIds().get(i);
            ListHelper.shuffle(this.indexList);
            IterationHelper.forEach(50, (Integer indexIndex) -> {
                int quizIndex = this.indexList.get(indexIndex);
                String quizId = this.idHolder.getQuizIds().get(quizIndex);
                String commentId = this.idHolder.getCommentIds().get(this.commentIndex++);
                String adjective =
                        ListHelper.getRandomValue(this.staticData.getCommentAdjectives());
                String text = StringReplacementHelper.replace(StringReplacementHelper.replace(
                        ListHelper.getRandomValue(this.staticData.getCommentTemplates()), "${A}",
                        adjective), "\'", "\\\'");
                String recordText = StringHelper.format("('%s', '%s', '%s', '%s'),", commentId,
                        text, quizId, userId);
                this.addLine(recordText);
            });
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }

    // Initialization Methods
    private void reset() {
        this.commentIndex = 0;
        this.indexList = ListHelper.newIndexList(75);
    }
}
