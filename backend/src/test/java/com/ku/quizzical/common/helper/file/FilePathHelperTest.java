package com.ku.quizzical.common.helper.file;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test Class for Like Controller
 */
@SpringBootTest
@ActiveProfiles("test")
public class FilePathHelperTest {
    @Test
    void concatenationTest() throws Exception {
        assertThat(FilePathHelper.concatenatePaths("", "d1/d2/d3")).isEqualTo("d1/d2/d3");
        assertThat(FilePathHelper.concatenatePaths("d1/d2/d3", "")).isEqualTo("d1/d2/d3");
        assertThat(FilePathHelper.concatenatePaths("d1/d2", "d3")).isEqualTo("d1/d2/d3");
    }

    @Test
    void extensionlessFileNameTest() throws Exception {
        assertThat(FilePathHelper.getExtensionlessFileName("d1/d2/d3.png")).isEqualTo("d3");
    }

    @Test
    void relativePathTest() throws Exception {
        assertThat(FilePathHelper.getRelativePath("d1/d2/d3", "d1/d2/d3/d4/d5")).isEqualTo("d4/d5");
    }

    @Test
    void hardRelativePathTest() throws Exception {
        String path1 = "d1/d2/d3/d4/d5.txt";
        String path2 = "d1/d2/d3/e4/e5/e6/e7.txt";
        String target = "../e4/e5/e6/e7.txt";
        assertThat(FilePathHelper.getRelativePath(path1, path2)).isEqualTo(target);
    }

    @Test
    void hardRootRelativePathTest() throws Exception {
        String path1 = "d1/d2/d3/d4/d5.txt";
        String path2 = "e1/e2/e3/e4/e5/e6/e7.txt";
        String target = "../../../../e1/e2/e3/e4/e5/e6/e7.txt";
        assertThat(FilePathHelper.getRelativePath(path1, path2)).isEqualTo(target);
    }

    @Test
    void upFolderPathTest() throws Exception {
        assertThat(FilePathHelper.getUpFolderPath("d1/d2/d3/d4/d5", "d1/d2/d3")).isEqualTo("..");
    }

    @Test
    void downFolderPathTest() throws Exception {
        assertThat(FilePathHelper.getDownFolderPath("d1/d2/d3", "d1/d2/d3/d4/d5"))
                .isEqualTo("d4/d5");
    }
}
