package com.ku.quizzical.devtools.util.synthetic;

import java.util.List;
import java.util.Map;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.list.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

final class SyntheticLikeDataGenerator extends SyntheticSubDataGenerator {
    // Instance fields
    private int likeIndex;
    private List<Integer> indexList;
    private Map<String, Integer> quizLikeCountMap;

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
        this.appendLikeSqlLines();
        this.appendQuizSqlLines();
    }

    // Major Methods
    private void appendLikeSqlLines() {
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
                MapHelper.put(this.quizLikeCountMap, quizId,
                        MapHelper.getInitialize(this.quizLikeCountMap, quizId, () -> 0) + 1);
                this.addLine(recordText);
            });
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }

    private void appendQuizSqlLines() {
        MapHelper.forEach(this.quizLikeCountMap, (String quizId, Integer numberOfLikes) -> {
            this.addLine(StringHelper.format(
                    "UPDATE development.quiz SET number_of_likes = %d WHERE id = '%s';",
                    numberOfLikes, quizId));
        });
    }

    // Initialization Methods
    private void reset() {
        this.likeIndex = 0;
        this.indexList = ListHelper.newIndexList(75);
        this.quizLikeCountMap = MapHelper.newLinkedHashMap();
    }
}
