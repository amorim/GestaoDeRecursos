package tk.amorim.decorator;

import tk.amorim.decorator.User;

/**
 * Created by lucas on 01/09/2017.
 */
public class Teacher extends UserDecorator {
    public Teacher(User user) {
        super(user);
        name = user.name;
        email = user.email;
        cpf = user.cpf;
        id = user.id;
        permission = Permission.TEACHER;
    }
}
