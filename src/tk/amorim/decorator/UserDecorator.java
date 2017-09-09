package tk.amorim.decorator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by lucas on 01/09/2017.
 */
public abstract class UserDecorator extends User {

    private User user;

    public UserDecorator(User user) {
        this.user = user;
    }

    @Override
    public ArrayList<Permission> getPermissionLevel() {
        ArrayList<Permission> arr = user.getPermissionLevel();
        arr.add(permission);
        return arr;
    }
}
