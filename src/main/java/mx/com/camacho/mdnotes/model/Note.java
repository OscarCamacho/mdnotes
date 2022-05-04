package mx.com.camacho.mdnotes.model;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Note {

    private String fullText;
    private List<String> textPerLine;
    private final Path path;
    private final String name;

    private boolean selected;

    public Note (Path path) {
        this.path = path;
        this.name = path.getFileName().toString();
    }
    public Note (Path path, String contents) {
        this(path);
        this.fullText = contents;
        this.textPerLine = Arrays.stream(contents.split(System.lineSeparator())).collect(Collectors.toList());
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public List<String> getTextPerLine() {
        return textPerLine;
    }

    public void setTextPerLine(List<String> textPerLine) {
        this.textPerLine = textPerLine;
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
