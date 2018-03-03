package com.example.computer.proyecto_ordencomida.Modelo;

/**
 * Created by Computer on 30/01/2018.
 */

public class Orden {

    private String productId;
    private String productNombre;
    private String cantidad;
    private String precio;
    private String descuento;

    public Orden() {
    }

    public Orden(String productId, String productNombre, String cantidad, String precio, String descuento) {
        this.productId = productId;
        this.productNombre = productNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNombre() {
        return productNombre;
    }

    public void setProductNombre(String productNombre) {
        this.productNombre = productNombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }
}


