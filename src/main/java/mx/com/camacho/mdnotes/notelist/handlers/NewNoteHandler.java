package mx.com.camacho.mdnotes.notelist.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mx.com.camacho.mdnotes.constants.StringConstants;
import mx.com.camacho.mdnotes.storage.file.NotesAsyncDao;

public class NewNoteHandler implements EventHandler<ActionEvent> {

    private final NotesAsyncDao notesAsyncDao;
    private final Button button;
    private final TextField newNoteNameField;
    private final ImageView add;
    private final ImageView save;
    private boolean readyToCreate = false;

    public NewNoteHandler (Button button, TextField newNoteNameField, ImageView add, ImageView save) {
        this.button = button;
        this.newNoteNameField = newNoteNameField;
        this.add = add;
        this.save = save;
        this.notesAsyncDao = NotesAsyncDao.getInstance();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String noteName = newNoteNameField.getText();
        readyToCreate = !noteName.isBlank();
        if (!readyToCreate) {
            button.setText(StringConstants.CREATE_NEW_NOTE);
            button.setGraphic(this.save);
            newNoteNameField.setVisible(true);
        } else {
            button.setText(StringConstants.NEW_NOTE);
            button.setGraphic(this.add);
            newNoteNameField.setVisible(false);
            // TODO: Use notesAsyncDao.create
        }
    }

}
