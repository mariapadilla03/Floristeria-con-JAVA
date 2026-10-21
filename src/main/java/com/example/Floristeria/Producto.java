package com.example.Floristeria;
import java.math.BigDecimal;

public class Producto {
    private final String nombre;
    private final String categoria;
    private final BigDecimal precio;
    private final String imagen;


    public Producto(String nombre, String categoria, BigDecimal precio, String imagen){
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre(){
        return nombre;
    }

    public String getCategoria(){
        return categoria;
    }

    public BigDecimal getPrecio(){
        return precio;
    }

    public String getImagen(){
        return imagen;
    }
}
