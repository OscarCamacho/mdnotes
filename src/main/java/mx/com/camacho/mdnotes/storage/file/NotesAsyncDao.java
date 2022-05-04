package mx.com.camacho.mdnotes.storage.file;

import static mx.com.camacho.mdnotes.constants.StringConstants.DEFAULT_DIRECTORY_NAME;
import static mx.com.camacho.mdnotes.constants.StringConstants.MARKDOWN_FILE_EXTENSION;

import mx.com.camacho.mdnotes.model.Note;
import mx.com.camacho.mdnotes.storage.AbstractAsyncCrudDao;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NotesAsyncDao extends AbstractAsyncCrudDao<Note, Path> {
    private static final NotesAsyncDao instance = new NotesAsyncDao();
    public static NotesAsyncDao getInstance() {
        return instance;
    }

    private final Path DEFAULT_NOTES_DIRECTORY = Paths.get(System.getProperty("user.home"), DEFAULT_DIRECTORY_NAME);

    private NotesAsyncDao () {
        super();
    }

    public Optional<Note> create(Note toCreate) {
            try {
                if (Files.notExists(toCreate.getPath())) {
                    Files.createFile(toCreate.getPath());
                }
                return Optional.of(new Note(Files.write(toCreate.getPath(),
                        toCreate.getFullText().getBytes(StandardCharsets.UTF_8))));
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return Optional.empty();
            }
    }

    public Optional<Note> read(Path id) throws IOException {
            if (Files.exists(id)) {
                return Optional.of(new Note(id, Files.readString(id)));
            }
            return Optional.empty();
    }

    public Optional<List<Note>> readAll() {
            try (Stream<Path> paths = Files.walk(DEFAULT_NOTES_DIRECTORY)) {
                return Optional.of(paths
                        .filter(path -> path.getFileName().toString().endsWith(MARKDOWN_FILE_EXTENSION))
                        .map(NotesAsyncDao::buildNote)
                        .collect(Collectors.toList()));
            } catch (Exception e) {
                return Optional.empty();
            }
    }

    public Optional<List<Note>> readAllThat(Predicate<Note> condition) {
            try (Stream<Path> paths = Files.walk(DEFAULT_NOTES_DIRECTORY)) {
                return Optional.of(paths
                        .filter(path -> path.getFileName().toString().endsWith(MARKDOWN_FILE_EXTENSION))
                        .map(NotesAsyncDao::buildNote)
                        .filter(condition)
                        .collect(Collectors.toList()));
            } catch (Exception e) {
                return Optional.empty();
            }
    }

    public Optional<Note> update(Note toUpdate) throws IOException {
        if (Files.exists(toUpdate.getPath())) {
            Files.write(toUpdate.getPath(), toUpdate.getFullText().getBytes(StandardCharsets.UTF_8));
            return Optional.of(toUpdate);
        }
        return Optional.empty();
    }

    public Optional<Boolean> delete(Path id) throws IOException {
        return Optional.of(Files.deleteIfExists(id));
    }

    private static Note buildNote (Path path) {
        String contents;
        try {
            contents = Files.readString(path);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not read the contents of %s", path));
        }
        return new Note(path, contents);
    }
}
