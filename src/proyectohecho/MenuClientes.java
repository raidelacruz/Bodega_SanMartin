package proyectohecho;

/**
 * Módulo de menú: Clientes.
 */
public class MenuClientes {

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- CONFIGURACIÓN DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver clientes registrados");
            System.out.println("3. Ver historial de compras de un cliente");
            System.out.println("4. Volver al menú principal");
            opcion = Entrada.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> ver();
                case 3 -> verHistorialCompras();
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 4) {
                Entrada.pausar();
            }
        } while (opcion != 4);
    }

    // Registrar cliente (Vector)
    static void registrar() {
        System.out.println("\n-- Registrar nuevo cliente --");
        System.out.print("Nombre del cliente: ");
        String nombre = Entrada.sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = Entrada.sc.nextLine();

        Cliente c = new Cliente(Datos.contadorIdCliente++, nombre, telefono);
        Datos.clientes.add(c);
        System.out.println("Cliente registrado correctamente: " + c);
    }

    // Ver clientes registrados
    static void ver() {
        System.out.println("\n-- Clientes registrados --");
        if (Datos.clientes.isEmpty()) {
            System.out.println("No hay clientes registrados todavía.");
            return;
        }
        for (Cliente c : Datos.clientes) {
            System.out.println(c);
        }
    }

    // Ver historial de compras de un cliente (Lista propia del Cliente)
    static void verHistorialCompras() {
        if (Datos.clientes.isEmpty()) {
            System.out.println("No hay clientes registrados todavía.");
            return;
        }
        ver();
        int id = Entrada.leerEntero("ID del cliente: ");
        Cliente c = Datos.buscarClientePorId(id);
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
}