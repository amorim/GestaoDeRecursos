package tk.amorim.db;

import tk.amorim.model.Activity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lucas on 04/09/2017.
 */
public class ActivityFiltering implements Filtering<Activity> {
    private List<Activity> activities;

    ActivityFiltering(List<Activity> activities) {
        this.activities = activities;
    }

    public ActivityFiltering byID(int id) {
        activities = activities.stream().filter(a -> a.getId() == id).collect(Collectors.toList());
        return this;
    }

    public ActivityFiltering byType(int type) {
        activities = activities.stream().filter(a -> a.getType() == type).collect(Collectors.toList());
        return this;
    }

    @Override
    public int count() {
        return activities.size();
    }

    @Override
    public List<Activity> getQuerySet() {
        return activities;
    }
}
