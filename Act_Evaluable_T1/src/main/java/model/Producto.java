package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {
    private int id;
    private String nombre, descripcion;
    private int cantidad;
    private double precio;


    public void printData(){
        System.out.println("Nombre: " + getNombre());
        System.out.println("Descripcion: " + getDescripcion());
        System.out.println("Precio: " + getPrecio());
        System.out.println("Cantidad: " + getCantidad());
        System.out.println();
    }
}
