package licenta.DuplicateFinder;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TestAlgorithmOfComparation {

    @Rule
    public TemporaryFolder temporaryFolder1 = new TemporaryFolder();

    @Test
    public void testEmptyListOfFiles(){
        GetFiles getFiles = new GetFiles();
        System.out.println(getFiles.listFilesForFolder(temporaryFolder1.getRoot()).size());
        Assert.assertEquals(0, getFiles.listFilesForFolder(temporaryFolder1.getRoot()).size());
    }
    @Test
    public void listAFolderTest() throws IOException {
        File file1 = temporaryFolder1.newFile("test1.txt");
        File file2 = temporaryFolder1.newFile("test2.txt");
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

        FileUtils.writeStringToFile(file1, "in acest rand se testeaza continutul unui fisier");
        FileUtils.writeStringToFile(file2, "en acest rand se testeaza continutul unui fisier");

        GetFiles getFiles = new GetFiles();
        List list = new ArrayList();
        list = getFiles.listFilesForFolder(temporaryFolder1.getRoot());

        Assert.assertNotEquals(0, getFiles.listFilesForFolder(temporaryFolder1.getRoot()).size());
        System.out.println("Algoritmul comparat dupa marimea fisierului");
        System.out.println(getFiles.getDuplicateFilesFromList(list, false, false));
        Assert.assertNotEquals(0, getFiles.getDuplicateFilesFromList(list, false, false).size());

        Assert.assertEquals(0, getFiles.getDuplicateFilesFromList(list, true, true).size());
        System.out.println("\nAlgoritmul comparat dupa continutul fisierului");
        System.out.println(getFiles.getDuplicateFilesFromList(list, true, true));
    }
}
