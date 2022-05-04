package mx.com.camacho.mdnotes.notelist.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import mx.com.camacho.mdnotes.constants.ApplicationIcon;
import mx.com.camacho.mdnotes.model.Note;
import mx.com.camacho.mdnotes.notelist.components.NoteListCellFactory;
import mx.com.camacho.mdnotes.notelist.handlers.NewNoteHandler;
import mx.com.camacho.mdnotes.storage.file.NotesAsyncDao;

import java.util.List;
import java.util.Optional;

import static mx.com.camacho.mdnotes.utils.IconUtils.loadIcon;

public class NoteListController {

    @FXML
    private Button newNoteButton;
    @FXML
    private TextField newNoteNameField;

    @FXML
    private ListView<Note> notesListView;

    @FXML
    private AnchorPane noNotesErrorContainer;

    private final NotesAsyncDao notesAsyncDao = NotesAsyncDao.getInstance();

    public NoteListController () {}

    @FXML
    public void initialize () {
        initializeNewNotesButton();
        initializeNotesListView();
    }

    public void initializeNewNotesButton () {
        ImageView addImage = new ImageView(loadIcon(ApplicationIcon.ADD));
        ImageView saveImage = new ImageView(loadIcon(ApplicationIcon.SAVE));
        addImage.setFitWidth(20);
        addImage.setFitHeight(20);
        saveImage.setFitWidth(20);
        saveImage.setFitHeight(20);
        newNoteButton.setGraphic(addImage);
        NewNoteHandler newNoteHandler = new NewNoteHandler(newNoteButton, newNoteNameField, addImage, saveImage);
        newNoteButton.setOnAction(newNoteHandler);
    }

    public void initializeNotesListView () {
        notesListView.setCellFactory(new NoteListCellFactory());
        Optional<List<Note>> storedNotes = notesAsyncDao.readAll();
        if (storedNotes.isEmpty() || storedNotes.orElseThrow().isEmpty()) {
            notesListView.setVisible(false);
            noNotesErrorContainer.setVisible(true);
        } else {
            ObservableList<Note> noteObservableList = FXCollections.observableList(storedNotes.orElseThrow());
            System.out.println("Notes recovered size: " + noteObservableList.size());
            notesListView.setItems(noteObservableList);
        }
    }

}
