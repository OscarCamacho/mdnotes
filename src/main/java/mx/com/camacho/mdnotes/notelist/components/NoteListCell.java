package mx.com.camacho.mdnotes.notelist.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import mx.com.camacho.mdnotes.MarkdownNotes;
import mx.com.camacho.mdnotes.model.Note;
import mx.com.camacho.mdnotes.utils.StringUtils;

import java.io.IOException;

public class NoteListCell extends ListCell<Note> {

    @FXML
    private Text noteName;

    @FXML
    private Text noteSummary;

    @FXML
    private CheckBox noteSelected;

    private Note note;

    public NoteListCell () {
        super();
        loadFxml();
    }

    private void loadFxml () {
        FXMLLoader fxmlLoader = new FXMLLoader(MarkdownNotes.class.getResource("notelist/notecell.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Note note, boolean b) {
        super.updateItem(note, b);
        System.out.println("updateItem");
        System.out.println(note);
        if (b || note == null) {
            noteName.setText("Not supposed to be here");
        } else {
            this.note = note;
            noteName.setText(note.getName());
            noteSummary.setText(StringUtils.truncate(note.getFullText(), 15));
        }
    }
}
