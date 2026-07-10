package proyectohecho;

import java.util.Scanner;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 * ===========================================================
 * PROYECTO: Sistema de Gestión de Tienda / Inventario
 * Estructuras de datos aplicadas:
 *   - Cola      -> pedidos pendientes de procesar
 *   - Pila      -> historial de ventas realizadas
 *   - Vector    -> inventario de productos
 *   - Matriz    -> horarios de empleados
 *   - Lista     -> productos vendidos
 *   - Árbol     -> categorías de productos
 *
 * Clases relacionadas: Producto, Pedido, Venta, CategoriaNodo
 * ===========================================================
 */
public class TiendaSistema {

    // Vector -> inventario de productos
    static Vector<Producto> inventario = new Vector<>();

    // Cola -> pedidos pendientes
    static Queue<Pedido> colaPedidos = new LinkedList<>();

    // Pila -> historial de ventas
    static Stack<Venta> historialVentas = new Stack<>();

    // Lista -> productos vendidos (registro simple)
    static LinkedList<String> productosVendidos = new LinkedList<>();

    // Matriz -> horarios de empleados (filas: empleados, columnas: días)
    static String[] empleados = {"Ana", "Luis", "Carla"};
    static String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
    static String[][] matrizHorarios = {
        {"08-14", "08-14", "Libre", "08-14", "08-14", "08-14"},
        {"14-20", "Libre", "14-20", "14-20", "14-20", "Libre"},
        {"09-17", "09-17", "09-17", "Libre", "09-17", "09-17"}
    };

    // Árbol -> categorías de productos
    static CategoriaNodo raizCategorias = construirArbolCategorias();

    static Scanner sc = new Scanner(System.in);
    static int contadorIdProducto = 1;
    static int contadorIdPedido = 1;
    static int contadorIdVenta = 1;

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> encolarPedido();
                case 3 -> procesarSiguientePedido();
                case 4 -> verColaPedidos();
                case 5 -> verHistorialVentas();
                case 6 -> verProductosVendidos();
                case 7 -> verMatrizHorarios();
                case 8 -> verArbolCategorias();
                case 9 -> generarReporte();
                case 10 -> System.out.println("Saliendo del sistema... ¡Hasta pronto!");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 10) {
            pausar();
            }
        } while (opcion != 10);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE GESTIÓN DE TIENDA / INVENTARIO =====");
        System.out.println("1. Registrar producto");
        System.out.println("2. Encolar pedido para procesamiento");
        System.out.println("3. Procesar siguiente pedido");
        System.out.println("4. Ver cola de pedidos");
        System.out.println("5. Ver historial de ventas");
        System.out.println("6. Ver productos vendidos");
        System.out.println("7. Ver matriz de horarios de empleados");
        System.out.println("8. Ver árbol de categorías de productos");
        System.out.println("9. Generar reporte de ventas");
        System.out.println("10. Salir");
    }

    // 1. Registrar producto (Vector)
    static void registrarProducto() {
        System.out.println("\n-- Registrar nuevo producto --");
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        double precio = leerDecimal("Precio: ");
        int cantidad = leerEntero("Cantidad en stock: ");
        System.out.print("Categoría: ");
        String categoria = raizCategorias.elegirCategoria(sc);

        Producto p = new Producto(contadorIdProducto++, nombre, precio, cantidad, categoria);
        inventario.add(p);
        System.out.println("Producto registrado correctamente: " + p);
    }

    // 2. Encolar pedido (Cola)
    static void encolarPedido() {
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        System.out.println("\n-- Encolar pedido --");
        System.out.print("Nombre del cliente: ");
        String cliente = sc.nextLine();
        int idProducto = leerEntero("ID del producto solicitado: ");
        int cantidad = leerEntero("Cantidad solicitada: ");

        Pedido pedido = new Pedido(contadorIdPedido++, cliente, idProducto, cantidad);
        colaPedidos.add(pedido);
        System.out.println("Pedido añadido a la cola: " + pedido);
    }

    // 3. Procesar siguiente pedido (Cola -> Pila -> Lista)
    static void procesarSiguientePedido() {
        Pedido pedido = colaPedidos.poll();
        if (pedido == null) {
            System.out.println("No hay pedidos pendientes en la cola.");
            return;
        }

        Producto producto = buscarProductoPorId(pedido.getIdProducto());
        if (producto == null) {
            System.out.println("El producto del pedido no existe en el inventario.");
            return;
        }
        if (!producto.tieneStockSuficiente(pedido.getCantidadSolicitada())) {
            System.out.println("Stock insuficiente para completar el pedido de " + producto.getNombre());
            return;
        }

        producto.reducirStock(pedido.getCantidadSolicitada());
        double total = producto.getPrecio() * pedido.getCantidadSolicitada();

        Venta venta = new Venta(contadorIdVenta++, producto.getNombre(), pedido.getCantidadSolicitada(), total);
        historialVentas.push(venta);
        productosVendidos.add(producto.getNombre() + " x" + pedido.getCantidadSolicitada());

        System.out.println("Pedido procesado con éxito: " + venta);
    }

    // 4. Ver cola de pedidos
    static void verColaPedidos() {
        System.out.println("\n-- Cola de pedidos pendientes --");
        if (colaPedidos.isEmpty()) {
            System.out.println("No hay pedidos en espera.");
            return;
        }
        for (Pedido p : colaPedidos) {
            System.out.println(p);
        }
    }

    // 5. Ver historial de ventas (Pila)
    static void verHistorialVentas() {
        System.out.println("\n-- Historial de ventas (más reciente primero) --");
        if (historialVentas.isEmpty()) {
            System.out.println("Aún no se han registrado ventas.");
            return;
        }
        for (int i = historialVentas.size() - 1; i >= 0; i--) {
            System.out.println(historialVentas.get(i));
        }
    }

    // 6. Ver productos vendidos (Lista)
    static void verProductosVendidos() {
        System.out.println("\n-- Productos vendidos --");
        if (productosVendidos.isEmpty()) {
            System.out.println("No se ha vendido ningún producto todavía.");
            return;
        }
        for (String item : productosVendidos) {
            System.out.println("- " + item);
        }
    }

    // 7. Ver matriz de horarios (Matriz)
    static void verMatrizHorarios() {
        System.out.println("\n-- Horarios de empleados --");
        System.out.printf("%-10s", "");
        for (String dia : dias) {
            System.out.printf("%-8s", dia);
        }
        System.out.println();

        for (int i = 0; i < empleados.length; i++) {
            System.out.printf("%-10s", empleados[i]);
            for (int j = 0; j < dias.length; j++) {
                System.out.printf("%-8s", matrizHorarios[i][j]);
            }
            System.out.println();
        }
    }

    // 8. Ver árbol de categorías (Árbol)
    static void verArbolCategorias() {
        System.out.println("\n-- Árbol de categorías de productos --");
        raizCategorias.imprimir("");
    }

    // 9. Generar reporte básico
    static void generarReporte() {
        System.out.println("\n===== REPORTE DE VENTAS =====");
        double totalVentas = 0;
        int cantidadVentas = historialVentas.size();
        for (Venta v : historialVentas) {
            totalVentas += v.getTotal();
        }
        System.out.println("Total de ventas realizadas: " + cantidadVentas);
        System.out.println("Ingreso total: S/ " + totalVentas);
        System.out.println("Pedidos pendientes en cola: " + colaPedidos.size());
        System.out.println("Productos registrados en inventario: " + inventario.size());
        System.out.println("\n-- Inventario actual --");
        for (Producto p : inventario) {
            System.out.println(p);
        }
    }

    static Producto buscarProductoPorId(int id) {
        for (Producto p : inventario) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    static CategoriaNodo construirArbolCategorias() {
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

    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    //funcion para pausar en cada parte del menu
    static void pausar(){
        System.out.println("\n PRESIONE ENTER PARA VOLVER AL MENU");
        sc.nextLine();
    }
}
