package com.ku.quizzical.devtools.util.synthetic;

import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.ThreadHelper;
import com.ku.quizzical.common.helper.number.IdHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.string.StringList;

final class SyntheticDataIdHolder {
    // Instance Fields
    private StringList userIds;
    private StringList subjectIds;
    private StringList quizIds;
    private StringList questionIds;
    private StringList likeIds;
    private StringList commentIds;

    // New Instance Method
    public static SyntheticDataIdHolder newInstance() {
        return new SyntheticDataIdHolder();
    }

    // Constructor Method
    private SyntheticDataIdHolder() {
        super();
        this.collect();
    }

    // Accessor Methods
    public StringList getUserIds() {
        return this.userIds;
    }

    public StringList getSubjectIds() {
        return this.subjectIds;
    }

    public StringList getQuizIds() {
        return this.quizIds;
    }

    public StringList getQuestionIds() {
        return this.questionIds;
    }

    public StringList getLikeIds() {
        return this.likeIds;
    }

    public StringList getCommentIds() {
        return this.commentIds;
    }

    // Initialization Methods
    private StringList makeIds(int amount) {
        StringList ids = StringHelper.newStringList();
        IterationHelper.forEach(amount, () -> {
            ThreadHelper.sleep(1);
            ListHelper.add(ids, IdHelper.nextMockId());
        });
        return ids;
    }

    private void collect() {
        this.userIds = this.makeIds(15);
        this.subjectIds = this.makeIds(5);
        this.quizIds = this.makeIds(75);
        this.questionIds = this.makeIds(225);
        this.likeIds = this.makeIds(15 * 50);
        this.commentIds = this.makeIds(15 * 50);
    }
}
