package mx.com.camacho.mdnotes.storage.file;

import static org.junit.jupiter.api.Assertions.*;

import mx.com.camacho.mdnotes.model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class NotesAsyncDaoTest {

    private static final String ROOT_DIRECTORY = ".notes";
    private static final Note NOTE_TO_CREATE = new Note(Path.of(ROOT_DIRECTORY,"test.md"), "contents");

    private NotesAsyncDao underTest;

    private List<Path> pathsToDelete;

    @BeforeEach
    public void setUp () throws IOException {
        underTest = NotesAsyncDao.getInstance();
        pathsToDelete = new ArrayList<>();

        Files.createDirectory(Path.of(ROOT_DIRECTORY));
    }

    @Test
    public void create_shouldConstructANewFile_withValidParameters ()
            throws ExecutionException, InterruptedException, IOException {
        pathsToDelete.add(NOTE_TO_CREATE.getPath());

        Optional<Note> created = underTest.createAsync(NOTE_TO_CREATE).get();

        assertTrue(created.isPresent());
        assertTrue(Files.exists(created.orElseThrow().getPath()));
        assertEquals(NOTE_TO_CREATE.getFullText(), Files.readString(created.orElseThrow().getPath()));
    }

    @Test
    public void create_shouldOverwriteFileContents_withAnExistingFile ()
            throws IOException, ExecutionException, InterruptedException {
        createFile();

        Optional<Note> overwritten = underTest.createAsync(NOTE_TO_CREATE).get();

        assertTrue(overwritten.isPresent());
        String actualContents = Files.readString(overwritten.orElseThrow().getPath());
        assertNotEquals("something", actualContents);
        assertEquals(NOTE_TO_CREATE.getFullText(), actualContents);
    }

    @Test
    public void read_shouldReturnTheExpectedInformation_withValidParameters ()
            throws IOException, ExecutionException, InterruptedException {
        createFile();

        Optional<Note> read = underTest.readAsync(NOTE_TO_CREATE.getPath()).get();

        assertTrue(read.isPresent());
        assertEquals(NOTE_TO_CREATE.getFullText(), read.orElseThrow().getFullText());
    }

    @Test
    public void read_shouldReturnEmptyOptional_withInvalidParameters () throws IOException {
        Optional<Note> read = underTest.read(NOTE_TO_CREATE.getPath());

        assertTrue(read.isEmpty());
    }

    @Test
    public void readAll_shouldReturnExpectedList_withValidParameters ()
            throws IOException, ExecutionException, InterruptedException {
        createFile("note1.md", "note 1 contents");
        createFile("note2.md", "note 2 contents");
        createFile("note3.md", "note 3 contents");
        createFile("not_a_note_1.txt", "not a note 1");
        createFile("not_a_note_2.abc", "not a note 2");

        Optional<List<Note>> notesRead = underTest.readAllAsync().get();

        assertTrue(notesRead.isPresent());
        var notesReadList = notesRead.orElseThrow()
                .stream()
                .map(note -> note.getPath().getFileName().toString())
                .collect(Collectors.toList());

        assertTrue(notesReadList.contains("note1.md"));
        assertTrue(notesReadList.contains("note2.md"));
        assertTrue(notesReadList.contains("note3.md"));
        assertFalse(notesReadList.contains("not_a_note_1.txt"));
        assertFalse(notesReadList.contains("not_a_note_2.abc"));
    }

    @Test
    public void readAllThat_shouldRetrievedExpectedFiles ()
            throws IOException, ExecutionException, InterruptedException {
        createFile("valid_1.md", "valid note 1");
        createFile("valid_2.md", "valid note 2");
        createFile("valid_3.md", "valid note 3");
        createFile("simple1.md", "not valid note 1");
        createFile("simple2.md", "not valid note 2");

        Optional<List<Note>> allRead = underTest.readAllThatAsync(note -> note.getPath().getFileName()
                .toString().contains("valid_")).get();

        assertTrue(allRead.isPresent());
        var readNames = allRead.orElseThrow().stream()
                .map(note -> note.getPath().getFileName().toString())
                .collect(Collectors.toList());
        assertTrue(readNames.contains("valid_1.md"));
        assertTrue(readNames.contains("valid_2.md"));
        assertTrue(readNames.contains("valid_3.md"));
        assertFalse(readNames.contains("simple1.md"));
        assertFalse(readNames.contains("simple2.md"));
    }

    @Test
    public void update_shouldReturnExpectedInstance () throws IOException, ExecutionException, InterruptedException {
        createFile();
        Note toUpdate = new Note(NOTE_TO_CREATE.getPath(), "updated");

        Optional<Note> updated = underTest.updateAsync(toUpdate).get();

        assertTrue(updated.isPresent());
        assertEquals(toUpdate, updated.orElseThrow());
    }

    @Test
    public void delete_shouldReturnTrue_whenItDeletedTheRequestedNote ()
            throws IOException, ExecutionException, InterruptedException {
        createFile();

        Optional<Boolean> deleted = underTest.deleteAsync(NOTE_TO_CREATE.getPath()).get();

        assertTrue(deleted.isPresent());
        assertTrue(deleted.orElseThrow());
    }

    private void createFile () throws IOException {
        createFile(NotesAsyncDaoTest.NOTE_TO_CREATE.getPath().getFileName().toString(), NotesAsyncDaoTest.NOTE_TO_CREATE.getFullText());
    }

    private void createFile (String fileName, String contents) throws IOException {
        Path file = Files.createFile(Path.of(ROOT_DIRECTORY, fileName));
        Files.write(file, contents.getBytes(StandardCharsets.UTF_8));
        pathsToDelete.add(file);
    }

    @AfterEach
    public void tearDown () throws IOException {
        for (Path path : pathsToDelete) {
            Files.deleteIfExists(path);
        }
        Files.deleteIfExists(Path.of(ROOT_DIRECTORY));
    }

}
