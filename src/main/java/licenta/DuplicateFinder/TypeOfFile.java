package licenta.DuplicateFinder;

public class TypeOfFile {
    private int textFiles;
    private int videoFiles;
    private int musicFiles;
    private int imageFiles;
    private int unknownFiles;

    public int getTextFiles() {
        return textFiles;
    }

    public void setTextFiles(int textFiles) {
        this.textFiles = textFiles;
    }

    public int getVideoFiles() {
        return videoFiles;
    }

    public void setVideoFiles(int videoFiles) {
        this.videoFiles = videoFiles;
    }

    public int getMusicFiles() {
        return musicFiles;
    }

    public void setMusicFiles(int musicFiles) {
        this.musicFiles = musicFiles;
    }

    public int getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(int imageFiles) {
        this.imageFiles = imageFiles;
    }

    public int getUnknownFiles() {
        return unknownFiles;
    }

    public void setUnknownFiles(int unknownFiles) {
        this.unknownFiles = unknownFiles;
    }
}
