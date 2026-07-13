package proyectohecho;

/**
 * Representa una venta ya concretada.
 * Se almacena en la Pila (historial de ventas) del sistema
 * y también en el historial de compras del Cliente correspondiente.
 */
public class Venta {
    private int idVenta;
    private Cliente cliente;
    private String producto;
    private int cantidad;
    private double total;

    public Venta(int idVenta, Cliente cliente, String producto, int cantidad, double total) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Cliente getCliente() {
        return cliente;
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
        return "Venta #" + idVenta + " | Cliente: " + cliente.getNombre() + " | " + producto + " x" + cantidad + " | Total: S/ " + total;
    }
}