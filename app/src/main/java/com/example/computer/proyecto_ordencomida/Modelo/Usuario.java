package com.example.computer.proyecto_ordencomida.Modelo;

/**
 * Created by Computer on 30/01/2018.
 */

public class Usuario {

    private String Name;
    private String Password;
    private String Phone;


    public Usuario() {
    }

    public Usuario(String name, String password, String phone) {
        Name = name;
        Password = password;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
