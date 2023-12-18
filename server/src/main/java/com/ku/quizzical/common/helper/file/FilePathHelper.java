package com.ku.quizzical.common.helper.file;

import java.io.File;
import java.util.List;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;

/**
 * Helper class for File Path Operations
 */
public class FilePathHelper {
    // Class Fields
    private static final List<String> TEXT_EXTENSION_LIST = FilePathHelper.makeTextExtensionList();

    /**
     * Gets the absolute path of a file
     */
    public static String getAbsolutePath(File file) {
        return file.getAbsolutePath();
    }

    /**
     * Gets the extensionless file name of a file
     */
    public static String getExtensionlessFileName(File file) {
        return FilePathHelper.getExtensionlessFileName(FilePathHelper.getAbsolutePath(file));
    }

    /**
     * Gets the extensionless file name of a file path
     */
    public static String getExtensionlessFileName(String path) {
        String fileName = FilePathHelper.getFileName(path);
        int lastPeriodIndex = StringHelper.lastIndexOf(fileName, '.');
        return ConditionalHelper.newTernaryOperation(lastPeriodIndex == -1, () -> fileName,
                () -> StringHelper.substring(fileName, 0, lastPeriodIndex));
    }

    /**
     * Gets the file name of a file
     */
    public static String getFileName(File file) {
        return FilePathHelper.getFileName(FilePathHelper.getAbsolutePath(file));
    }

    /**
     * Gets the file name of a file path
     */
    public static String getFileName(String path) {
        String slashedPath = FilePathHelper.toSlashedPath(path);
        int lastSlashIndex = StringHelper.lastIndexOf(slashedPath, '/');
        return StringHelper.substring(slashedPath, lastSlashIndex + 1);
    }

    /**
     * Gets the parent folder path in relation to a file path
     */
    public static String getParentFolderPath(String path) {
        return FilePathHelper.getParentFolderPath(FileHelper.newFile(path));
    }

    /**
     * Gets the parent folder path in relation to a file path
     */
    public static String getParentFolderPath(File file) {
        String slashedPath = FilePathHelper.toSlashedPath(file.getAbsolutePath());
        int lastSlashIndex = StringHelper.lastIndexOf(slashedPath, '/');
        return StringHelper.substring(slashedPath, 0, lastSlashIndex);
    }

    /**
     * Gets the relative path in relation to a root path
     */
    public static String getRelativePath(File rootFolder, File file) {
        return FilePathHelper.getRelativePath(rootFolder.getAbsolutePath(), file.getAbsolutePath());
    }

    /**
     * Gets the relative path in relation to a root path
     */
    public static String getRelativePath(String rootFolderPath, File file) {
        return FilePathHelper.getRelativePath(rootFolderPath, file.getAbsolutePath());
    }

    /**
     * Gets the relative path in relation to a root path
     */
    public static String getRelativePath(String rootFolderPath, String path) {
        String slashedRootFolderPath = FilePathHelper.toSlashedPath(rootFolderPath);
        String slashedPath = FilePathHelper.toSlashedPath(path);
        return ConditionalHelper.newTernaryOperation(slashedRootFolderPath.equals(slashedPath),
                () -> "",
                () -> StringHelper.substring(slashedPath, slashedRootFolderPath.length() + 1));
    }

    /**
     * Checks if a path has the extension of a text file
     */
    public static boolean hasTextFileExtension(String path) {
        return FilePathHelper.isMatchingExtension(path, FilePathHelper.TEXT_EXTENSION_LIST);
    }

    /**
     * Checks if a path has a matching extension
     */
    public static boolean isMatchingExtension(String path, List<String> extensionList) {
        return ListHelper.isTrueForAny(extensionList,
                (String extension) -> StringHelper.endsWith(path, extension));
    }

    /**
     * Converts a path to slashed path
     */
    public static String toSlashedPath(String path) {
        return StringReplacementHelper.replace(path, "\\", "/");
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private FilePathHelper() {
        super();
    }

    /**
     * Makes a list of text file extensions
     */
    private static List<String> makeTextExtensionList() {
        List<String> textExtensionList = ListHelper.newArrayList();
        ListHelper.add(textExtensionList, ".css");
        ListHelper.add(textExtensionList, ".env");
        ListHelper.add(textExtensionList, ".gitignore");
        ListHelper.add(textExtensionList, ".html");
        ListHelper.add(textExtensionList, ".java");
        ListHelper.add(textExtensionList, ".js");
        ListHelper.add(textExtensionList, ".md");
        ListHelper.add(textExtensionList, ".py");
        ListHelper.add(textExtensionList, ".ts");
        ListHelper.add(textExtensionList, ".tsx");
        ListHelper.add(textExtensionList, ".txt");
        ListHelper.add(textExtensionList, ".xml");
        ListHelper.add(textExtensionList, ".yml");
        return textExtensionList;
    }
}
