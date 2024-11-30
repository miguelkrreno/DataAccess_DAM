package Service;

import dao.ProductoDAO;
import dao.ProductoFavDAO;
import model.Producto;
import model.ProductoFav;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductoFavService {
    ProductoDAO prodDao = new ProductoDAO();
    ProductoFavDAO dao = new ProductoFavDAO();

    public boolean getMetodo(int option, Scanner sc) {
        boolean isExit = true;

        switch (option) {

            case 1:
                getAllProductosFav();
                break;
            case 2:
                RegistrarProductoFav(sc);
                break;
            case 3:
                isExit = false;
                break;
            default:
                System.out.println("ERROR: La Opcion (" + option + ") No es correcta. Debes seleccionar una Opcion del SubMenu del 1 al 3");
                break;

        }
        return isExit;
    }

    private void getAllProductosFav() {
        List<ProductoFav> productosFav = null;
        try {
            productosFav = dao.getAllProductosFAV();
            List<ProductoFav> responseProdFav = new ArrayList<ProductoFav>();

            if(!productosFav.isEmpty()){
                for (var prodFav : productosFav){
                    var producto = prodDao.getProductoById(prodFav.getIdProducto());

                    System.out.println("Id_Fav: " + prodFav.getId());
                    System.out.println("Id_Prod: " + producto.getId());
                    producto.printData();
                    System.out.println();
                }
                System.out.println();
                System.out.println("Nº PRODUCTOS FAVORITOS: "+ productosFav.size());
                System.out.println("--------------------");
            }
            else{
                System.out.println("No se han encontrado Productos favoritos");
            }
        } catch (SQLException e) {
            System.out.println("No se han podido recuperar los productos favoritos");
        }

    }

    private void RegistrarProductoFav(Scanner sc) {
        System.out.println("Registrar productos en favoritos con un precio Mayor, Menor o Igual a ...");
        System.out.println("Insertar un operador  con FORMATO (>) Mayor, (<) Menor o (=) Igual a");
        String operador = sc.next();

        float precio;
        System.out.println("Insertar Precio con FORMATO 00,00");

        try {
            precio = sc.nextFloat();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            String error = "Error: El valor ingresado no es un número válido. Inténtalo de nuevo con este formato Float ( 00,00 ).";
            String msg = "Insertar Precio con FORMATO 00,00";
            String valor = bucleErrorType(error, msg, sc, "float", true);
            precio = Float.parseFloat(valor);
        }

        try {
            List<Producto> productos = prodDao.getProductosQuery(precio, operador);

            for (var producto : productos) {
                var isInserted = dao.registrarProductoFav(producto.getId());
                System.out.println("Producto con Id: " + producto.getId() +" registrado en favoritos : " + isInserted);
            }

        }catch (SQLException e){
            System.out.println("Problemas para agregar productos a favoritos");
        }
    }

    private String bucleErrorType(String error, String msg, Scanner sc, String type, boolean isError) {
        String valor = "";
        while(isError){

            System.out.println(error);
            System.out.println(msg);
            valor  = sc.nextLine();

            try {
                switch (type) {
                    case "float":
                        Float.parseFloat(valor);
                        isError = false;
                        break;
                    case "int":
                        Integer.parseInt(valor);
                        isError = false;
                        break;
                    default:
                        System.out.println("ERROR: El Type (" + type + ") No es correcto. Verifica Type");
                        break;
                }
            }
            catch (Exception e){
                sc.nextLine();
                System.out.println("catch");
                valor =  bucleErrorType(error, msg, sc, type, isError);
                isError = false;
            }
        }
        return valor;
    }
}
