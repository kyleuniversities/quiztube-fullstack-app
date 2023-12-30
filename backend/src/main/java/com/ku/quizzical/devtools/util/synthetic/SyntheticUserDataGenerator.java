package com.ku.quizzical.devtools.util.synthetic;

import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

final class SyntheticUserDataGenerator extends SyntheticSubDataGenerator {
    // New Instance Method
    public static SyntheticUserDataGenerator newInstance(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        return new SyntheticUserDataGenerator(generator, staticData, idHolder);
    }

    // Constructor Method
    private SyntheticUserDataGenerator(SyntheticDataGenerator generator,
            SyntheticStaticData staticData, SyntheticDataIdHolder idHolder) {
        super(generator, staticData, idHolder);
    }

    // Accessor Methods
    @Override
    public String getSubDataTag() {
        return "user";
    }

    // Main Instance Method
    @Override
    public void appendSqlLines() {
        this.addLine(
                "INSERT INTO development.user (id, username, email, password, picture, thumbnail)");
        this.addLine("VALUES");
        ListHelper.forEach(this.staticData.getUsernames(), (Integer i, String username) -> {
            String id = this.idHolder.getUserIds().get(i);
            String firstLetter = username.charAt(0) + "";
            String pictureText = "static/user/user-picture-" + firstLetter + ".png";
            String thumbnailText = "static/user/user-picture-" + firstLetter + "_T.png";
            String recordText = StringHelper.format("('%s', '%s', '%s', '%s', '%s', '%s'),", id,
                    username, username + "@gamil.com", username + "!", pictureText, thumbnailText);
            this.addLine(recordText);
        });
        this.deleteLastCharacters(1);
        this.appendLastCharacters(";");
    }
}
