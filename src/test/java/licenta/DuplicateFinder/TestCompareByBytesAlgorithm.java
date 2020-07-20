package licenta.DuplicateFinder;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class TestCompareByBytesAlgorithm {
    @Rule
    public TemporaryFolder temporaryFolder1 = new TemporaryFolder();

    @Rule
    public TemporaryFolder temporaryFolder2 = new TemporaryFolder();

    @Test
    public void testCompareTwoNullFile() throws IOException {
        File file1 = temporaryFolder1.newFile("test1.txt");
        File file2 = temporaryFolder2.newFile("test2.txt");
        MyFileObject myFileObject1 = new MyFileObject();
        MyFileObject myFileObject2 = new MyFileObject();
        //file1
        myFileObject1.setFilePath(file1.getPath());
        myFileObject1.setFileName(file1.getName());
        myFileObject1.setFileSize(file1.getUsableSpace());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        myFileObject1.setFileLastModifiedDate(simpleDateFormat.format(file1.lastModified()));

        //file2
        myFileObject2.setFilePath(file2.getPath());
        myFileObject2.setFileName(file2.getName());
        myFileObject2.setFileSize(file2.getUsableSpace());
        myFileObject2.setFileLastModifiedDate(simpleDateFormat.format(file2.lastModified()));
        GetFiles getFiles = new GetFiles();
        Assert.assertTrue(file1.exists());
        Assert.assertTrue(file2.exists());
        Assert.assertNotEquals(0, getFiles.compareTwoFilesByBites(myFileObject1, myFileObject2));
        Assert.assertTrue(getFiles.compareTwoFilesByBites(myFileObject1, myFileObject2));
    }


    @Test
    public void testCompareTwoFilesWithSameData() throws IOException {

        File file1 = temporaryFolder1.newFile("test1.txt");
        File file2 = temporaryFolder2.newFile("test2.txt");
        FileUtils.writeStringToFile(file1, "in acest rand se testeaza continutul unui fisier");
        FileUtils.writeStringToFile(file2, "in acest rand se testeaza continutul unui fisier");
        MyFileObject myFileObject1 = new MyFileObject();
        MyFileObject myFileObject2 = new MyFileObject();
        //file1
        myFileObject1.setFilePath(file1.getPath());
        myFileObject1.setFileName(file1.getName());
        myFileObject1.setFileSize(file1.getUsableSpace()+100);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        myFileObject1.setFileLastModifiedDate(simpleDateFormat.format(file1.lastModified()));

        //file2
        myFileObject2.setFilePath(file2.getPath());
        myFileObject2.setFileName(file2.getName());
        myFileObject2.setFileSize(file2.getUsableSpace());
        myFileObject2.setFileLastModifiedDate(simpleDateFormat.format(file2.lastModified()));
        GetFiles getFiles = new GetFiles();
        Assert.assertNotNull(getFiles.listFilesForFolder(temporaryFolder1.getRoot()));
        Assert.assertNotNull(getFiles.listFilesForFolder(temporaryFolder2.getRoot()));
        System.out.println("Fisierele au acelasi continut:");
        Assert.assertTrue(getFiles.compareTwoFilesByBites(myFileObject1, myFileObject2));
    }
    @Test
    public void testCompareTwoFilesWithDifferentData() throws IOException {

        File file1 = temporaryFolder1.newFile("test1.txt");
        File file2 = temporaryFolder2.newFile("test2.txt");
        FileUtils.writeStringToFile(file1, "in acest rand se testeaza continutul unui fisier");
        FileUtils.writeStringToFile(file2, "   acest rand se testeaza continutul unui fisier");
        MyFileObject myFileObject1 = new MyFileObject();
        MyFileObject myFileObject2 = new MyFileObject();
        //file1
        myFileObject1.setFilePath(file1.getPath());
        myFileObject1.setFileName(file1.getName());
        myFileObject1.setFileSize(file1.getUsableSpace());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        myFileObject1.setFileLastModifiedDate(simpleDateFormat.format(file1.lastModified()));

        //file2
        myFileObject2.setFilePath(file2.getPath());
        myFileObject2.setFileName(file2.getName());
        myFileObject2.setFileSize(file2.getUsableSpace());
        myFileObject2.setFileLastModifiedDate(simpleDateFormat.format(file2.lastModified()));
        GetFiles getFiles = new GetFiles();
        Assert.assertNotNull(getFiles.listFilesForFolder(temporaryFolder1.getRoot()));
        System.out.println("Fisierele nu au acelasi continut:");
        Assert.assertNotNull(getFiles.listFilesForFolder(temporaryFolder1.getRoot()));
        Assert.assertNotNull(getFiles.listFilesForFolder(temporaryFolder2.getRoot()));
        Assert.assertFalse(getFiles.compareTwoFilesByBites(myFileObject1, myFileObject2));
    }
}
