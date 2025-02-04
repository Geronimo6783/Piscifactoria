package simulador.sql.dto;

/**
 * Clase que representa a un DTO de la tabla
 * cliente.
 */
public class DTOCliente {

    /**
     * Id del cliente.
     */
    private int id;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Nif del cliente.
     */
    private String nif;

    /**
     * Teléfono del cliente.
     */
    private String telefono;

    /**
     * Constructor parametrizado.
     * @param id Id del cliente.
     * @param nombre Nombre del cliente.
     * @param nif Nif del cliente.
     * @param telefono Teléfono del cliente.
     */
    public DTOCliente(int id, String nombre, String nif, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
    }

    /**
     * Constructor de DTOCliente sin el id.
     * @param nombre Nombre del cliente.
     * @param nif Nif del cliente
     * @param telefono Telefono del cliente.
     */
    public DTOCliente(String nombre, String nif, String telefono) {
        this.id = 0;
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
    }

    /**
     * Permite obtener el id del cliente.
     * @return Id del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Permite establecer el id del cliente.
     * @param id Id del cliente a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permite obtener el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Permite establecer el nombre del cliente.
     * @param nombre Nombre del cliente a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Permite obtener el nif del cliente.
     * @return Nif del cliente.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Permite establecer el nif del cliente.
     * @param nif Nif del cliente a establecer.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Permite obtener el teléfono del cliente.
     * @return Teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Permite establecer el teléfono del cliente.
     * @param telefono Teléfono del cliente a establecer.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
