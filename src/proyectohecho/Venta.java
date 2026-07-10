package proyectohecho;

/**
 * Representa una venta ya concretada.
 * Se almacena en la Pila (historial de ventas) del sistema.
 */
public class Venta {
    private int idVenta;
    private String producto;
    private int cantidad;
    private double total;

    public Venta(int idVenta, String producto, int cantidad, double total) {
        this.idVenta = idVenta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Venta #" + idVenta + " | " + producto +
               " x" + cantidad + " | Total: S/ " + total;
    }
}
