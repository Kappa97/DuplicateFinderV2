package licenta.DuplicateFinder;

public class SizeOfFile {
    private double textFileSize;
    private double videoFileSize;
    private double musicFileSize;
    private double imageFileSize;
    private double unknownFileSize;

    public double getTextFileSize() {
        return textFileSize;
    }

    public void setTextFileSize(double textFileSize) {
        this.textFileSize = textFileSize;
    }

    public double getVideoFileSize() {
        return videoFileSize;
    }

    public void setVideoFileSize(double videoFileSize) {
        this.videoFileSize = videoFileSize;
    }

    public double getMusicFileSize() {
        return musicFileSize;
    }

    public void setMusicFileSize(double musicFileSize) {
        this.musicFileSize = musicFileSize;
    }

    public double getImageFileSize() {
        return imageFileSize;
    }

    public void setImageFileSize(double imageFileSize) {
        this.imageFileSize = imageFileSize;
    }

    public double getUnknownFileSize() {
        return unknownFileSize;
    }

    public void setUnknownFileSize(double unknownFileSize) {
        this.unknownFileSize = unknownFileSize;
    }
}
