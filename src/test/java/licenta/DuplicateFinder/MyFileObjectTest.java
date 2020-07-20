package licenta.DuplicateFinder;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.CDATASection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.*;

class MyFileObjectTest {
    private MyFileObject myFileObject;

    @BeforeEach
    void setUp() {
        myFileObject = new MyFileObject();
        myFileObject.setFileName("testName.jpg");
        myFileObject.setFileSize(10);
        myFileObject.setFilePath("D:\\TestDuplicateFiles\\test1\\testName.jpg");
        myFileObject.setFileGroup(1);
        myFileObject.setFileLastModifiedDate("04/27/2020 19:52:29");
    }

    @Test
    void getFileName() {
        String expectFileName = "testName.jpg";
        Assert.assertEquals(expectFileName, myFileObject.getFileName());
    }

    @Test
    void setFileName() {
        String expectFileName = "example";
        myFileObject.setFileName("example");
        Assert.assertEquals(expectFileName, myFileObject.getFileName());
    }

    @Test
    void getFilePath() {
        String expectedFilePath = "D:\\TestDuplicateFiles\\test1\\testName.jpg";
        Assert.assertEquals(expectedFilePath, myFileObject.getFilePath());
    }

    @Test
    void setFilePath() {
        String expectedFilePath = "D:\\TestDuplicateFiles\\test2\\testName2.jpg";
        myFileObject.setFilePath("D:\\TestDuplicateFiles\\test2\\testName2.jpg");
        Assert.assertEquals(expectedFilePath, myFileObject.getFilePath());
    }

    @Test
    void getFileSize() {
        double expectedFileSize = 10;
        Assert.assertEquals(expectedFileSize, myFileObject.getFileSize(), 0);
    }

    @Test
    void setFileSize() {
        double expectedFileSize = 100;
        myFileObject.setFileSize(100);
        Assert.assertEquals(expectedFileSize, myFileObject.getFileSize(), 0);
    }

    @Test
    void getFileGroup() {
        int expectedFileGroup = 1;
        Assert.assertEquals(expectedFileGroup, myFileObject.getFileGroup());
    }

    @Test
    void setFileGroup() {
        int expectedFileGroup = 2;
        myFileObject.setFileGroup(2);
        Assert.assertEquals(expectedFileGroup, myFileObject.getFileGroup());
    }

    @Test
    void getFileLastModifiedDate() {
        String expectedFileLastModifiedDate = "04/27/2020 19:52:29";
        Assert.assertEquals(expectedFileLastModifiedDate, myFileObject.getFileLastModifiedDate());
    }

    @Test
    void setFileLastModifiedDate() {
        DateTimeFormatter dateTimeFormatter = ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(dateTimeFormatter.format(localDateTime));
        String expectedFileLastModifiedDate = localDateTime.toString();
        myFileObject.setFileLastModifiedDate(expectedFileLastModifiedDate);
        Assert.assertEquals(expectedFileLastModifiedDate, myFileObject.getFileLastModifiedDate());
    }
}