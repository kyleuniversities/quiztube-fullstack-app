package com.ku.quizzical.devtools.util.synthetic;

import java.io.File;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.file.FileHelper;
import com.ku.quizzical.common.helper.file.FilePathHelper;
import com.ku.quizzical.common.util.string.StringList;
import com.ku.quizzical.common.util.string.StringListMap;

final class SyntheticStaticData {
    // Instance Fields
    private String staticsFolderPath;
    private StringList usernames;
    private StringList subjects;
    private StringListMap questionAssociations;
    private StringListMap questionTemplates;
    private StringListMap quizTitleTemplates;
    private StringListMap quizDescriptionTemplates;
    private StringList commentAdjectives;
    private StringList commentTemplates;

    // New Instance Method
    public static SyntheticStaticData newInstance(String staticsFolderPath) {
        return new SyntheticStaticData(staticsFolderPath);
    }

    // Constructor Method
    private SyntheticStaticData(String staticsFolderPath) {
        super();
        this.collect(staticsFolderPath);
    }

    public String getStaticsFolderPath() {
        return this.staticsFolderPath;
    }

    public StringList getUsernames() {
        return this.usernames;
    }

    public StringList getSubjects() {
        return this.subjects;
    }

    public StringListMap getQuestionAssociations() {
        return this.questionAssociations;
    }

    public StringListMap getQuestionTemplates() {
        return this.questionTemplates;
    }

    public StringListMap getQuizTitleTemplates() {
        return this.quizTitleTemplates;
    }

    public StringListMap getQuizDescriptionTemplates() {
        return this.quizDescriptionTemplates;
    }

    public StringList getCommentAdjectives() {
        return this.commentAdjectives;
    }

    public StringList getCommentTemplates() {
        return this.commentTemplates;
    }

    // Initialization Methods
    private StringListMap collectQuestionAssociations() {
        return this.collectStringListMapTemplates("question-associations");
    }

    private StringListMap collectQuestionTemplates() {
        return this.collectStringListMapTemplates("question-templates");
    }

    private StringListMap collectQuizTitleTemplates() {
        return this.collectStringListMapTemplates("quiz-title-templates");
    }

    private StringListMap collectQuizDescriptionTemplates() {
        return this.collectStringListMapTemplates("quiz-description-templates");
    }

    private StringListMap collectStringListMapTemplates(String tag) {
        StringListMap listMap = StringListMap.newInstance();
        FileHelper.forEachFile(this.staticsFolderPath + "/" + tag, (File file) -> {
            String name = FilePathHelper.getExtensionlessFileName(file);
            StringList lines = FileHelper.getStringList(file);
            MapHelper.put(listMap, name, lines);
        });
        return listMap;
    }

    private void collect(String staticsFolderPath) {
        this.staticsFolderPath = staticsFolderPath;
        this.usernames = FileHelper.getStringList(this.staticsFolderPath + "/usernames.txt");
        this.subjects = FileHelper.getStringList(this.staticsFolderPath + "/subjects.txt");
        this.questionAssociations = this.collectQuestionAssociations();
        this.questionTemplates = this.collectQuestionTemplates();
        this.quizTitleTemplates = this.collectQuizTitleTemplates();
        this.quizDescriptionTemplates = this.collectQuizDescriptionTemplates();
        this.commentAdjectives =
                FileHelper.getStringList(this.staticsFolderPath + "/comment-adjectives.txt");
        this.commentTemplates =
                FileHelper.getStringList(this.staticsFolderPath + "/comment-templates.txt");
    }
}
