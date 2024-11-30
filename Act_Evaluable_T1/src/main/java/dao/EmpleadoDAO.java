package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Empleado;
import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private Connection connection;

    private PreparedStatement pst;

    private ResultSet rs;

    public List<Empleado> getEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<Empleado>();
        connection = new DBConnection().getConnection();

        String query = String.format("SELECT * FROM %s",
                SchemaDB.TAB_EMPLEADOS);

        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();

        while (rs.next()) {
            Empleado empleado = new Empleado();
            empleado.setId(rs.getInt("id"));
            empleado.setNombre(rs.getString("nombre"));
            empleado.setApellidos(rs.getString("apellidos"));
            empleado.setCorreo(rs.getString("correo"));

            empleados.add(empleado);
        }
        return empleados;
    }

    public boolean registrarEmpleado(Empleado empleado) throws SQLException {
        connection = new DBConnection().getConnection();

        String query = String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)",
                SchemaDB.TAB_EMPLEADOS,
                SchemaDB.PRODUCTOS_COL_NOMBRE, SchemaDB.EMPLEADOS_COL_APELLIDOS, SchemaDB.EMPLEADOS_COL_CORREO);

        pst = connection.prepareStatement(query);
        pst.setString(1, empleado.getNombre());
        pst.setString(2, empleado.getApellidos());
        pst.setString(3, empleado.getCorreo());

        return pst.executeUpdate() > 0;
    }
}
