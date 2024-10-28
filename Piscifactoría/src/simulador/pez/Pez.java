package simulador.pez;

/**
 * Clase abstracta que representa a un pez.
 */
public abstract class Pez {

    /**
     * Nombre del pez.
     */
    protected final String nombre;

    /**
     * Nombre científico del pez.
     */
    protected final String nombreCientifico;

    /**
     * Edad del pez en días.
     */
    protected int edad;

    /**
     * Sexo del pez si el valor es true es hembra si es false es macho.
     */
    protected final boolean sexo;

    /**
     * Indica si el pez es fértil y puede reproducirse.
     */
    protected boolean fertil;

    /**
     * Indica si el pez está vivo.
     */
    protected boolean vivo;

    /**
     * Indica si el pez ha sido alimentado.
     */
    protected boolean alimentado;

    /**
     * Número de días en los que el pez no se ha reproducido desde la última vez que era fértil.
     */
    protected int diasSinReproducirse;

    /**
     * 
     * @return Nombre del pez.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @return Nombre científico del pez.
     */
    public String getNombreCientifico() {
        return nombreCientifico;
    }

    /**
     * 
     * @return Edad del pez.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * 
     * @return Sexo del pez si es true es hembra y se es false es macho.
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * 
     * @return True si el pez es fértil.
     */
    public boolean isFertil() {
        return fertil;
    }

    /**
     * 
     * @return True si el pez está vivo.
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * 
     * @return True si el pez ha sido alimentado.
     */
    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * 
     * @return Número de días en los que el pez no se ha reproducido desde la última vez que era fértil.
     */
    public int getDiasSinReproducirse() {
        return diasSinReproducirse;
    }

    /**
     * Permite establecer la edad del pez.
     * @param edad Edad a establecer.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Permite establecer si el pez es fértil.
     * @param fertil True para establecer que el pez es fértil.
     */
    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }

    /**
     * Permite establecer si el pez está vivo.
     * @param vivo True para establecer que el pez está vivo.
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Permite establecer si el pez ha sido alimentado.
     * @param alimentado True para establecer que el pez ha sido alimentado.
     */
    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    /**
     * Permite establecer el número de días sin reproducirse desde la última vez que el pez era fértil.
     * @param diasSinReproducirse Número de días sin reproducirse desde la última vez que el pez era fértil.
     */
    public void setDiasSinReproducirse(int diasSinReproducirse) {
        this.diasSinReproducirse = diasSinReproducirse;
    }

    /**
     * Constructor de peces a partir del nombre, nombre científico y sexo del pez.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     * @param sexo Sexo del pez.
     */
    protected Pez(String nombre, String nombreCientifico, boolean sexo) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.sexo = sexo;
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
    }

    /**
     * Muestra el estado del pez.
     */
    public abstract void showStatus();

    /**
     * Hace que el pez crezca un día.
     */
    public abstract void grow();

    /**
     * Reinicia el pez estableciendo sus atributos a sus estados iniciales.
     */
    public void reset(){
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.diasSinReproducirse = 0;
    }

    /**
     * Indica la cantidad de comida que comerá el pez un día.
     * @return Cantidad de comida que come el pez.
     */
    public int comer(){
        return 1;
    }

    /**
     * 
     * @return True si el pez está maduro.
     */
    public abstract boolean isMaduro();

    /**
     * 
     * @return True si el pez está en la edad óptima para ser vendido.
     */
    public abstract boolean isEdadOptima();

    /**
     * 
     * @return Pez nuevo de sexo masculino.
     */
    public abstract Pez obtenerPezHijo();

    /**
     * 
     * @return Pez nuevo de sexo femenino.
     */
    public abstract Pez obtenerPezHija();
}
