package proyectohecho;

/**
 * Representa un producto del inventario.
 * Se almacena en el Vector de inventario del sistema.
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;

    public Producto(int id, String nombre, double precio, int cantidad, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // ---------- Getters ----------
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    // ---------- Setters ----------
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // ---------- Lógica propia de la clase ----------
    public void reducirStock(int cantidadVendida) {
        this.cantidad -= cantidadVendida;
    }

    public boolean tieneStockSuficiente(int cantidadSolicitada) {
        return this.cantidad >= cantidadSolicitada;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | Precio: S/ " + precio +
               " | Stock: " + cantidad + " | Categoría: " + categoria;
    }
}
