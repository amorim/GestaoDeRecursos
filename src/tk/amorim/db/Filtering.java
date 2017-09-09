package tk.amorim.db;

import java.util.List;

/**
 * Created by lucas on 04/09/2017.
 */
public interface Filtering<T> {
    int count();
    List<T> getQuerySet();
}
