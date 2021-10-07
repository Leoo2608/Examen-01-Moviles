package com.example.examen_01_moviles.models;

public class Producto {
    public String uid;
    public String nombre;
    public double precio;
    public String proveedor;
    public String categoria;

    public Producto() { }

    public Producto(String uid, String nombre, double precio,String proveedor,  String categoria) {
        this.uid = uid;
        this.nombre = nombre;
        this.precio = precio;
        this.proveedor = proveedor;
        this.categoria = categoria;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
