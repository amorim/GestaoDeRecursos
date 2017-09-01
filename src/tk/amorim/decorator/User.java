package tk.amorim.decorator;

import java.util.Comparator;

/**
 * Created by lucas on 01/09/2017.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String cpf;
    Permission permission;

    public Permission getPermissionLevel() {
        return permission;
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

    @Override
    public boolean equals(Object other) {
       return ((User)other).getCpf().equals(cpf);
    }
}
