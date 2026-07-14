package proyectohecho;

/**
 * Módulo de menú: Reportes y Consultas.
 */
public class MenuReportes {

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- REPORTES Y CONSULTAS ---");
            System.out.println("1. Ver matriz de horarios de empleados");
            System.out.println("2. Ver árbol de categorías de productos");
            System.out.println("3. Generar reporte de ventas");
            System.out.println("4. Volver al menú principal");
            opcion = Entrada.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> verMatrizHorarios();
                case 2 -> verArbolCategorias();
                case 3 -> generarReporte();
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 4) {
                Entrada.pausar();
            }
        } while (opcion != 4);
    }

    // Ver matriz de horarios (Matriz)
    static void verMatrizHorarios() {
        System.out.println("\n-- Horarios de empleados --");
        System.out.printf("%-10s", "");
        for (String dia : Datos.dias) {
            System.out.printf("%-8s", dia);
        }
        System.out.println();

        for (int i = 0; i < Datos.empleados.length; i++) {
            System.out.printf("%-10s", Datos.empleados[i]);
            for (int j = 0; j < Datos.dias.length; j++) {
                System.out.printf("%-8s", Datos.matrizHorarios[i][j]);
            }
            System.out.println();
        }
    }

    // Ver árbol de categorías (Árbol)
    static void verArbolCategorias() {
        System.out.println("\n-- Árbol de categorías de productos --");
        Datos.raizCategorias.imprimir("");
    }

    // Generar reporte básico
    static void generarReporte() {
        System.out.println("\n===== REPORTE DE VENTAS =====");
        double totalVentas = 0;
        int cantidadVentas = Datos.historialVentas.size();
        for (Venta v : Datos.historialVentas) {
            totalVentas += v.getTotal();
        }
        System.out.println("Total de ventas realizadas: " + cantidadVentas);
        System.out.println("Ingreso total: S/ " + totalVentas);
        System.out.println("Pedidos pendientes en cola: " + Datos.colaPedidos.size());
        System.out.println("Productos registrados en inventario: " + Datos.inventario.size());
        System.out.println("Clientes registrados: " + Datos.clientes.size());
        System.out.println("\n-- Inventario actual --");
        for (Producto p : Datos.inventario) {
            System.out.println(p);
        }
    }
}