package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PhotoSize {

    @JsonProperty(required = true)
    private String file_id;

    @JsonProperty(required = true)
    private int width;

    @JsonProperty(required = true)
    private int height;

    private int file_size;
    private String file_path;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return "PhotoSize{" +
                "file_id='" + file_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", file_size=" + file_size +
                ", file_path='" + file_path + '\'' +
                '}';
    }

    public static String ListFilesToString(List<PhotoSize> listPhotos) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (PhotoSize photo : listPhotos) {
            if (!first) {
                sb.append("\n");
            }
            first = false;
            sb.append("file_id=").append(photo.getFile_id())
                    .append("\n\twidth=").append(photo.getWidth()).append(" height=").append(photo.getHeight())
                    .append("\n\tfile_size=").append(photo.getFile_size());
            String file_path = photo.getFile_path();
            if (file_path != null && !file_path.isEmpty()) {
                sb.append("\n\tfile_path=").append(file_path);
            }
        }
        return sb.toString();
    }

}
