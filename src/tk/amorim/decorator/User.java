package tk.amorim.decorator;

import java.util.ArrayList;

/**
 * Created by lucas on 01/09/2017.
 */
public class User {
    int id;
    String name;
    String email;
    String cpf;
    Permission permission;

    public ArrayList<Permission> getPermissionLevel() {
        ArrayList<Permission> perm = new ArrayList<>();
        perm.add(permission);
        return perm;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public User toRole(int role) {
        if (role == 1)
            return new Student(this);
        if (role == 2)
            return new Teacher(this);
        return new Researcher(this);
    }
}
