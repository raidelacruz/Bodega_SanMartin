package proyectohecho;

/**
 * Módulo de menú: Productos.
 * Registrar, editar, eliminar y buscar productos, además de administrar
 * el árbol de categorías (agregar nuevas categorías sin tocar el código).
 */
public class MenuProductos {

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- CONFIGURACIÓN DE PRODUCTOS ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Editar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar producto por nombre");
            System.out.println("5. Agregar categoría");
            System.out.println("6. Ver inventario completo");
            System.out.println("7. Volver al menú principal");
            opcion = Entrada.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> editar();
                case 3 -> eliminar();
                case 4 -> buscarPorNombre();
                case 5 -> agregarCategoria();
                case 6 -> verInventario();
                case 7 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if (opcion != 7) {
                Entrada.pausar();
            }
        } while (opcion != 7);
    }

    // Registrar producto (Vector)
    static void registrar() {
        System.out.println("\n-- Registrar nuevo producto --");
        System.out.print("Nombre del producto: ");
        String nombre = Entrada.sc.nextLine();
        double precio = Entrada.leerDecimal("Precio: ");
        int cantidad = Entrada.leerEntero("Cantidad en stock: ");
        System.out.print("Categoría: ");
        String categoria = Datos.raizCategorias.elegirCategoria(Entrada.sc);

        Producto p = new Producto(Datos.contadorIdProducto++, nombre, precio, cantidad, categoria);
        Datos.inventario.add(p);
        System.out.println("Producto registrado correctamente: " + p);
    }

    // Editar producto
    static void editar() {
        if (Datos.inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        verInventario();

        int id = Entrada.leerEntero("ID del producto a editar: ");
        Producto p = Datos.buscarProductoPorId(id);
        if (p == null) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        System.out.println("Editando: " + p);
        System.out.println("Deje el campo vacío y presione Enter para no modificarlo.");

        System.out.print("Nuevo nombre [" + p.getNombre() + "]: ");
        String nombre = Entrada.sc.nextLine();
        if (!nombre.isBlank()) {
            p.setNombre(nombre);
        }

        System.out.print("Nuevo precio [" + p.getPrecio() + "]: ");
        String precioTxt = Entrada.sc.nextLine();
        if (!precioTxt.isBlank()) {
            try {
                double precio = Double.parseDouble(precioTxt);
                if (precio < 0) {
                    System.out.println("El precio no puede ser negativo, se mantiene el valor anterior.");
                } else {
                    p.setPrecio(precio);
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor no válido, se mantiene el precio anterior.");
            }
        }

        System.out.print("Nueva cantidad en stock [" + p.getCantidad() + "]: ");
        String cantidadTxt = Entrada.sc.nextLine();
        if (!cantidadTxt.isBlank()) {
            try {
                int cantidad = Integer.parseInt(cantidadTxt);
                if (cantidad < 0) {
                    System.out.println("El stock no puede ser negativo, se mantiene el valor anterior.");
                } else {
                    p.setCantidad(cantidad);
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor no válido, se mantiene el stock anterior.");
            }
        }

        System.out.println("Producto actualizado: " + p);
    }

    // Eliminar producto
    static void eliminar() {
        if (Datos.inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        verInventario();

        int id = Entrada.leerEntero("ID del producto a eliminar: ");
        Producto p = Datos.buscarProductoPorId(id);
        if (p == null) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        System.out.print("¿Seguro que desea eliminar \"" + p.getNombre() + "\"? (S/N): ");
        String confirmacion = Entrada.sc.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            Datos.inventario.remove(p);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // Buscar producto por nombre (coincidencia parcial, sin distinguir mayúsculas/minúsculas)
    static void buscarPorNombre() {
        if (Datos.inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        System.out.print("Nombre (o parte del nombre) a buscar: ");
        String texto = Entrada.sc.nextLine().trim().toLowerCase();

        if (texto.isEmpty()) {
            System.out.println("Debe ingresar algún texto para buscar.");
            return;
        }

        System.out.println("\n-- Resultados de la búsqueda --");
        boolean encontrado = false;
        for (Producto p : Datos.inventario) {
            if (p.getNombre().toLowerCase().contains(texto)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún producto que coincida con \"" + texto + "\".");
        }
    }

    // Agregar categoría/subcategoría en tiempo de ejecución (Árbol)
    static void agregarCategoria() {
        System.out.println("\n-- Agregar nueva categoría --");
        System.out.println("Árbol actual:");
        Datos.raizCategorias.imprimir("");

        CategoriaNodo padre = Datos.raizCategorias.elegirNodoPadre(Entrada.sc);

        System.out.print("Nombre de la nueva categoría: ");
        String nombre = Entrada.sc.nextLine().trim();
        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío. Operación cancelada.");
            return;
        }

        padre.agregarSubcategoria(new CategoriaNodo(nombre));
        System.out.println("Categoría \"" + nombre + "\" agregada correctamente dentro de \"" + padre.getNombre() + "\".");
    }

    // Ver inventario completo (Vector)
    static void verInventario() {
        System.out.println("\n-- Inventario actual --");
        if (Datos.inventario.isEmpty()) {
            System.out.println("No hay productos registrados aún.");
            return;
        }
        for (Producto p : Datos.inventario) {
            System.out.println(p);
        }
    }
}