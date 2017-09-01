package tk.amorim.model;

import tk.amorim.decorator.User;

import java.util.Date;

/**
 * Created by lucas on 01/09/2017.
 */
public abstract class Allocation {
    private int id;
    private Date start;
    private Date end;
    private Activity activity;
    private Resource resource;
    private User owner;

    public Allocation(Allocation alloc) {
        this.id = alloc.getId();
        this.start = alloc.getStart();
        this.end = alloc.getEnd();
        this.activity = alloc.getActivity();
        this.resource = alloc.getResource();
        this.owner = alloc.getOwner();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
