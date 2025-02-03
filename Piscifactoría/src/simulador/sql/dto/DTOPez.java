package simulador.sql.dto;

/**
 * Clase que representa a un DTO de la tabla Pez.
 */
public class DTOPez {

    /**
     * Id del pez.
     */
    private int id;

    /**
     * Nombre del pez.
     */
    private String nombre;

    /**
     * Nombre científico del pez.
     */
    private String nombreCientifico;

    /**
     * Constructor parametrizado.
     * @param id Id del pez.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     */
    public DTOPez(int id, String nombre, String nombreCientifico) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
    }

    /**
     * Constructor de peces sin id.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     */
    public DTOPez(String nombre, String nombreCientifico) {
        this.id = 0;
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
    }

    /**
     * Permite obtener el id del pez.
     * @return Id del pez.
     */
    public int getId() {
        return id;
    }

    /**
     * Permite establecer el id del pez.
     * @param id Id del pez a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permite obtener el nombre del pez.
     * @return Nombre del pez.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Permite establecer el nombre del pez.
     * @param nombre Nombre del pez a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Permite obtener el nombre científico del pez.
     * @return Nombre científico del pez.
     */
    public String getNombreCientifico() {
        return nombreCientifico;
    }

    /**
     * Permite establecer el nombre científico del pez.
     * @param nombreCientifico Nombre científico del pez a establecer.
     */
    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }
}
