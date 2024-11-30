package Service;

import dao.EmpleadoDAO;
import model.Empleado;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmpleadoService {
    EmpleadoDAO dao = new EmpleadoDAO();

    public boolean getMetodo(int option, Scanner sc) {
        boolean isExit = true;

        switch (option) {

            case 1:
                getAllEmpleados();
                break;
            case 2:
                RegistrarEmpleado(sc);
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

    private void getAllEmpleados() {
        List<Empleado> empleados = null;
        try {
            empleados = dao.getEmpleados();
        } catch (SQLException e) {
            System.out.println("ERROR: Problemas para mostrar empleados: " + e.getMessage());
        }

        if(empleados != null){
            for (var empleado : empleados) {
                empleado.printData();
                System.out.println();
            }

            System.out.println("Nº EMPLEADOS: "+ empleados.size());
            System.out.println("--------------------");
            System.out.println();
        }
        else {
            System.out.println("Nº EMPLEADOS: 0");
            System.out.println("--------------------");
            System.out.println();
        }
    }

    private void RegistrarEmpleado(Scanner sc) {
        Empleado empleado = new Empleado();

        System.out.println("Nombre del empleado");
        empleado.setNombre(sc.nextLine());
        System.out.println("Apellidos del empleado");
        empleado.setApellidos(sc.nextLine());
        System.out.println("Correo del empleado");
        empleado.setCorreo(sc.next());

        boolean isInserted = false;
        try {
            isInserted = dao.registrarEmpleado(empleado);
        } catch (SQLException e) {
            System.out.println("ERROR: El empleado no se ha podido insertar");
        }

        System.out.println("Empleado Insertado: " + isInserted);
    }
}
