package com.ku.quizzical.common.helper;

import com.ku.quizzical.common.helper.file.FilePathHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.string.StringList;
import com.ku.quizzical.common.util.wrapper.StringWrapper;

/**
 * Helper class for Java Operations
 */
public class JavaHelper {
    /**
     * Gets the package text of a folder path
     */
    public static String getPackageText(String folderPath) {
        StringList packageTextParts = StringList.newInstance();
        StringWrapper parentFolderPath =
                StringWrapper.newInstance(FilePathHelper.toSlashedPath(folderPath));
        StringWrapper part =
                StringWrapper.newInstance(FilePathHelper.getFileName(parentFolderPath.getValue()));
        ConditionalHelper.whileLoop(() -> !part.isEqualTo("src") && !part.isEqualTo("java"), () -> {
            packageTextParts.add(part.getValue());
            parentFolderPath
                    .setValue(FilePathHelper.getParentFolderPath(parentFolderPath.getValue()));
            part.setValue(FilePathHelper.getFileName(parentFolderPath.getValue()));
        });
        ListHelper.reverse(packageTextParts);
        return StringHelper.join(packageTextParts, ".");
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private JavaHelper() {
        super();
    }
}
