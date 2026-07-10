package proyectohecho;

/**
 * Representa un pedido de un cliente.
 * Se almacena en la Cola de pedidos pendientes del sistema.
 */
public class Pedido {
    private int idPedido;
    private String cliente;
    private int idProducto;
    private int cantidadSolicitada;

    public Pedido(int idPedido, String cliente, int idProducto, int cantidadSolicitada) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.idProducto = idProducto;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    @Override
    public String toString() {
        return "Pedido #" + idPedido + " | Cliente: " + cliente +
               " | Producto ID: " + idProducto + " | Cantidad: " + cantidadSolicitada;
    }
}
