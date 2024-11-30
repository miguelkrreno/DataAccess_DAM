package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection connection;

    private PreparedStatement pst;

    private ResultSet rs;

    public List<Producto> getProductos() throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        connection = new DBConnection().getConnection();

        //SELECT * FROM Productos WHERE nombre = 'NombreDelProducto';
        String query = String.format("SELECT * FROM %s",
                SchemaDB.TAB_PRODUCTOS);

        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();

        while (rs.next()) {
            Producto producto = new Producto();
            producto.setId(rs.getInt("id")); // Suponiendo que tienes un campo `id` en la clase Producto
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setCantidad(rs.getInt("cantidad"));
            producto.setPrecio(rs.getDouble("precio"));

            productos.add(producto); // Agregar el producto a la lista
        }
        return productos;
    }

    public Producto getProductoById(int idProducto) throws SQLException {
        Producto producto = null; // Inicializa como null para distinguir si no se encuentra ningún resultado.
        connection = new DBConnection().getConnection();

        System.out.println("getProductoById");
        String query = String.format("SELECT * FROM %s WHERE %s=?",
                SchemaDB.TAB_PRODUCTOS, SchemaDB.PRODUCTOS_COL_ID);

        pst = connection.prepareStatement(query);
        pst.setInt(1, idProducto);
        rs = pst.executeQuery();

        if (rs.next()) {
            producto = new Producto();
            producto.setId(rs.getInt("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setCantidad(rs.getInt("cantidad"));
            producto.setPrecio(rs.getDouble("precio"));

            System.out.println("Producto encontrado: " + producto.getNombre());
        } else {
            System.out.println("No se encontraron resultados para el id: " + idProducto);
        }

        return producto;
    }


    public List<Producto> getProductosQuery(double precio, String operador) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        connection = new DBConnection().getConnection();

        //SELECT * FROM Productos WHERE nombre = 'NombreDelProducto';
        String query = String.format("SELECT * FROM %s WHERE %s"+operador+"?",
                SchemaDB.TAB_PRODUCTOS, SchemaDB.PRODUCTOS_COL_PRECIO);

        pst = connection.prepareStatement(query);
        pst.setDouble(1, precio);
        rs = pst.executeQuery();

        while (rs.next()) {
            Producto producto = new Producto();
            producto.setId(rs.getInt("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setCantidad(rs.getInt("cantidad"));
            producto.setPrecio(rs.getDouble("precio"));

            productos.add(producto);
        }
        return productos;
    }

    public boolean registrarProducto(Producto producto) throws SQLException {
        connection = new DBConnection().getConnection();

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUE (?, ?, ?, ?)",
                SchemaDB.TAB_PRODUCTOS,
                SchemaDB.PRODUCTOS_COL_NOMBRE, SchemaDB.PRODUCTOS_COL_DESCRIPCION, SchemaDB.PRODUCTOS_COL_CANTIDAD, SchemaDB.PRODUCTOS_COL_PRECIO);

        pst = connection.prepareStatement(query);
        pst.setString(1, producto.getNombre());
        pst.setString(2, producto.getDescripcion());
        pst.setInt(3, producto.getCantidad());
        pst.setDouble(4, producto.getPrecio());

        return pst.executeUpdate() > 0;
    }


}
