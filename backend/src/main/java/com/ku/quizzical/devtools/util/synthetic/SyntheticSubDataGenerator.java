package com.ku.quizzical.devtools.util.synthetic;

import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.file.FileHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.string.StringList;

abstract class SyntheticSubDataGenerator {
    // Instance Fields
    protected SyntheticDataGenerator generator;
    protected SyntheticStaticData staticData;
    protected SyntheticDataIdHolder idHolder;
    protected String outputFolderPath;
    private StringList lines;

    // Constructor Method
    protected SyntheticSubDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super();
        this.generator = generator;
        this.staticData = staticData;
        this.idHolder = idHolder;
        this.outputFolderPath = this.generator.getOutputFolderPath();
    }

    // Abstract Methods
    protected abstract void appendSqlLines();

    protected abstract String getSubDataTag();

    // Main Instance Method
    public final void generate() {
        this.reset();
        this.appendSqlLines();
        this.exportLines();
    }

    // Major Methods
    private void exportLines() {
        FileHelper.exportStringList(this.lines,
                this.outputFolderPath + "/" + this.getSubDataTag() + ".sql");
    }

    // Protected Helper Methods
    protected final void addLine(String line) {
        ListHelper.add(this.lines, line);
    }

    protected final void appendLastCharacters(String text) {
        String lastLine = ListHelper.getWithReverseIndex(this.lines, 0);
        String appended = lastLine + text;
        ListHelper.set(this.lines, this.lines.size() - 1, appended);
    }

    protected final void deleteLastCharacters(int amount) {
        String lastLine = ListHelper.getWithReverseIndex(this.lines, 0);
        String trimmed = StringHelper.substringFromUpToTextLength(lastLine, amount);
        ListHelper.set(this.lines, this.lines.size() - 1, trimmed);
    }

    // Initialization Methods
    private void reset() {
        this.lines = StringHelper.newStringList();
    }
}
