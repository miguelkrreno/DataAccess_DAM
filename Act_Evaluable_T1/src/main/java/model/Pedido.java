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
public class Pedido implements Serializable {
    private int id;
    private String descripcion;
    private double precioTotal;
    private int idProducto;

    public void printData(){
        System.out.println("Descripcion: " + getDescripcion());
        System.out.println("Precio: " + getPrecioTotal());
        System.out.println("IdProducto: " + getIdProducto());
    }
}
