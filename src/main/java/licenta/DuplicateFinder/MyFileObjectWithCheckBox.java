package licenta.DuplicateFinder;

import javafx.scene.control.CheckBox;

public class MyFileObjectWithCheckBox extends MyFileObject {
    private CheckBox fileCheckBox;


    public CheckBox getFileCheckBox() {
        return fileCheckBox;
    }

    public void setFileCheckBox(CheckBox fileCheckBox) {
        this.fileCheckBox = fileCheckBox;
    }
}

