package licenta.DuplicateFinder;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GetFiles {
    // functia ce permite sa returneze fisierele dintr-un singur folder
    public List<MyFileObject> listFilesForFolder(File folder) {
        List<MyFileObject> listOfMyFileObjects = new ArrayList<MyFileObject>();

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                MyFileObject myFileObject = new MyFileObject();
                myFileObject.setFileName(fileEntry.getName());
                myFileObject.setFilePath(fileEntry.getPath());
                myFileObject.setFileGroup(1);
                double sizeOfFile = Math.floor((double) fileEntry.length() / (1024) * 1000) / 1000;
                myFileObject.setFileSize(sizeOfFile);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                myFileObject.setFileLastModifiedDate(simpleDateFormat.format(fileEntry.lastModified()));
                listOfMyFileObjects.add(myFileObject);


            }
        }
        return listOfMyFileObjects;
    }

    public List<MyFileObject> getAllFilesFromFolders(List<String> list) {
        List<MyFileObject> listOfMyFileObjects = new ArrayList<MyFileObject>();
        for (int i = 0; i < list.size(); i++) {
            String valueFromListString = "";
            valueFromListString = list.get(i);
            File folder = new File(valueFromListString);
            List<MyFileObject> listOfSingleFolder = listFilesForFolder(folder);
            listOfMyFileObjects.addAll(listOfSingleFolder);
        }
        return listOfMyFileObjects;
    }

    public List<MyFileObject> getDuplicateFilesFromList(List<MyFileObject> listOfAllFiles, boolean compareByBytes) {
        List<MyFileObject> listOfFiles = new ArrayList<MyFileObject>(listOfAllFiles);
        List<MyFileObject> listOfDuplicates = new ArrayList<MyFileObject>();
        int fileGroup = 1;

        for (int originalFile = 0; originalFile < listOfFiles.size() - 1; originalFile++) {
            MyFileObject myFileObjectOriginal = listOfFiles.get(originalFile);
            boolean originalFileIsAdded = false;
            for (int incremneDuplicatFile = originalFile + 1; incremneDuplicatFile < listOfFiles
                    .size(); incremneDuplicatFile++) {

                MyFileObject myFileObjectDuplicat = listOfFiles.get(incremneDuplicatFile);

                if (myFileObjectOriginal.getFileSize() == myFileObjectDuplicat.getFileSize()) {
                    boolean isEqualByBytes;
                    if (compareByBytes == true) {
                        isEqualByBytes = compareTwoFilesByBites(myFileObjectOriginal, myFileObjectDuplicat);
                        System.out.println(isEqualByBytes);
                    } else {
                        isEqualByBytes = true;
                    }
                    if (isEqualByBytes) {
                        if (originalFileIsAdded == false) {
                            // adauga in lista si fisierul original
                            myFileObjectOriginal.setFileGroup(fileGroup);
                            listOfDuplicates.add(myFileObjectOriginal);
                            originalFileIsAdded = true;
                            myFileObjectDuplicat.setFileGroup(fileGroup);
                            listOfDuplicates.add(myFileObjectDuplicat);
                            listOfFiles.remove(myFileObjectDuplicat);
                            incremneDuplicatFile--;
                        } else {
                            // adauga doar fisierul duplicat
                            fileGroup--;
                            myFileObjectDuplicat.setFileGroup(fileGroup);
                            listOfDuplicates.add(myFileObjectDuplicat);
                            listOfFiles.remove(myFileObjectDuplicat);
                            incremneDuplicatFile--;
                        }
                        fileGroup++;
                    }

                }

            }

        }

        return listOfDuplicates;
    }

    public static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public boolean compareTwoFilesByBites(MyFileObject myFileObject1, MyFileObject myFileObject2) {
        byte[] firstFileInByte = null;
        byte[] secondFileInByte = null;
        int numberOfFirstBytes = 10;
        int numberOfLastBytes = 10;
        int numberOfAllBytes = 0;
        String bytesForFirstString = "";
        String bytesForSecondString = "";

        try {
            firstFileInByte = Files.readAllBytes(Paths.get(myFileObject1.getFilePath()));
            secondFileInByte = Files.readAllBytes(Paths.get(myFileObject2.getFilePath()));
            for (int i = 0; i < numberOfFirstBytes; i++) {
                bytesForFirstString = bytesForFirstString + firstFileInByte[i];
                bytesForSecondString = bytesForSecondString + secondFileInByte[i];
            }
            numberOfAllBytes = firstFileInByte.length;
            numberOfLastBytes = numberOfAllBytes - numberOfLastBytes;

            for (int i = numberOfLastBytes; i < numberOfAllBytes; i++) {
                bytesForFirstString = bytesForFirstString + firstFileInByte[i];
                bytesForSecondString = bytesForSecondString + secondFileInByte[i];
            }
            for (int i = numberOfLastBytes; i < numberOfAllBytes / 2; i++) {
                bytesForFirstString = bytesForFirstString + firstFileInByte[i];
                bytesForSecondString = bytesForSecondString + secondFileInByte[i];
            }
            System.out.println(bytesForFirstString);
            System.out.println(bytesForSecondString);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (bytesForFirstString.equals(bytesForSecondString)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean compareTwoFilesBySH512(MyFileObject myFileObject1, MyFileObject myFileObject2) {
        return true;
    }
}
