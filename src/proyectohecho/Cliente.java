package proyectohecho;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un cliente de la tienda.
 * Se almacena en un Vector de clientes registrados del sistema
 * y guarda su propio historial de compras (Lista de Ventas).
 */
public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private List<Venta> historialCompras;

    public Cliente(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.historialCompras = new ArrayList<>();
    }

    // ---------- Getters ----------
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Venta> getHistorialCompras() {
        return historialCompras;
    }

    // ---------- Lógica propia de la clase ----------
    public void registrarCompra(Venta venta) {
        historialCompras.add(venta);
    }

    public double totalGastado() {
        double total = 0;
        for (Venta v : historialCompras) {
            total += v.getTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | Tel: " + telefono + " | Compras realizadas: " + historialCompras.size();
    }
}