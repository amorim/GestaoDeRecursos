package tk.amorim.decorator;

/**
 * Created by lucas on 01/09/2017.
 */
public class UserDecorator extends User {

    private User user;

    public UserDecorator(User user) {
        this.user = user;
    }
}
