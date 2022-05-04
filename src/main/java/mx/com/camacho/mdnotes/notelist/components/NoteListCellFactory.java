package mx.com.camacho.mdnotes.notelist.components;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mx.com.camacho.mdnotes.model.Note;

public class NoteListCellFactory implements Callback<ListView<Note>, ListCell<Note>> {

    @Override
    public ListCell<Note> call(ListView<Note> noteListView) {
        return new NoteListCell();
    }
}
