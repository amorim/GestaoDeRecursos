package tk.amorim.db;

import tk.amorim.decorator.Permission;
import tk.amorim.decorator.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lucas on 04/09/2017.
 */
public class UserFiltering implements Filtering<User> {
    private List<User> users;

    public UserFiltering(List<User> users) {
        this.users = users;
    }

    public UserFiltering byCPF(String cpf) {
        users = users.stream().filter(u -> u.getCpf().equals(cpf)).collect(Collectors.toList());
        return this;
    }

    public UserFiltering byName(String name) {
        users = users.stream().filter(u -> u.getName().contains(name)).collect(Collectors.toList());
        return this;
    }

    public UserFiltering byPermission(Permission perm) {
        users = users.stream().filter(u -> u.getPermissionLevel().indexOf(perm) != -1).collect(Collectors.toList());
        return this;
    }

    public UserFiltering byID(int id) {
        users = users.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        return this;
    }

    public int count() {
        return users.size();
    }

    public List<User> getQuerySet() {
        return users;
    }
}
