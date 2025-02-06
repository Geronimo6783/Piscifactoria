package simulador.sql.dto;

/**
 * Clase que representa a un DTO de un pedido
 * que almacena el nombre del pez del que se realiza
 * el pedido, el nombre del usuario, el porcentaje de completado
 * del pedido y el número de referencia del pedido.
 */
public class DTOPedidoUsuarioPez {

    /**
     * Número de referencia del pedido.
     */
    private int numeroReferencia;

    /**
     * Nombre del cliente que realiza el pedido.
     */
    private String nombreCliente;

    /**
     * Nombre del pez del que se realiza el pedido.
     */
    private String nombrePez;

    /**
     * Porcentaje de completado del pedido.
     */
    private double porcentajeCompletado;

    /**
     * Constructor parametrizado.
     * @param numeroReferencia Número de referencia del pedido.
     * @param nombreCliente Nombre del cliente que realiza el pedido.
     * @param nombrePez Nombre del pez del que se realiza el pedido.
     * @param porcentajeCompletado Porcentaje de completado del pedido.
     */
    public DTOPedidoUsuarioPez(int numeroReferencia, String nombreCliente, String nombrePez,
            double porcentajeCompletado) {
        this.numeroReferencia = numeroReferencia;
        this.nombreCliente = nombreCliente;
        this.nombrePez = nombrePez;
        this.porcentajeCompletado = porcentajeCompletado;
    }

    /**
     * Permite obtener el número de referencia del pedido.
     * @return Número de referencia del pedido.
     */
    public int getNumeroReferencia() {
        return numeroReferencia;
    }

    /**
     * Permite establecer el número de referencia del pedido.
     * @param numeroReferencia Número de referencia del pedido a establecer.
     */
    public void setNumeroReferencia(int numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }

    /**
     * Permite obtener el nombre del cliente que realiza el pedido.
     * @return Nombre del cliente que realiza el pedido.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Permite establecer el nombre del cliente que realiza el pedido.
     * @param nombreCliente Nombre del cliente que realiza el pedido a establecer.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Permite obtener el nombre del pez del que se realiza el pedido.
     * @return Nombre del pez del que se realiza el pedido.
     */
    public String getNombrePez() {
        return nombrePez;
    }

    /**
     * Permite establecer el nombre del pez del que se realiza el pedido.
     * @param nombrePez Nombre del pez del que se realiza el pedido a establecer.
     */
    public void setNombrePez(String nombrePez) {
        this.nombrePez = nombrePez;
    }

    /**
     * Permite obtener el porcentaje de completado del pedido.
     * @return Porcentaje de completado del pedido.
     */
    public double getPorcentajeCompletado() {
        return porcentajeCompletado;
    }

    /**
     * Permite establecer el porcentaje de completado del pedido.
     * @param porcentajeCompletado Porcentaje de completado del pedido a establecer.
     */
    public void setPorcentajeCompletado(double porcentajeCompletado) {
        this.porcentajeCompletado = porcentajeCompletado;
    }
}
