package dao;

import database.DBConnection;
import database.SchemaDB;
import model.ProductoFav;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoFavDAO {

    private Connection connection;

    private PreparedStatement pst;

    private ResultSet rs;

    public List<ProductoFav> getAllProductosFAV() throws SQLException {
        List<ProductoFav> productosFav = new ArrayList<ProductoFav>();
        connection = new DBConnection().getConnection();

        String query = String.format("SELECT * FROM %s",
                SchemaDB.TAB_PRODUCTOSFAV);

        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();

        while (rs.next()) {
            ProductoFav productoFav = new ProductoFav();
            productoFav.setId(rs.getInt("id"));
            productoFav.setIdProducto(rs.getInt("idProducto"));

            productosFav.add(productoFav);
        }
        return productosFav;
    }

    public boolean registrarProductoFav(int idProducto) throws SQLException {
        connection = new DBConnection().getConnection();

        String query = String.format("INSERT INTO %s (%s) VALUE (?)",
                SchemaDB.TAB_PRODUCTOSFAV,
                SchemaDB.PRODUCTOSFAV_COL_IDPRODUCTO);

        pst = connection.prepareStatement(query);
        pst.setInt(1, idProducto);

        return pst.executeUpdate() > 0;
    }
}
