package mx.com.camacho.mdnotes.storage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public abstract class AbstractAsyncCrudDao<O, I> implements CrudDao<O, I>{

    private final ExecutorService executorService;

    protected AbstractAsyncCrudDao () {
        this.executorService = Executors.newCachedThreadPool();
    }

    public Future<Optional<O>> createAsync (O toCreate) {
        return this.executorService.submit(() -> create(toCreate));
    }

    public Future<Optional<O>> readAsync (I id) {
        return this.executorService.submit(() -> read(id));
    }

    public Future<Optional<List<O>>> readAllAsync () {
        return this.executorService.submit(this::readAll);
    }

    public Future<Optional<List<O>>> readAllThatAsync (Predicate<O> filter) {
        return this.executorService.submit(() -> readAllThat(filter));
    }

    public Future<Optional<O>> updateAsync (O toUpdate) {
        return this.executorService.submit(() -> update(toUpdate));
    }

    public Future<Optional<Boolean>> deleteAsync (I id) {
        return this.executorService.submit(() -> delete(id));
    }

}
