package tk.amorim.db;

import tk.amorim.decorator.User;
import tk.amorim.model.Activity;
import tk.amorim.model.Allocation;
import tk.amorim.model.Resource;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Created by lucas on 01/09/2017.
 */
public abstract class Database {
    private AtomicInteger atomic = new AtomicInteger(1000);
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();
    private ArrayList<Allocation> allocations = new ArrayList<>();

    public UserFiltering getUsers() throws Exception {
        return new UserFiltering(users);
    }
    public int getUniqueIdForInstance() throws Exception {
        return atomic.getAndIncrement();
    }
    public void addUser(User user) throws Exception {
        users.add(user);
    }
    public void addActivity(Activity activity) throws Exception {
        activities.add(activity);
    }
    public ActivityFiltering getActivities() throws Exception {
        return new ActivityFiltering(this.activities);
    }

    public void addResource(Resource res) throws Exception {
        resources.add(res);
    }
    public ArrayList<Resource> getResources() throws Exception {
        return resources;
    }
    public void addAllocation(Allocation alloc) throws Exception {
        allocations.add(alloc);
    }
    public ArrayList<Allocation> getAllocations() throws Exception {
        return allocations;
    }
}
