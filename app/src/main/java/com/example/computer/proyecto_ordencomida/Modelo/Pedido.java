package com.example.computer.proyecto_ordencomida.Modelo;

import java.util.List;

/**
 * Created by Computer on 1/02/2018.
 */

public class Pedido {

    private String telefono;
    private String nombre;
    private String direccion;
    private String total;
    private List<Orden> listaOrdenComidas;


    public Pedido() {
    }

    public Pedido(String telefono, String nombre, String direccion, String total, List<Orden> listaOrdenComidas) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.total = total;
        this.listaOrdenComidas = listaOrdenComidas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Orden> getListaOrdenComidas() {
        return listaOrdenComidas;
    }

    public void setListaOrdenComidas(List<Orden> listaOrdenComidas) {
        this.listaOrdenComidas = listaOrdenComidas;
    }
}
