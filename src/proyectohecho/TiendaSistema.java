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
 *   - Vector    -> clientes registrados
 *   - Matriz    -> horarios de empleados
 *   - Lista     -> productos vendidos / historial de compras por cliente
 *   - Árbol     -> categorías de productos
 *
 * Clases relacionadas: Producto, Cliente, Pedido, Venta, CategoriaNodo
 * ===========================================================
 */
public class TiendaSistema {

    // Vector -> inventario de productos
    static Vector<Producto> inventario = new Vector<>();

    // Vector -> clientes registrados
    static Vector<Cliente> clientes = new Vector<>();

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
    static int contadorIdCliente = 1;
    static int contadorIdPedido = 1;
    static int contadorIdVenta = 1;

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> registrarCliente();
                case 3 -> verClientes();
                case 4 -> encolarPedido();
                case 5 -> procesarSiguientePedido();
                case 6 -> verColaPedidos();
                case 7 -> verHistorialVentas();
                case 8 -> verHistorialComprasCliente();
                case 9 -> verProductosVendidos();
                case 10 -> verMatrizHorarios();
                case 11 -> verArbolCategorias();
                case 12 -> generarReporte();
                case 13 -> System.out.println("Saliendo del sistema... ¡Hasta pronto!");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 13) {
                pausar();
            }
        } while (opcion != 13);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE GESTIÓN DE TIENDA / INVENTARIO =====");
        System.out.println("1. Registrar producto");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Ver clientes registrados");
        System.out.println("4. Encolar pedido para procesamiento");
        System.out.println("5. Procesar siguiente pedido");
        System.out.println("6. Ver cola de pedidos");
        System.out.println("7. Ver historial de ventas");
        System.out.println("8. Ver historial de compras de un cliente");
        System.out.println("9. Ver productos vendidos");
        System.out.println("10. Ver matriz de horarios de empleados");
        System.out.println("11. Ver árbol de categorías de productos");
        System.out.println("12. Generar reporte de ventas");
        System.out.println("13. Salir");
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

    // 2. Registrar cliente (Vector)
    static void registrarCliente() {
        System.out.println("\n-- Registrar nuevo cliente --");
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        Cliente c = new Cliente(contadorIdCliente++, nombre, telefono);
        clientes.add(c);
        System.out.println("Cliente registrado correctamente: " + c);
    }

    // 3. Ver clientes registrados
    static void verClientes() {
        System.out.println("\n-- Clientes registrados --");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados todavía.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    // 4. Encolar pedido (Cola)
    static void encolarPedido() {
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados. Registra un cliente primero (opción 2).");
            return;
        }
        System.out.println("\n-- Encolar pedido --");
        verClientes();
        int idCliente = leerEntero("ID del cliente: ");
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        int idProducto = leerEntero("ID del producto solicitado: ");
        int cantidad = leerEntero("Cantidad solicitada: ");

        Pedido pedido = new Pedido(contadorIdPedido++, cliente, idProducto, cantidad);
        colaPedidos.add(pedido);
        System.out.println("Pedido añadido a la cola: " + pedido);
    }

    // 5. Procesar siguiente pedido (Cola -> Pila -> Lista -> historial del Cliente)
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

        Venta venta = new Venta(contadorIdVenta++, pedido.getCliente(), producto.getNombre(),
                pedido.getCantidadSolicitada(), total);
        historialVentas.push(venta);
        productosVendidos.add(producto.getNombre() + " x" + pedido.getCantidadSolicitada());
        pedido.getCliente().registrarCompra(venta);

        System.out.println("Pedido procesado con éxito: " + venta);
    }

    // 6. Ver cola de pedidos
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

    // 7. Ver historial de ventas (Pila)
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

    // 8. Ver historial de compras de un cliente (Lista propia del Cliente)
    static void verHistorialComprasCliente() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados todavía.");
            return;
        }
        verClientes();
        int id = leerEntero("ID del cliente: ");
        Cliente c = buscarClientePorId(id);
        if (c == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        System.out.println("\n-- Historial de compras de " + c.getNombre() + " --");
        if (c.getHistorialCompras().isEmpty()) {
            System.out.println("Este cliente aún no ha realizado compras.");
            return;
        }
        for (Venta v : c.getHistorialCompras()) {
            System.out.println(v);
        }
        System.out.println("Total gastado: S/ " + c.totalGastado());
    }

    // 9. Ver productos vendidos (Lista)
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

    // 10. Ver matriz de horarios (Matriz)
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

    // 11. Ver árbol de categorías (Árbol)
    static void verArbolCategorias() {
        System.out.println("\n-- Árbol de categorías de productos --");
        raizCategorias.imprimir("");
    }

    // 12. Generar reporte básico
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
        System.out.println("Clientes registrados: " + clientes.size());
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

    static Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
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

    // función para pausar en cada parte del menu
    static void pausar() {
        System.out.println("\n PRESIONE ENTER PARA VOLVER AL MENU");
        sc.nextLine();
    }
}