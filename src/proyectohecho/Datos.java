package proyectohecho;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * Centraliza todas las estructuras de datos y contadores compartidos
 * por los distintos módulos del sistema (Productos, Clientes, Pedidos, Reportes).
 *
 * Al tener un único lugar con el "estado" del programa, cada menú
 * (MenuProductos, MenuClientes, etc.) solo se encarga de su propia lógica,
 * sin duplicar ni pisarse las estructuras entre sí.
 */
public class Datos {

    // Vector -> inventario de productos
    public static Vector<Producto> inventario = new Vector<>();

    // Vector -> clientes registrados
    public static Vector<Cliente> clientes = new Vector<>();

    // Cola -> pedidos pendientes
    public static Queue<Pedido> colaPedidos = new LinkedList<>();

    // Pila -> historial de ventas
    public static Stack<Venta> historialVentas = new Stack<>();

    // Lista -> productos vendidos (registro simple)
    public static LinkedList<String> productosVendidos = new LinkedList<>();

    // Matriz -> horarios de empleados (filas: empleados, columnas: días)
    public static String[] empleados = {"Ana", "Luis", "Carla"};
    public static String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
    public static String[][] matrizHorarios = {
        {"08-14", "08-14", "Libre", "08-14", "08-14", "08-14"},
        {"14-20", "Libre", "14-20", "14-20", "14-20", "Libre"},
        {"09-17", "09-17", "09-17", "Libre", "09-17", "09-17"}
    };

    // Árbol -> categorías de productos (se puede seguir ampliando en tiempo
    // de ejecución desde el menú, no hace falta tocar este código de nuevo)
    public static CategoriaNodo raizCategorias = construirArbolCategorias();

    public static int contadorIdProducto = 1;
    public static int contadorIdCliente = 1;
    public static int contadorIdPedido = 1;
    public static int contadorIdVenta = 1;

    private static CategoriaNodo construirArbolCategorias() {
        CategoriaNodo raiz = new CategoriaNodo("Productos");

        CategoriaNodo electronica = new CategoriaNodo("Electrónica");
        electronica.agregarSubcategoria(new CategoriaNodo("Computadoras"));
        electronica.agregarSubcategoria(new CategoriaNodo("Celulares"));

        CategoriaNodo alimentos = new CategoriaNodo("Alimentos");
        alimentos.agregarSubcategoria(new CategoriaNodo("Lácteos"));
        alimentos.agregarSubcategoria(new CategoriaNodo("Bebidas"));

        CategoriaNodo hogar = new CategoriaNodo("Hogar");
        hogar.agregarSubcategoria(new CategoriaNodo("Limpieza"));
        hogar.agregarSubcategoria(new CategoriaNodo("Cocina"));

        raiz.agregarSubcategoria(electronica);
        raiz.agregarSubcategoria(alimentos);
        raiz.agregarSubcategoria(hogar);

        return raiz;
    }

    // ---------- Búsquedas compartidas ----------
    public static Producto buscarProductoPorId(int id) {
        for (Producto p : inventario) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}