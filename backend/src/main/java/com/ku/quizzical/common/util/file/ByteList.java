package com.ku.quizzical.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import com.ku.quizzical.common.helper.ExceptionHelper;
import com.ku.quizzical.common.helper.file.FileHelper;

/**
 * Utility structure for representing a byte list for a file
 */
public final class ByteList extends LinkedList<Integer> {
    // Class Fields
    private static final long serialVersionUID = 1L;

    // New Instance Methods
    public static ByteList newInstanceFromFilePath(String path) {
        ByteList byteList = ByteList.newInstance();
        byteList.collect(path);
        return byteList;
    }

    public static ByteList newInstance() {
        return new ByteList();
    }

    // Constructor Methods
    private ByteList() {
        super();
    }

    // Operant Methods
    public void collect(String path) {
        try {
            FileInputStream inputStream = FileHelper.newFileInputStream(path);
            BufferedInputStream stream = FileHelper.newBufferedInputStream(inputStream);
            int currentByte = stream.read();
            while (currentByte != -1) {
                this.add(currentByte);
                currentByte = stream.read();
            }
            stream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHelper.throwNewIllegalStateException("Error reading byte list.");
        }
    }

    public void flush(String path) {
        Iterator<Integer> iterator = this.iterator();
        try {
            FileOutputStream outputStream = FileHelper.newFileOutputStream(path);
            BufferedOutputStream stream = FileHelper.newBufferedOutputStream(outputStream);
            while (iterator.hasNext()) {
                stream.write(iterator.next());
            }
            stream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHelper.throwNewIllegalStateException("Error writing byte list.");
        }
    }
}
