package licenta.DuplicateFinder;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;


import javafx.event.ActionEvent;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabStep1;
    @FXML
    private Tab tabStep2;
    @FXML
    private Tab tabStep3;

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


    //Step 2
    @FXML
    private Button btnViewResults;

    @FXML
    private Label labelFilesScaned;

    @FXML
    private Label labelDuplicatesFound;

    @FXML
    private Label labelSpaceUsed;

    @FXML
    private VBox vBoxARC;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    @FXML
    PieChart pieChartOfFiles;

    @FXML
    PieChart pieChartUsedSpace;


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
    private CheckBox checkBoxSelectAllDuplicates;

    @FXML
    private CheckBox checkBoxSelectAllFiles;

    @FXML
    private CheckBox checkBoxCompareBySHA512;

    @FXML
    private Button btnDeleteDuplicates;

    @FXML
    private Button btnClearTable;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea textArea;

    @FXML
    Label labelFileName;

    @FXML
    Label labelFileType;


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
        Image image = new Image(getClass().getResourceAsStream("images/plus.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        btnAddDirectory.setGraphic(imageView);
        Image image2 = new Image(getClass().getResourceAsStream("images/minus.png"));
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(25);
        imageView2.setFitWidth(25);
        btnRemoveDirectory.setGraphic(imageView2);
        vBoxARC.setSpacing(8);
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
        if (list.getItems().size() != 0) {
            TypeOfFile countTypeOfFile = new TypeOfFile();
            SizeOfFile sizeOfFile = new SizeOfFile();
            tableView.getItems().clear();
            //selecteaza tab-ul 2 din tabPane
            tabPane.getSelectionModel().select(tabStep2);
            //creaza un task Worker
            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    GetFiles getFiles = new GetFiles();
                    List<MyFileObject> listaCuObiecte = new ArrayList<MyFileObject>(getFiles.getAllFilesFromFolders(list.getItems()));
                    List<MyFileObject> listOfDuplicates = new ArrayList<MyFileObject>(
                            getFiles.getDuplicateFilesFromList(listaCuObiecte, chckbxCompareByBytes.isSelected(), checkBoxCompareBySHA512.isSelected()));

                    //scoate fiecare elemnt din lista de tip MyFileObject
                    for (int i = 0; i < listOfDuplicates.size(); i++) {

                        System.out.println(listOfDuplicates.get(i).getFileName());
                        String path = listOfDuplicates.get(i).getFilePath();
                        String fileExtension = path.substring(path.lastIndexOf('.') + 1);
                        double fileSize = listOfDuplicates.get(i).getFileSize();

                        switch (fileExtension) {
                            case "txt":
                            case "doc":
                            case "docx":
                            case "pdf":
                                countTypeOfFile.setTextFiles(countTypeOfFile.getTextFiles() + 1);
                                sizeOfFile.setTextFileSize(sizeOfFile.getTextFileSize() + fileSize);
                                break;
                            case "jpg":
                            case "jpeg":
                            case "gif":
                            case "png":
                                countTypeOfFile.setImageFiles(countTypeOfFile.getImageFiles() + 1);
                                sizeOfFile.setImageFileSize(sizeOfFile.getImageFileSize() + fileSize);
                                break;
                            case "mp3":
                            case "wav":
                            case "wma":
                            case "aac":
                            case "m4a":
                            case "flac":
                                countTypeOfFile.setMusicFiles(countTypeOfFile.getMusicFiles() + 1);
                                sizeOfFile.setMusicFileSize(sizeOfFile.getMusicFileSize() + fileSize);
                                break;
                            case "mp4":
                            case "webm":
                            case "mkv":
                            case "flv":
                                countTypeOfFile.setVideoFiles(countTypeOfFile.getVideoFiles() + 1);
                                sizeOfFile.setVideoFileSize(sizeOfFile.getVideoFileSize() + fileSize);
                                break;
                            default:
                                countTypeOfFile.setUnknownFiles(countTypeOfFile.getUnknownFiles() + 1);
                                sizeOfFile.setUnknownFileSize(sizeOfFile.getUnknownFileSize() + fileSize);
                        }

                        //Creaza un nou obiect de tip MyFileObjectWithCheckBox pentru a putea sa-l aduge in tabel cu CheckBox-uri
                        MyFileObjectWithCheckBox myFileObjectWithCheckBox = new MyFileObjectWithCheckBox();
                        //creaza un checkBox pentru obiectul de tip MyFileObjectWithCheckBox
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
                        updateProgress(i, listOfDuplicates.size() - 1);
                        updateMessage(listOfDuplicates.get(i).getFilePath());
                        //Thread.sleep(100);
                    }
                    return null;
                }
            };

            task.messageProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    progressLabel.setText("Read: " + t1);
                    //creaza datele pentru numarul de fisiere
                    ObservableList<PieChart.Data> pieDataOfFiles = FXCollections.observableArrayList(
                            new PieChart.Data("Text", countTypeOfFile.getTextFiles()),
                            new PieChart.Data("Music", countTypeOfFile.getMusicFiles()),
                            new PieChart.Data("Video", countTypeOfFile.getVideoFiles()),
                            new PieChart.Data("Picture", countTypeOfFile.getImageFiles()),
                            new PieChart.Data("Other", countTypeOfFile.getUnknownFiles())
                    );
                    //asigneaza datele in Pichart
                    pieChartOfFiles.setData(pieDataOfFiles);
                    pieChartOfFiles.setTitle("Files");

                    //creaza datele pentru numarul de spatiul folosit
                    ObservableList<PieChart.Data> pieDataUsedSpace = FXCollections.observableArrayList(
                            new PieChart.Data("Text", sizeOfFile.getTextFileSize()),
                            new PieChart.Data("Music", sizeOfFile.getMusicFileSize()),
                            new PieChart.Data("Video", sizeOfFile.getVideoFileSize()),
                            new PieChart.Data("Picture", sizeOfFile.getImageFileSize()),
                            new PieChart.Data("Other", sizeOfFile.getUnknownFileSize())
                    );
                    //asigneaza datele in Pichart
                    pieChartUsedSpace.setData(pieDataUsedSpace);
                    pieChartUsedSpace.setTitle("Used Space");


                }
            });
            //sterge valorile din progressBar-ul anterior
            progressBar.progressProperty().unbind();
            //leaga progressBar cu task worker
            progressBar.progressProperty().bind(task.progressProperty());
            //creza un fir de executie
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } else {
            System.out.println("Introdu un director");
        }
    }

    public void chckbxCompareByBytesAddAction(ActionEvent event){
        if (chckbxCompareByBytes.isSelected()){
            System.out.println("este selectat dupa biti");
            checkBoxCompareBySHA512.setSelected(false);
        }else{
            System.out.println("nu este selectat dupa biti");
        }
    }
    public void checkBoxCompareBySHA512AddAction(ActionEvent event){
        if (checkBoxCompareBySHA512.isSelected()){
            System.out.println("este selectat dupa sha512");
            chckbxCompareByBytes.setSelected(false);
        }else{
            System.out.println("nu este selectat dupa sha512");
        }
    }


    //Toate ActionEvent din Step 3
    //Adauga ActionEvent pentu CheckBox-ul checkBoxSelectAllDuplicates
    public void checkBoxSelectAllDuplicatesAddAction(ActionEvent event) {

        if (checkBoxSelectAllDuplicates.isSelected()) {
            if (checkBoxSelectAllFiles.isSelected()) {
                checkBoxSelectAllFiles.setSelected(false);
            }
            int testValueOfGroup = 1;

            for (int i = 0; i < tableView.getItems().size(); i++) {
                int valueOfGroup = (Integer) tableView.getItems().get(i).getFileGroup();
                if (testValueOfGroup == valueOfGroup) {
                    CheckBox checkBox2 = new CheckBox();
                    checkBox2.setSelected(false);
                    tableView.getItems().get(i).setFileCheckBox(checkBox2);
                    tableView.refresh();
                    testValueOfGroup++;
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(true);
                    tableView.getItems().get(i).setFileCheckBox(checkBox);
                    tableView.refresh();
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

    //Adauga ActionEvent pentu CheckBox-ul chckbxSelectAllFiles
    public void chckbxSelectAllFilesAddAction(ActionEvent event) {

        if (checkBoxSelectAllFiles.isSelected()) {
            if (checkBoxSelectAllDuplicates.isSelected()) {
                checkBoxSelectAllDuplicates.setSelected(false);
            }
            int testValueOfGroup = 0;
            for (int i = 0; i < tableView.getItems().size(); i++) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(true);
                tableView.getItems().get(i).setFileCheckBox(checkBox);
                tableView.refresh();
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
    public void btnViewResultAddAction(ActionEvent event) {
        System.out.println("step 3");
        tabPane.getSelectionModel().select(tabStep3);
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
    }

    //Adauga ActionEvent pentu butonul btnClearTable
    public void btnClearTableAddAction(ActionEvent event) {
        tableView.getItems().clear();
    }


    public void btnTextAddAction() {
        textArea.setVisible(true);
    }

    public void readSelectedFile() {
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                String path = tableView.getSelectionModel().getSelectedItem().getFilePath();
                try {
                    //ruleaza o comanda din cmd
                    Process builder = Runtime.getRuntime().exec("cmd /c explorer.exe /select, " + path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                File file = new File(tableView.getSelectionModel().getSelectedItem().getFilePath());
                String path = tableView.getSelectionModel().getSelectedItem().getFilePath();

                String fileExtension = path.substring(path.lastIndexOf('.') + 1);
                System.out.println(fileExtension);
                CenterImage centerImage = new CenterImage();
                labelFileName.setText("File Name: " + tableView.getSelectionModel().getSelectedItem().getFileName());

                //verifica fisierele txt
                if (fileExtension.equals("txt")) {
                    imageView.setVisible(false);
                    textArea.clear();
                    textArea.setVisible(true);
                    labelFileType.setVisible(false);
                    try {
                        //citeste fisierul
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
//                } else if (fileExtension.equals("docx")) {
//                    FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

                } else if (fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("jpeg") || fileExtension.equals("gif")) {
                    textArea.setVisible(false);
                    imageView.setVisible(true);
                    labelFileType.setVisible(false);
                    System.out.println(file);
                    String fileLocation = file.toURI().toString();
                    System.out.println(fileLocation);
                    Image image = new Image(fileLocation);
                    imageView.setImage(image);
                    if (fileExtension.equals("jpg")) {
                        centerImage.centerImageInImageView(image, imageView);
                    }
                } else if (fileExtension.equals("mp3") || fileExtension.equals("wav") || fileExtension.equals("wma") || fileExtension.equals("aac") || fileExtension.equals("m4a") || fileExtension.equals("flac")) {
                    textArea.setVisible(false);
                    imageView.setVisible(false);
                    labelFileType.setVisible(true);
                    labelFileType.setText("Music File");
                } else if (fileExtension.equals("mp4") || fileExtension.equals("webm") || fileExtension.equals("mkv") || fileExtension.equals("flv")) {
                    textArea.setVisible(false);
                    imageView.setVisible(false);
                    labelFileType.setVisible(true);
                    labelFileType.setText("Video File");
                } else {
                    textArea.setVisible(false);
                    imageView.setVisible(false);
                    labelFileType.setVisible(true);
                    labelFileType.setText("Unknown File");
                }
            } catch (Exception exception) {
                exception.getStackTrace();
            }
        });
    }
}

