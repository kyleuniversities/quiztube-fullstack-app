package com.ku.quizzical.common.helper.file;

import java.io.File;
import java.util.List;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.number.IntegerHelper;
import com.ku.quizzical.common.helper.string.StringDeleterHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;
import com.ku.quizzical.common.util.wrapper.IntegerWrapper;
import com.ku.quizzical.common.util.wrapper.StringWrapper;

/**
 * Helper class for File Path Operations
 */
public class FilePathHelper {
    // Class Fields
    private static final List<String> TEXT_EXTENSION_LIST = FilePathHelper.makeTextExtensionList();

    /**
     * Concatenates a parent folder path and a relativePath
     */
    public static String concatenatePaths(String parentFolderPath, String relativePath) {
        boolean willUseConcatenatingSlash = !parentFolderPath.isEmpty() && !relativePath.isEmpty();
        String concatenatingText =
                ConditionalHelper.ifReturnElse(willUseConcatenatingSlash, "/", "");
        return parentFolderPath + concatenatingText + relativePath;
    }

    /**
     * Gets the absolute path of a file
     */
    public static String getAbsolutePath(File file) {
        return file.getAbsolutePath();
    }

    /**
     * Gets the relative down folder path in relation to a root folder path to a destination path
     */
    public static String getDownFolderPath(String rootFolderPath, String destinationPath) {
        if (rootFolderPath.isEmpty()) {
            return destinationPath;
        }
        if (rootFolderPath.equals(destinationPath)) {
            return "";
        }
        return destinationPath.substring(rootFolderPath.length() + 1);
    }

    /**
     * Gets the parent folder path in relation to a file path
     */
    public static String getDeepestCommonPath(String path1, String path2) {
        String slashedPath1 = FilePathHelper.toSlashedPath(path1);
        String slashedPath2 = FilePathHelper.toSlashedPath(path2);
        StringWrapper commonPath = StringWrapper.newInstance(slashedPath1);
        ConditionalHelper.whileLoop(() -> !slashedPath2.startsWith(commonPath.getValue()), () -> {
            commonPath.setValue(FilePathHelper.getParentFolderPath(commonPath.getValue()));

        });
        return commonPath.getValue();
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
        String slashedPath = FilePathHelper.toSlashedPath(path);
        int lastSlashIndex = StringHelper.lastIndexOf(slashedPath, '/');
        return ConditionalHelper.newTernaryOperation(lastSlashIndex > -1,
                () -> StringHelper.substring(slashedPath, 0, lastSlashIndex), () -> "");
    }

    /**
     * Gets the parent folder path in relation to a file path
     */
    public static String getParentFolderPath(File file) {
        return FilePathHelper.getParentFolderPath(file.getAbsolutePath());
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
     * Gets the relative path in relation to a source path. The source path does not have to be a
     * parent folder of the path
     */
    public static String getRelativePath(String sourcePath, String destinationPath) {
        String slashedSourcePath = FilePathHelper.toSlashedPath(sourcePath);
        String slashedDestinationPath = FilePathHelper.toSlashedPath(destinationPath);
        String commonSlashedPath =
                FilePathHelper.getDeepestCommonPath(slashedSourcePath, slashedDestinationPath);
        String upFolderPath = FilePathHelper.getUpFolderPath(slashedSourcePath, commonSlashedPath);
        String downFolderPath =
                FilePathHelper.getDownFolderPath(commonSlashedPath, slashedDestinationPath);
        return FilePathHelper.concatenatePaths(upFolderPath, downFolderPath);
    }

    /**
     * Gets the relative up folder path in relation to a source path to a root folder path. The
     * source path is treated as a file. So "nouns/food/fruit/apple" would only have ".." to
     * "nouns/food", even if apple is a folder
     */
    public static String getUpFolderPath(String sourcePath, String rootFolderPath) {
        StringBuilder upFolderPath = StringHelper.newBuilder();
        String slashedSourcePath = FilePathHelper.toSlashedPath(sourcePath);
        String slashedRootFolderPath = FilePathHelper.toSlashedPath(rootFolderPath);
        if (slashedSourcePath.equals(slashedRootFolderPath)) {
            return "";
        }
        int sourceNumberOfSlashes = StringHelper.numberOfInstancesWithin(slashedSourcePath, "/");
        int rootFolderNumberOfSlashes =
                StringHelper.numberOfInstancesWithin(slashedRootFolderPath, "/");
        int slashIndexShift = ConditionalHelper.ifReturnElse(rootFolderPath.isEmpty(), 0, 1);
        IterationHelper.forEach(
                IntegerHelper.max(
                        sourceNumberOfSlashes - rootFolderNumberOfSlashes - slashIndexShift, 0),
                () -> upFolderPath.append("../"));
        ConditionalHelper.ifThen(upFolderPath.length() > 0,
                () -> StringDeleterHelper.deleteLastCharacters(upFolderPath, 1));
        return upFolderPath.toString();
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
