package proyectohecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Nodo del Árbol de categorías de productos.
 * Cada nodo puede tener múltiples subcategorías (árbol n-ario).
 */
public class CategoriaNodo {
    private String nombre;
    private List<CategoriaNodo> subcategorias;

    public CategoriaNodo(String nombre) {
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<CategoriaNodo> getSubcategorias() {
        return subcategorias;
    }

    public void agregarSubcategoria(CategoriaNodo hijo) {
        subcategorias.add(hijo);
    }

    public void imprimir(String indentacion) {
        System.out.println(indentacion + "- " + nombre);
        for (CategoriaNodo hijo : subcategorias) {
            hijo.imprimir(indentacion + "   ");
        }
    }
    public void listarCategorias(List<String> lista){
        lista.add(this.nombre);
        for (CategoriaNodo hijo : subcategorias) {
            hijo.listarCategorias(lista);
        }
    }

    public String elegirCategoria(Scanner sc){
        System.out.println("ELIGE LA CATEGORIA: ");
        for (int i = 0; i < subcategorias.size(); i++) {
            System.out.println((i+1) + ". "+subcategorias.get(i).getNombre());
        }
        int seleccion = leerOpcion(sc, subcategorias.size());
        CategoriaNodo elegido = subcategorias.get(seleccion-1);
        return elegido.bajarNivel(sc);
    }

    private String bajarNivel(Scanner sc) {
        if (subcategorias.isEmpty()) {
            return this.nombre;
        }    
    
    System.out.println("\"" + nombre + "\" tiene subcategorías. Elige una:");
    System.out.println("0. " + nombre + " (sin subcategoría específica)");
    for (int i = 0; i < subcategorias.size(); i++) {
        System.out.println((i + 1) + ". " + subcategorias.get(i).getNombre());
    }

    int seleccion = leerOpcion(sc, subcategorias.size());
    if (seleccion == 0) {
        return this.nombre;
    }

    return subcategorias.get(seleccion - 1).bajarNivel(sc);
    
    }
    private int leerOpcion(Scanner sc, int max) {
    int seleccion = -1;
    while (seleccion < 0 || seleccion > max) {
        System.out.print("Ingresa el número: ");
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        seleccion = sc.nextInt();
        sc.nextLine();
    }
    return seleccion;
    }
}
