package Service;

import dao.ProductoDAO;
import database.SchemaDB;
import model.Producto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductoService {

    ProductoDAO dao = new ProductoDAO();

    public boolean getMetodo(int option, Scanner sc) {
        boolean isExit = true;

        switch (option) {

            case 1:
                getAllProductos();
                break;
            case 2:
                getProductosByPrecio(sc);
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

    private void getAllProductos() {
        List<Producto> productos = null;
        try {
            productos = dao.getProductos();
            if(productos.isEmpty()){
                List<Producto> productosAPI = getProductosAPI();
                insertProductByAPI(productosAPI);
                productos = dao.getProductos();
            }

            for (var producto : productos) {
                producto.printData();
                System.out.println();
            }

            System.out.println("Nº PRODUCTOS: "+ productos.size());
            System.out.println("--------------------");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("ERROR: Problemas para recuperar los products");
        }



    }

    private void insertProductByAPI(List<Producto> productos) throws SQLException {
        int cont = 0;
        for (var producto : productos) {
            var isInserted = dao.registrarProducto(producto);
            System.out.println("Producto Insertado: " + isInserted);
            cont++;
        }
        System.out.println(cont + " Producto(s) insertados en BBDD correctamente");

    }

    private void getProductosByPrecio(Scanner sc)  {
        System.out.println("Buscar un producto segun si su precio es (>) Mayor, (<) Menor o (=) Igual a ...");
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
            var productos = dao.getProductosQuery(precio, operador);
            if(!productos.isEmpty()){
                for (var producto : productos) {
                    producto.printData();
                    System.out.println();
                }

                System.out.println("Nº PRODUCTOS Con precio " + operador + " a " + precio + " euros: "+ productos.size());
                System.out.println("--------------------");
                System.out.println();
            }
            else {
                System.out.println("Nº PRODUCTOS Con precio " + operador + " a " + precio + " euros: 0");
                System.out.println("--------------------");
                System.out.println();
            }

        }catch (SQLException e){
            System.out.println("Problemas para buscar productos con los datos ingresados");
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



    private List<Producto> getProductosAPI() {
        List<Producto> productos = new ArrayList<>();
        Producto producto = new Producto();
        try{
            URL url = new URL(SchemaDB.URL_API_PRODUCTOS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int cont = 0;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();
            JSONObject response = new JSONObject(line);
            JSONArray productosAPI = response.getJSONArray("products");

            for (Object prodAPI : productosAPI) {
                if (prodAPI instanceof JSONObject){
                    producto.setNombre(((JSONObject) prodAPI).getString("title"));
                    producto.setDescripcion(((JSONObject) prodAPI).getString("description"));
                    producto.setPrecio(((JSONObject) prodAPI).getDouble("price"));
                    producto.setCantidad(((JSONObject) prodAPI).getInt("stock"));

                    productos.add(producto);
                    cont++;
                }
            }

            System.out.println(cont + " Producto(s) extraidos de la API correctamente");
        } catch (MalformedURLException e) {
            System.out.println("ERROR: Problemas para conectar con la API y extraer productos");
        }
        catch (IOException e) {
            System.out.println("ERROR: Problemas para conectar con la API y extraer productos");
        }

        return productos;
    }
}
