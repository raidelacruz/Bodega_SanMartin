package proyectohecho;

/*
 * ===========================================================
 * PROYECTO: Sistema de Gestión de Tienda / Inventario
 *
 * Punto de entrada del programa. La lógica de cada módulo vive
 * en su propia clase para no mezclar todo en un único archivo:
 *
 *   - Datos               -> estructuras de datos y contadores compartidos
 *   - Entrada              -> lectura por teclado (Scanner, validaciones)
 *   - MenuProductos         -> registrar/editar/eliminar/buscar productos y categorías
 *   - MenuClientes           -> registrar/ver clientes e historial de compras
 *   - MenuPedidosVentas       -> cola de pedidos, procesamiento y ventas
 *   - MenuReportes             -> matriz de horarios, árbol de categorías, reportes
 *
 * Estructuras de datos aplicadas (en Datos.java):
 *   Cola, Pila, Vector (x2), Matriz, Lista, Árbol
 * ===========================================================
 */
public class TiendaSistema {

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = Entrada.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> MenuProductos.mostrar();
                case 2 -> MenuClientes.mostrar();
                case 3 -> MenuPedidosVentas.mostrar();
                case 4 -> MenuReportes.mostrar();
                case 5 -> System.out.println("Saliendo del sistema... ¡Hasta pronto!");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 5);

        Entrada.sc.close();
    }

    static void mostrarMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE GESTIÓN DE TIENDA / INVENTARIO =====");
        System.out.println("1. Productos");
        System.out.println("2. Clientes");
        System.out.println("3. Pedidos y ventas");
        System.out.println("4. Reportes y consultas");
        System.out.println("5. Salir");
    }
}