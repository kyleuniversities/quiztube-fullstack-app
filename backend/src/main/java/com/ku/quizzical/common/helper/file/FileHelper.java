package com.ku.quizzical.common.helper.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import com.ku.quizzical.common.helper.ArrayHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ExceptionHelper;
import com.ku.quizzical.common.helper.FunctionHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.TimeHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.file.ByteList;
import com.ku.quizzical.common.util.string.StringList;

/**
 * Helper class for File Operations
 */
public class FileHelper {
    /**
     * Copies a file
     */
    public static void copyFile(File source, File destination) {
        FileHelper.exportByteList(FileHelper.getByteList(source), destination.getAbsolutePath());
    }

    /**
     * Copies a folder
     */
    public static void copyFolder(String sourceFolderPath, String destinationFolderPath) {
        FileHelper.copyFolder(FileHelper.newFile(sourceFolderPath),
                FileHelper.newFile(destinationFolderPath));
    }

    /**
     * Copies a folder
     */
    public static void copyFolder(File source, File destination) {
        String sourceFolderPath = source.getAbsolutePath();
        String destinationFolderPath = destination.getAbsolutePath();
        ConditionalHelper.ifThen(FileHelper.fileExists(destination),
                () -> FileHelper.deleteFolder(destination));
        FileHelper.forEachFileDescendant(sourceFolderPath, (File sourceFile) -> {
            String relativePath = FilePathHelper.getRelativePath(sourceFolderPath, sourceFile);
            File destinationFile = FileHelper.newFile(destinationFolderPath + "/" + relativePath);
            String destinationFileParentFolderPath =
                    FilePathHelper.getParentFolderPath(destinationFile);
            FileHelper.makeNewDirectoryAtPath(destinationFileParentFolderPath);
            FileHelper.copyFile(sourceFile, destinationFile);
        });
    }

    /**
     * Deletes a file
     */
    public static void deleteFile(File file) {
        file.delete();
    }

    /**
     * Deletes a folder
     */
    public static void deleteFolder(File folder) {
        FileHelper.forEachFile(folder.getAbsolutePath(), (File file) -> {
            boolean isDirectory = file.isDirectory();
            ConditionalHelper.ifThen(isDirectory, () -> FileHelper.deleteFolder(file));
            ConditionalHelper.ifThen(!isDirectory, () -> FileHelper.deleteFile(file));
        });
    }

    /**
     * Exports a byte list into a file
     */
    public static void exportByteList(ByteList byteList, String path) {
        byteList.flush(path);
    }

    /**
     * Exports lines as into a text file
     */
    public static void exportStringList(Iterable<String> lines, String path) {
        try {
            PrintWriter writer = FileHelper.newPrintWriter(path);
            for (String line : lines) {
                writer.println(line);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHelper.throwNewIllegalStateException(e.getMessage());
        }
    }

    /**
     * Exports lines as into a text file
     */
    public static void exportText(String text, String path) {
        FileHelper.exportStringList(StringHelper.split(text, "[\n]"), path);
    }

    /**
     * Checks if a file exists
     */
    public static boolean fileExists(String path) {
        return FileHelper.fileExists(FileHelper.newFile(path));
    }

    /**
     * Checks if a file exists
     */
    public static boolean fileExists(File file) {
        return file.exists();
    }

    /**
     * Iterates through each file in directly within a directory
     */
    public static void forEachFile(String folderPath, Consumer<File> action) {
        File folder = FileHelper.newFile(folderPath);
        List<File> children = FileHelper.listFiles(folder);
        ListHelper.forEach(children, action);
    }

    /**
     * Iterates through each file in recursively within a directory
     */
    public static void forEachFileDescendant(String rootFolderPath, Consumer<File> action) {
        FileHelper.forEachFileDescendant(rootFolderPath, false, action);
    }

    /**
     * Iterates through each file in recursively within a directory
     */
    public static void forEachFileDescendant(String rootFolderPath, boolean includeDirectories,
            Consumer<File> action) {
        FileHelper.forEachFile(rootFolderPath, (File file) -> {
            boolean isDirectory = file.isDirectory();
            boolean isProcessable = !isDirectory || includeDirectories;
            ConditionalHelper.ifThen(isProcessable, () -> action.accept(file));
            ConditionalHelper.ifThen(isDirectory, () -> FileHelper
                    .forEachFileDescendant(file.getAbsolutePath(), includeDirectories, action));
        });
    }

    /**
     * Gets the byte list of any file
     */
    public static ByteList getByteList(File file) {
        return ByteList.newInstanceFromFilePath(file.getAbsolutePath());
    }

    /**
     * Gets the time of when a file was last modified - which is the number of milliseconds from
     * (0:00:00 GMT January 1, 1970) that it was modified or 0L if there is an error collecting the
     * file
     */
    public static long getTimeLastModifiedInEpochMilliseconds(File file) {
        return file.lastModified() + TimeHelper.PST_SHIFT;
    }

    /**
     * Gets the file lines of a text file as String List
     */
    public static StringList getStringList(File file) {
        return FileHelper.getStringList(FilePathHelper.getAbsolutePath(file));
    }

    /**
     * Gets the file lines of a text file as String List
     */
    public static StringList getStringList(String path) {
        StringList list = StringList.newInstance();
        try {
            BufferedReader reader = FileHelper.newBufferedReader(path);
            String line = reader.readLine();
            while (line != null) {
                ListHelper.add(list, line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHelper.throwNewIllegalStateException(e.getMessage());
        }
        return list;
    }

    /**
     * Gets the lines of a text file as a String
     */
    public static String getText(String path) {
        return StringHelper.join(FileHelper.getStringList(path), "\n");
    }

    /**
     * Lists the file children of a folder File
     */
    public static List<File> listFiles(File file) {
        return ListHelper.toList(file.listFiles());
    }

    /**
     * Makes a new directory at a specified path. All intermediary parent directories will be made
     * as well. If the directory already exists, do nothing.
     */
    public static void makeNewDirectoryAtPath(String path) {
        FileHelper.newFile(path).mkdirs();
    }

    /**
     * Creates a new Buffered Input Stream object
     */
    public static BufferedInputStream newBufferedInputStream(FileInputStream fileInputStream)
            throws IOException {
        return new BufferedInputStream(fileInputStream);
    }


    /**
     * Creates a new Buffered Output Stream object
     */
    public static BufferedOutputStream newBufferedOutputStream(FileOutputStream fileOutputStream)
            throws IOException {
        return new BufferedOutputStream(fileOutputStream);
    }

    /**
     * Creates a new Buffered Reader object
     */
    public static BufferedReader newBufferedReader(String path) throws IOException {
        return new BufferedReader(new FileReader(FileHelper.newFile(path)));
    }

    /**
     * Creates a new File object
     */
    public static File newFile(String path) {
        return new File(path);
    }

    /**
     * Creates a new File Input Stream object
     */
    public static FileInputStream newFileInputStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    /**
     * Creates a new File Output Stream object
     */
    public static FileOutputStream newFileOutputStream(String path) throws IOException {
        return new FileOutputStream(path);
    }

    /**
     * Creates a new Print Writer object
     */
    public static PrintWriter newPrintWriter(String path) throws IOException {
        return new PrintWriter(path);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private FileHelper() {
        super();
    }
}
