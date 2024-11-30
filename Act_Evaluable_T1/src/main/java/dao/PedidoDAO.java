package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private Connection connection;

    private PreparedStatement pst;

    private ResultSet rs;

    public List<Pedido> getPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        connection = new DBConnection().getConnection();

        String query = String.format("SELECT * FROM %s",
                SchemaDB.TAB_PEDIDOS);

        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();

        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setId(rs.getInt("id"));
            pedido.setDescripcion(rs.getString("descripcion"));
            pedido.setPrecioTotal(rs.getDouble("precioTotal"));
            pedido.setIdProducto(rs.getInt("idProducto"));

            pedidos.add(pedido);
        }
        return pedidos;
    }

    public boolean registrarPedido(Pedido pedido) throws SQLException {
        connection = new DBConnection().getConnection();

        String query = String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)",
                SchemaDB.TAB_PEDIDOS,
                SchemaDB.PEDIDOS_COL_DESCRIPCION, SchemaDB.PEDIDOS_COL_PRECIOTOTAL, SchemaDB.PEDIDOS_COL_IDPRODUCTO);

        pst = connection.prepareStatement(query);
        pst.setString(1, pedido.getDescripcion());
        pst.setDouble(2, pedido.getPrecioTotal());
        pst.setInt(3, pedido.getIdProducto());

        return pst.executeUpdate() > 0;
    }
}
