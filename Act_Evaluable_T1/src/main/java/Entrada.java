import Service.EmpleadoService;
import Service.PedidoService;
import Service.ProductoFavService;
import Service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Entrada {

    public static void main(String[] args) {
        boolean isOpenGesAlmacen = true;
        boolean isSubOption = true;
        String menu;
        int option;
        int subOption;
        List<String> options;
        List<String> subOptions;
        ProductoService productoService = new ProductoService();
        EmpleadoService empleadoService = new EmpleadoService();
        PedidoService pedidoService = new PedidoService();
        ProductoFavService productoFavService = new ProductoFavService();




        options = new ArrayList<String>();
        options.add("¿Que quieres hacer?");
        options.add("1. Pedidos");
        options.add("2. Productos");
        options.add("3. Empleados");
        options.add("4. ProductosFAV");
        options.add("5. Salir");


        System.out.println("***************** Bienvenido al programa GesAlmacen *****************");
        Scanner sc = new Scanner(System.in);

        while(isOpenGesAlmacen) {
            menu = showMenu(sc, options);
            try {

                option = Integer.parseInt(menu);
            }
            catch (NumberFormatException e) {
                option = 8;
                System.out.println();
            }

            switch(option) {

                case 1:
                    isSubOption = true;
                    while (isSubOption) {
                        System.out.println("3. *** PEDIDOS ***");
                        System.out.println("Selecciona ...");

                        subOptions = new ArrayList<String>();
                        subOptions.add("1. Ver Todos los pedidos");
                        subOptions.add("2. Insertar un pedido");                        ;
                        subOptions.add("3. Salir");

                        var subMenu = showMenu(sc, subOptions);

                        subOption = Integer.parseInt(subMenu);
                        isSubOption = pedidoService.getMetodo(subOption, sc);
                    }
                    break;

                case 2:
                    isSubOption = true;
                    while (isSubOption){
                        System.out.println("2. *** PRODUCTOS ***");
                        System.out.println("Selecciona ...");

                        subOptions = new ArrayList<String>();
                        subOptions.add("1. Ver Todos los Productos");
                        subOptions.add("2. Ver Productos con precio menor, mayor o igual a ... euros");;
                        subOptions.add("3. Salir");
                        var subMenu = showMenu(sc, subOptions);

                        subOption = Integer.parseInt(subMenu);
                        isSubOption = productoService.getMetodo(subOption, sc);
                    }
                    break;

                case 3:
                    isSubOption = true;
                    while (isSubOption) {
                        System.out.println("3. *** EMPLEADOS ***");
                        System.out.println("Selecciona ...");

                        subOptions = new ArrayList<String>();
                        subOptions.add("1. Ver Todos los empleados");
                        subOptions.add("2. Insertar un empleado");                        ;
                        subOptions.add("3. Salir");

                        var subMenu = showMenu(sc, subOptions);

                        subOption = Integer.parseInt(subMenu);
                        isSubOption = empleadoService.getMetodo(subOption, sc);
                    }
                    break;

                case 4:
                    isSubOption = true;
                    while (isSubOption){
                        System.out.println("2. *** PRODUCTOS FAVORITOS ***");
                        System.out.println("Selecciona ...");

                        subOptions = new ArrayList<String>();
                        subOptions.add("1. Ver Todos los Productos Favoritos");
                        subOptions.add("2. registrar favoritos con un precio meno, mayor o igual a ...");;
                        subOptions.add("3. Salir");
                        var subMenu = showMenu(sc, subOptions);

                        subOption = Integer.parseInt(subMenu);
                        isSubOption = productoFavService.getMetodo(subOption, sc);
                    }
                    break;

                case 5:
                    isOpenGesAlmacen  = false;
                    break;

                default:
                    System.out.println("ERROR: La Opcion (".concat(menu).concat(") No es correcta. Debes seleccionar una Opcion del Menu del 1 al 5"));
                    System.out.println();
                    break;
            }
        }

        System.out.println("¡Hasta pronto!");
        sc.close();
    }

    private static String showMenu(Scanner sc, List<String> options){
       for (var item : options) {
           System.out.println(item);
       }
        return sc.next();
    }
}
