package Service;

import dao.PedidoDAO;
import dao.ProductoDAO;
import model.Pedido;
import model.Producto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PedidoService {
    PedidoDAO dao = new PedidoDAO();
    ProductoDAO prodDao = new ProductoDAO();

    public boolean getMetodo(int option, Scanner sc) {
        boolean isExit = true;

        switch (option) {

            case 1:
                getAllPedidos();
                break;
            case 2:
                RegistrarPedido(sc);
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

    private void getAllPedidos() {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        try {
            pedidos = dao.getPedidos();
        } catch (SQLException e) {
            System.out.println("ERROR: Problemas para mostrar los pedidos: " + e.getMessage());
        }

        if(!pedidos.isEmpty()){
            for (var pedido : pedidos) {
                pedido.printData();
                System.out.println();
            }

            System.out.println("Nº PEDIDOS: "+ pedidos.size());
            System.out.println("--------------------");
            System.out.println();
        }
        else {
            System.out.println("Nº PEDIDOS: 0");
            System.out.println("--------------------");
            System.out.println();
        }
    }

    private void RegistrarPedido(Scanner sc) {
        Pedido pedido = new Pedido();

        System.out.println("Descripcion del pedido");
        sc.nextLine();
        pedido.setDescripcion(sc.nextLine());

        System.out.println("Precio Total del pedido (Formato 00,00)");

        try {
            pedido.setPrecioTotal(sc.nextFloat());
        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            String error = "Error: El valor ingresado no es un número válido. Inténtalo de nuevo con este formato Float ( 00,00 ).";
            String msg = "Precio Total del pedido (Formato 00,00)";
            String valor = bucleErrorType(error, msg, sc, "float", true);
            pedido.setPrecioTotal(Float.parseFloat(valor));
        }

        pedido.setIdProducto(validationIdProducto(sc));


        boolean isInserted = false;
        try {
            isInserted = dao.registrarPedido(pedido);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR: El pedido no se ha podido insertar");
        }

        System.out.println("Pedido Insertado: " + isInserted);
        sc.nextLine();
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
                valor =  bucleErrorType(error, msg, sc, type, isError);
                isError = false;
            }
        }
        return valor;
    }

    private int validationIdProducto(Scanner sc) {
        int idProducto;
        try {
            System.out.println("Producto Id");
            idProducto = sc.nextInt();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            String error = "Error: El valor ingresado no es un número válido. Inténtalo de nuevo con este formato entero ( 00 ).";
            String msg = "Producto Id";
            String valor = bucleErrorType(error, msg, sc, "int", true);
            idProducto  = Integer .parseInt(valor);
        }

        try {
            Producto producto = prodDao.getProductoById(idProducto);
            if (producto.getId() == 0){
                String error = "Error: El producto con Id: " + idProducto + "NO EXISTE. Intenta con un Id de esta lista";
                List<Producto> productos = prodDao.getProductos();

                for (var prod : productos) {
                   prod.printData();
                    System.out.println();
                }
                System.out.println("Producto Id");
                idProducto = sc.nextInt();
            }
        }
        catch (Exception e) {
            validationIdProducto(sc);
        }

        return idProducto;
    }
}
