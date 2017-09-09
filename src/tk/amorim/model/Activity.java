package tk.amorim.model;

import tk.amorim.decorator.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 01/09/2017.
 */
public abstract class Activity {
    private int id;
    private String title;
    private String description;
    private String material;
    private List<User> users = new ArrayList<>();

    public static Activity getInstanceByType(int type) {
        if (type == 0)
            return new ClassicActivity();
        if (type == 1)
            return new PresentationActivity();
        return new LabActivity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
