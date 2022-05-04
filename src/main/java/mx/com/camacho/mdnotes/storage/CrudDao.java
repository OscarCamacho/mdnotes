package mx.com.camacho.mdnotes.storage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface CrudDao<O, I> {

    Optional<O> create (O toCreate);

    Optional<O> read (I id) throws IOException;

    Optional<List<O>> readAll ();

    Optional<List<O>> readAllThat (Predicate<O> condition);

    Optional<O> update (O toUpdate) throws IOException;

    Optional<Boolean> delete (I id) throws IOException;

}
