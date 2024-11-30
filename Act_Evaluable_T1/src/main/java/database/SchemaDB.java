package database;

public interface SchemaDB {
    //Variables deconexion BBDD
    String HOST = "127.0.0.1";
    String PORT = "3306";
    String DATABASE = "almacen";

    //Tabla Empleado
    String TAB_EMPLEADOS = "empleados";
    String EMPLEADOS_COL_ID = "id";
    String EMPLEADOS_COL_NOMBRE = "nombre";
    String EMPLEADOS_COL_APELLIDOS = "apellidos";
    String EMPLEADOS_COL_CORREO = "correo";

    //Tabla Producto
    String TAB_PRODUCTOS = "productos";
    String PRODUCTOS_COL_ID = "id";
    String PRODUCTOS_COL_NOMBRE = "nombre";
    String PRODUCTOS_COL_DESCRIPCION = "descripcion";
    String PRODUCTOS_COL_CANTIDAD = "cantidad";
    String PRODUCTOS_COL_PRECIO = "precio";

    //Tabla Pedido
    String TAB_PEDIDOS = "pedidos";
    String PEDIDOS_COL_ID = "id";
    String PEDIDOS_COL_DESCRIPCION = "descripcion";
    String PEDIDOS_COL_PRECIOTOTAL = "precio_total";
    String PEDIDOS_COL_IDPRODUCTO = "id_producto";

    //Tabla ProductoFAV
    String TAB_PRODUCTOSFAV = "productos_fav";
    String PRODUCTOSFAV_COL_ID = "id";
    String PRODUCTOSFAV_COL_IDPRODUCTO = "id_producto";

    //URL APIs
    String URL_API_PRODUCTOS = "https://dummyjson.com/products";
}
