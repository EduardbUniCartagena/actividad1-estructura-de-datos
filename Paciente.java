import java.io.Serializable;

/**
 * Representa un paciente que solicita un turno de atención.
 * Implementa Serializable para poder guardarse y leerse desde
 * archivos binarios (persistencia).
 */
public class Paciente implements Serializable {

    // Identifica la versión de la clase al serializar/deserializar.
    private static final long serialVersionUID = 1L;

    private String nombre;
    private TipoPaciente tipo;

    public Paciente(String nombre, TipoPaciente tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoPaciente getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
