package proyectohecho;

/**
 * Módulo de menú: Pedidos y Ventas.
 */
public class MenuPedidosVentas {

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- PEDIDOS Y VENTAS ---");
            System.out.println("1. Encolar pedido");
            System.out.println("2. Procesar siguiente pedido");
            System.out.println("3. Ver cola de pedidos");
            System.out.println("4. Ver historial de ventas");
            System.out.println("5. Ver productos vendidos");
            System.out.println("6. Volver al menú principal");
            opcion = Entrada.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> encolarPedido();
                case 2 -> procesarSiguientePedido();
                case 3 -> verColaPedidos();
                case 4 -> verHistorialVentas();
                case 5 -> verProductosVendidos();
                case 6 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 6) {
                Entrada.pausar();
            }
        } while (opcion != 6);
    }

    // Encolar pedido (Cola)
    static void encolarPedido() {
        if (Datos.inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        if (Datos.clientes.isEmpty()) {
            System.out.println("No hay clientes registrados. Registra un cliente primero.");
            return;
        }
        System.out.println("\n-- Encolar pedido --");
        MenuClientes.ver();
        int idCliente = Entrada.leerEntero("ID del cliente: ");
        Cliente cliente = Datos.buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        int idProducto = Entrada.leerEntero("ID del producto solicitado: ");
        int cantidad = Entrada.leerEntero("Cantidad solicitada: ");

        Pedido pedido = new Pedido(Datos.contadorIdPedido++, cliente, idProducto, cantidad);
        Datos.colaPedidos.add(pedido);
        System.out.println("Pedido añadido a la cola: " + pedido);
    }

    // Procesar siguiente pedido (Cola -> Pila -> Lista -> historial del Cliente)
    static void procesarSiguientePedido() {
        Pedido pedido = Datos.colaPedidos.poll();
        if (pedido == null) {
            System.out.println("No hay pedidos pendientes en la cola.");
            return;
        }

        Producto producto = Datos.buscarProductoPorId(pedido.getIdProducto());
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

        Venta venta = new Venta(Datos.contadorIdVenta++, pedido.getCliente(), producto.getNombre(),
                pedido.getCantidadSolicitada(), total);
        Datos.historialVentas.push(venta);
        Datos.productosVendidos.add(producto.getNombre() + " x" + pedido.getCantidadSolicitada());
        pedido.getCliente().registrarCompra(venta);

        System.out.println("Pedido procesado con éxito: " + venta);
    }

    // Ver cola de pedidos
    static void verColaPedidos() {
        System.out.println("\n-- Cola de pedidos pendientes --");
        if (Datos.colaPedidos.isEmpty()) {
            System.out.println("No hay pedidos en espera.");
            return;
        }
        for (Pedido p : Datos.colaPedidos) {
            System.out.println(p);
        }
    }

    // Ver historial de ventas (Pila)
    static void verHistorialVentas() {
        System.out.println("\n-- Historial de ventas (más reciente primero) --");
        if (Datos.historialVentas.isEmpty()) {
            System.out.println("Aún no se han registrado ventas.");
            return;
        }
        for (int i = Datos.historialVentas.size() - 1; i >= 0; i--) {
            System.out.println(Datos.historialVentas.get(i));
        }
    }

    // Ver productos vendidos (Lista)
    static void verProductosVendidos() {
        System.out.println("\n-- Productos vendidos --");
        if (Datos.productosVendidos.isEmpty()) {
            System.out.println("No se ha vendido ningún producto todavía.");
            return;
        }
        for (String item : Datos.productosVendidos) {
            System.out.println("- " + item);
        }
    }
}