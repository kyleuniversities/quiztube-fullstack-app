package com.ku.quizzical.devtools.util.synthetic;

import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

final class SyntheticSubjectDataGenerator extends SyntheticSubDataGenerator {
    // New Instance Method
    public static SyntheticSubjectDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticSubjectDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticSubjectDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "subject";
    }

    // Main Instance Method
    @Override
    public void appendSqlLines() {
        this.addLine("INSERT INTO development.subject (id, text, picture, thumbnail)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getSubjects(), (Integer i, String subject) -> {
            String id = this.idHolder.getSubjectIds().get(i);
            String pictureText = "static/subject/subject-picture-" + subject + ".png";
            String thumbnailText = "static/subject/subject-picture-" + subject + "_T.png";
            String recordText = StringHelper.format("('%s', '%s', '%s', '%s'),", id, subject,
                    pictureText, thumbnailText);
            this.addLine(recordText);
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }
}
