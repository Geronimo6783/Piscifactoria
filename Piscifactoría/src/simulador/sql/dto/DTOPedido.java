package simulador.sql.dto;

/**
 * Clase que representa a un DTO de la
 * tabla pedido.
 */
public class DTOPedido {
    
    /**
     * Id del cliente que realizó el pedido.
     */
    private int idCliente;

    /**
     * Id del pez del que se ha realizado el pedido.
     */
    private int idPez;

    /**
     * Número de peces solicitados en el pedido.
     */
    private int pecesSolicitados;

    /**
     * Número de peces enviados.
     */
    private int pecesEnviados;

    /**
     * Constructor parametrizado.
     * @param idCliente Id del cliente que realizó el pedido.
     * @param idPez Id del pez del que se ha realizado el pedido.
     * @param pecesSolicitados Número de peces solicitados en el pedido.
     * @param pecesEnviados Número de peces enviados.
     */
    public DTOPedido(int idCliente, int idPez, int pecesSolicitados, int pecesEnviados) {
        this.idCliente = idCliente;
        this.idPez = idPez;
        this.pecesSolicitados = pecesSolicitados;
        this.pecesEnviados = pecesEnviados;
    }

    /**
     * Permite obtener el id del cliente que realizó el pedido.
     * @return Id del cliente que realizó el pedido.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Permite establecer el id del cliente que realizó el pedido.
     * @param idCliente Id del cliente que realizó el pedido a establecer.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Permite obtener el id del pez del que se ha realizado el pedido.
     * @return Id del pez del que se ha realizado el pedido.
     */
    public int getIdPez() {
        return idPez;
    }

    /**
     * Permite establecer el id del pez del que se ha realizado el pedido.
     * @param idPez Id del pez del que se ha realizado el pedido a establecer.
     */
    public void setIdPez(int idPez) {
        this.idPez = idPez;
    }

    /**
     * Permite obtener el número de peces solicitados en el pedido.
     * @return Número de peces solicitados en el pedido.
     */
    public int getPecesSolicitados() {
        return pecesSolicitados;
    }

    /**
     * Permite establecer el número de peces solicitados en el pedido.
     * @param pecesSolicitados Número de peces solicitados en el pedido a establecer.
     */
    public void setPecesSolicitados(int pecesSolicitados) {
        this.pecesSolicitados = pecesSolicitados;
    }

    /**
     * Permite obtener el número de peces enviados.
     * @return Número de peces enviados.
     */
    public int getPecesEnviados() {
        return pecesEnviados;
    }

    /**
     * Permite establecer el número de peces enviados.
     * @param pecesEnviados Número de peces enviados a establecer.
     */
    public void setPecesEnviados(int pecesEnviados) {
        this.pecesEnviados = pecesEnviados;
    }
}
