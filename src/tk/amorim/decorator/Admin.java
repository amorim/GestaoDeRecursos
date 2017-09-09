package tk.amorim.decorator;

/**
 * Created by lucas on 01/09/2017.
 */
public class Admin extends UserDecorator {

    public Admin(User user) {
        super(user);
        name = user.name;
        email = user.email;
        cpf = user.cpf;
        id = user.id;
        permission = Permission.ADMIN;
    }
}
