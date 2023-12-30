package com.ku.quizzical.devtools.util.synthetic;

import java.io.File;
import java.util.List;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.file.FileHelper;
import com.ku.quizzical.common.helper.file.FilePathHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.string.StringList;

public final class SyntheticDataGenerator {
    // Instance Fields
    private String resourcesFolderPath;
    private String staticsFolderPath;
    private String outputFolderPath;
    private SyntheticStaticData staticData;
    private SyntheticDataIdHolder idHolder;

    // New Instance Method
    public static SyntheticDataGenerator newInstance() {
        return new SyntheticDataGenerator();
    }

    // Constructor Method
    private SyntheticDataGenerator() {
        super();
    }

    // Accessor Methods
    public String getOutputFolderPath() {
        return this.outputFolderPath;
    }

    // Main Instance Method
    public void generate(String resourcesFolderPath) {
        this.reset(resourcesFolderPath);
        this.collectStaticData();
        this.makeIds();
        this.generateSqlQueries();
        this.combineSqlQueries();
    }

    // Major Methods
    private void collectStaticData() {
        this.staticData = SyntheticStaticData.newInstance(this.staticsFolderPath);
    }

    private void makeIds() {
        this.idHolder = SyntheticDataIdHolder.newInstance();
    }

    private void generateSqlQueries() {
        SyntheticUserDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
        SyntheticSubjectDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
        SyntheticQuizDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
        SyntheticQuestionDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
        SyntheticLikeDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
        SyntheticCommentDataGenerator.newInstance(this, this.staticData, this.idHolder).generate();
    }

    private void combineSqlQueries() {
        StringList lines = StringHelper.newStringList();
        List<String> tagList = List.of("user", "subject", "quiz", "question", "like", "comment");
        ListHelper.forEach(tagList, (String tag) -> {
            StringList query = FileHelper.getStringList(this.outputFolderPath + "/" + tag + ".sql");
            ListHelper.addAll(lines, query);
            ListHelper.add(lines, "");
        });
        FileHelper.exportStringList(lines, outputFolderPath + "/_populate.sql");
    }

    // Initialization Methods
    private void reset(String resourcesFolderPath) {
        this.resourcesFolderPath = resourcesFolderPath + "/synthetic-data";
        this.staticsFolderPath = this.resourcesFolderPath + "/statics";
        this.outputFolderPath = this.resourcesFolderPath + "/output";
    }
}
