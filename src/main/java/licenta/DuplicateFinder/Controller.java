package licenta.DuplicateFinder;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;


import javafx.event.ActionEvent;



import java.io.File;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    //Step 1
    @FXML
    private Button btnAddDirectory;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnRemoveDirectory;

    @FXML
    private Button btnStartScan;

    @FXML
    private CheckBox chckbxCompareByBytes;

    @FXML
    private ListView list;


    //Step 3
    @FXML
    private TableView<MyFileObjectWithCheckBox> tableView;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, String> checkBoxTableColumn;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, String> fileNameTableColumn;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, String> filePathTableColumn;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, Double> fileSizeTableColumn;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, Integer> fileGroupTableColumn;

    @FXML
    private TableColumn<MyFileObjectWithCheckBox, String> fileLastModifiedDateTableColumn;

    @FXML
    private CheckBox chckbxSelectAll;

    @FXML
    private Button btnDeleteDuplicates;

    @FXML
    private Button btnClearTable;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnTest;

    @FXML
    private TextArea textArea;

    //asocierea coloanelor cu obiectul MyFileObjectWithCheckBox
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, String>("fileCheckBox"));
        fileNameTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, String>("fileName"));
        filePathTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, String>("filePath"));
        fileSizeTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, Double>("fileSize"));
        fileGroupTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, Integer>("fileGroup"));
        fileLastModifiedDateTableColumn.setCellValueFactory(new PropertyValueFactory<MyFileObjectWithCheckBox, String>("fileLastModifiedDate"));
        textArea.setEditable(false);
        readSelectedFile();
    }

    //Toate ActionEvent din Step 1
    // adauga ActionEvent pentu butonul btnAddDirectory
    public void btnAddDirectoryAddAction(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        //!!! doar pentru calculatorul meu la urma trebuie de sters !!!
        chooser.setInitialDirectory(new File("D:\\TestDuplicateFiles"));
        File selectedDirectory = chooser.showDialog(null);
        if (selectedDirectory != null) {
            list.getItems().add(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("file is not valid");
        }
    }

    // adauga ActionEvent pentu butonul btnClear
    public void btnClearAddAction(ActionEvent event) {
        list.getItems().clear();
    }

    //adauga ActionEvent pentru butonul btnRemoveDirectory
    public void btnRemoveDirectoryAddAction(ActionEvent event) {
        try {
            int index = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(index);
        } catch (Exception e2) {
            System.out.println("Selecteaza un element");
        }
    }

    //adauga ActionEvent pentru butonul btnStartScanAddAction
    public void btnStartScanAddAction(ActionEvent event) {
        // creaza constructorul
        GetFiles getFiles = new GetFiles();
        // creaza lista cu toate obiectele
        List<MyFileObject> listaCuObiecte = new ArrayList<MyFileObject>(getFiles.getAllFilesFromFolders(list.getItems()));
        List<MyFileObject> listOfDuplicates = new ArrayList<MyFileObject>(
                getFiles.getDuplicateFilesFromList(listaCuObiecte, chckbxCompareByBytes.isSelected()));

        //scoate fiecare elemnt din lista de tip MyFileObject
        for (int i = 0; i < listOfDuplicates.size(); i++) {
            System.out.println(listOfDuplicates.get(i).getFileName());

            //Creaza un nou obiect de tip MyFileObjectWithCheckBox pentru a putea sa-l aduge in tabel cu CheckBox-uri
            MyFileObjectWithCheckBox myFileObjectWithCheckBox = new MyFileObjectWithCheckBox();
            CheckBox selectCheckBox = new CheckBox();
            selectCheckBox.setSelected(false);
            myFileObjectWithCheckBox.setFileCheckBox(selectCheckBox);
            myFileObjectWithCheckBox.setFileName(listOfDuplicates.get(i).getFileName());
            myFileObjectWithCheckBox.setFilePath(listOfDuplicates.get(i).getFilePath());
            myFileObjectWithCheckBox.setFileSize(listOfDuplicates.get(i).getFileSize());
            myFileObjectWithCheckBox.setFileGroup(listOfDuplicates.get(i).getFileGroup());
            myFileObjectWithCheckBox.setFileLastModifiedDate(listOfDuplicates.get(i).getFileLastModifiedDate());
            //adauga la fiecare rand obiectul de tip MyFileObjectWithCheckBox
            tableView.getItems().add(myFileObjectWithCheckBox);
            tableView.setEditable(true);
        }
    }

    //Toate ActionEvent din Step 3
    //Adauga ActionEvent pentu CheckBox-ul chckbxSelectAll
    public void chckbxSelectAllAddAction(ActionEvent event) {

        if (chckbxSelectAll.isSelected()) {

            int testValueOfGroup = 0;
            for (int i = 0; i < tableView.getItems().size(); i++) {
                int valueOfGroup = (Integer) tableView.getItems().get(i).getFileGroup();
                if (testValueOfGroup == valueOfGroup) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(true);
                    tableView.getItems().get(i).setFileCheckBox(checkBox);
                    tableView.refresh();
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(false);
                    tableView.getItems().get(i).setFileCheckBox(checkBox);
                    tableView.refresh();
                    testValueOfGroup++;
                }

            }
            System.out.println("is selected");
        } else {

            for (int i = 0; i < tableView.getItems().size(); i++) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(false);
                tableView.getItems().get(i).setFileCheckBox(checkBox);
                tableView.refresh();
            }
            System.out.println("is not selected");
        }

    }

    //Adauga ActionEvent pentu butonul btnDeleteDuplicates
    public void btnDeleteDuplicatesAddAction(ActionEvent event) {
        System.out.println("delete");
        //MyFileObjectWithCheckBox myFileObjectWithCheckBox3 = tableView.getSelectionModel().getSelectedCells();
        for (int i = 0; i < tableView.getItems().size(); i++) {
            if (tableView.getItems().get(i).getFileCheckBox().isSelected()) {
                //creazaun obiect de tip MyFileObjectWithCheckBox daca este selectat checkBox-ul
                MyFileObjectWithCheckBox myFileObjectWithCheckBox = tableView.getItems().get(i);
                //sterge fisierul din pc
                try {
                    File deleteFile = new File(myFileObjectWithCheckBox.getFilePath());
                    if (deleteFile.delete()) {
                        System.out.println("File successefully deleted");
                    } else {
                        System.out.println("Failed to delete the file");
                    }
                } catch (Exception e1) {
                    System.out.println("File don't loaded");
                }
                //sterge randul din tabeldin tabel
                tableView.getItems().remove(myFileObjectWithCheckBox);
                i--;
            }
        }

        //selecteaza randul selectat
//        MyFileObjectWithCheckBox myFileObjectWithCheckBox = tableView.getSelectionModel().getSelectedItem();
//        System.out.println(myFileObjectWithCheckBox.getFileName());


    }

    //Adauga ActionEvent pentu butonul btnClearTable
    public void btnClearTableAddAction(ActionEvent event) {
        tableView.getItems().clear();
    }


    public void showImageOnMpuseClicked() {
//        tableView.setOnMouseClicked((MouseEvent event) -> {
//            if (event.getButton().equals(MouseButton.PRIMARY)) {
//                File imageFile = new File(tableView.getSelectionModel().getSelectedItem().getFilePath());
//                System.out.println(imageFile);
//                String fileLocation = imageFile.toURI().toString();
//                System.out.println(fileLocation);
//                Image image = new Image(fileLocation);
//                imageView.setImage(image);
//            }
//        });
    }

    public void btnTextAddAction() {
        textArea.setVisible(true);
        //citeste un fisier de tip txt
//        try {
//            File file = new File("D:/TestDuplicateFiles/test1/propzitia1.txt");    //creates a new file instance
//
//            FileReader fileReader = new FileReader(file);   //reads the file
//            BufferedReader bufferedReader = new BufferedReader(fileReader);  //creates a buffering character input stream
//            StringBuffer stringBuffer = new StringBuffer();    //constructs a string buffer with no characters
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuffer.append(line);      //appends line to string buffer
//                stringBuffer.append("\n");     //line feed
//            }
//            fileReader.close();    //closes the stream and release the resources
//            System.out.println("Contents of File: ");
//            System.out.println(stringBuffer.toString());   //returns a string that textually represents the object
//            textArea.appendText(stringBuffer.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            //textArea.setVisible(false);
//        }
        //citeste un fisier Word
//        try {
//            FileInputStream fileInputStream = new FileInputStream("word.docx");
//            XWPFDocument docx = new XWPFDocument(fileInputStream);
//            List<XWPFParagraph> paragraphList = docx.getParagraphs();
//            for (XWPFParagraph paragraph : paragraphList){
//                System.out.println(paragraph.getText());
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void readSelectedFile() {
        tableView.setOnMouseClicked((MouseEvent event) -> {

            //verifica fisierele txt
            File file = new File(tableView.getSelectionModel().getSelectedItem().getFilePath());
            String path = tableView.getSelectionModel().getSelectedItem().getFilePath();
            String fileExtension = path.substring(path.lastIndexOf('.')+1);
            System.out.println(fileExtension);
            if(fileExtension.equals("txt")){
                textArea.clear();
                textArea.setVisible(true);
                try {
                    FileReader fileReader = new FileReader(file);   //reads the file
                    BufferedReader bufferedReader = new BufferedReader(fileReader);  //creates a buffering character input stream
                    StringBuffer stringBuffer = new StringBuffer();    //constructs a string buffer with no characters
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);      //appends line to string buffer
                        stringBuffer.append("\n");     //line feed
                    }
                    fileReader.close();    //closes the stream and release the resources
                    System.out.println("Contents of File: ");
                    System.out.println(stringBuffer.toString());   //returns a string that textually represents the object
                    textArea.appendText(stringBuffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    textArea.setFont(new Font(34));
                    textArea.setText("something is wrong");
                }
            }else if (fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("jpeg") || fileExtension.equals("gif")){
                textArea.setVisible(false);
                System.out.println(file);
                String fileLocation = file.toURI().toString();
                System.out.println(fileLocation);
                Image image = new Image(fileLocation);

                imageView.setImage(image);
            }else {
                textArea.setVisible(true);
                textArea.setFont(new Font(35));
                textArea.setStyle("-fx-text-alignment: center");
                textArea.setText("Can't read the file");
            }

        });

    }



}

