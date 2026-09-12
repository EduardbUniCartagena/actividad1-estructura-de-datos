import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Sistema de digiturno para un consultorio médico.
 *
 * Esta clase contiene ÚNICAMENTE la lógica del digiturno (las estructuras
 * de datos y las operaciones sobre ellas).
 *
 * Estructuras (TAD) que se usan en esta clase:
 *  - Cola  -> orden de llegada dentro de cada tipo de paciente (FIFO).
 *  - Lista -> histórico de atención del día.
 *  - Pila  -> deshacer el último turno generado (LIFO).
 *
 * Persistencia (archivos binarios):
 *  - Lectura de datos de prueba desde "datos/datos_prueba.dat".
 *  - Escritura del histórico de atención en "datos/historico.dat".
 */
public class Digiturno {

    private Cola<Paciente> colaGeneral = new Cola<>();
    private Cola<Paciente> colaPremium = new Cola<>();
    private Cola<Paciente> colaPrioritaria = new Cola<>();

    private Lista<Paciente> historico = new Lista<>();
    private Pila<Paciente> pilaDeshacer = new Pila<>();

    private static final String ARCHIVO_DATOS_PRUEBA = "datos/datos_prueba.dat";
    private static final String ARCHIVO_HISTORICO = "datos/historico.dat";

    /**
     * Genera un nuevo turno para un paciente y lo registra en el sistema.
     *
     * Justificación del TAD:
     * - TAD Cola (FIFO): Se inserta el paciente en la cola correspondiente a su tipo
     *   (PRIORITARIO, PREMIUM o GENERAL) mediante el método encolar(), garantizando que
     *   los pacientes del mismo tipo mantengan su orden de llegada estricto.
     * - TAD Pila (LIFO): Se apila simultáneamente el paciente recién creado en la
     *   pilaDeshacer mediante el método apilar(), lo que permite rastrear la última
     *   acción realizada para poder revertirla si ocurrió un error en la digitación.
     *
     * @param nombre Nombre completo del paciente.
     * @param tipo Tipo de paciente (GENERAL, PREMIUM, PRIORITARIO).
     */
    public void generarTurno(String nombre, TipoPaciente tipo) {
        Paciente paciente = new Paciente(nombre, tipo);

        switch (tipo) {
            case PRIORITARIO:
                colaPrioritaria.encolar(paciente);
                break;
            case PREMIUM:
                colaPremium.encolar(paciente);
                break;
            case GENERAL:
                colaGeneral.encolar(paciente);
                break;
        }

        pilaDeshacer.apilar(paciente);
    }

    /**
     * Atiende al siguiente paciente respetando la regla de prioridad entre colas.
     *
     * Justificación del TAD:
     * - TAD Cola (FIFO): Se revisan las tres colas en orden estricto de prioridad
     *   (PRIORITARIO -> PREMIUM -> GENERAL) utilizando estaVacia(). Se remueve al
     *   primer paciente de la cola seleccionada mediante desencolar(), respetando el
     *   orden de llegada FIFO de esa categoría.
     * - TAD Lista: El paciente desencolado se registra al final de la lista de histórico
     *   mediante el método agregar(), conservando el registro cronológico exacto
     *   de las atenciones realizadas en el día.
     *
     * @return El Paciente atendido, o null si no hay pacientes en ninguna cola.
     */
    public Paciente atenderSiguiente() {
        Cola<Paciente> colaAAtender = null;

        if (!colaPrioritaria.estaVacia()) {
            colaAAtender = colaPrioritaria;
        } else if (!colaPremium.estaVacia()) {
            colaAAtender = colaPremium;
        } else if (!colaGeneral.estaVacia()) {
            colaAAtender = colaGeneral;
        }

        if (colaAAtender == null) {
            return null; // No hay pacientes en ninguna cola
        }

        Paciente pacienteAtendido = colaAAtender.desencolar();
        historico.agregar(pacienteAtendido);

        return pacienteAtendido;
    }

    /**
     * Deshace la generación del último turno registrado antes de que sea atendido.
     *
     * Justificación del TAD:
     * - TAD Pila (LIFO): Se utiliza desapilar() sobre pilaDeshacer para obtener de forma
     *   inmediata (O(1)) el último paciente registrado en el sistema, respetando el
     *   comportamiento LIFO ideal para operaciones de deshacer (undo).
     * - TAD Cola: Una vez identificado el paciente a retirar, se invoca eliminar(paciente)
     *   en la cola correspondiente a su tipo. Este método remueve el nodo de la cola
     *   sin alterar el orden relativo de los demás pacientes en espera.
     *
     * @return El Paciente cuyo turno fue deshecho, o null si la pila está vacía.
     */
    public Paciente deshacerUltimoTurno() {
        if (pilaDeshacer.estaVacia()) {
            return null;
        }

        Paciente ultimoPaciente = pilaDeshacer.desapilar();

        switch (ultimoPaciente.getTipo()) {
            case PRIORITARIO:
                colaPrioritaria.eliminar(ultimoPaciente);
                break;
            case PREMIUM:
                colaPremium.eliminar(ultimoPaciente);
                break;
            case GENERAL:
                colaGeneral.eliminar(ultimoPaciente);
                break;
        }

        return ultimoPaciente;
    }

    /**
     * Carga los pacientes de prueba desde un archivo binario serializado al iniciar la aplicación.
     *
     * Justificación del TAD y Persistencia:
     * - Persistencia Binaria: Se utiliza ObjectInputStream sobre FileInputStream para deserializar
     *   directamente una estructura de objetos Paciente[] almacenada en datos/datos_prueba.dat.
     *   Se prefiere la serialización binaria sobre archivos de texto porque preserva de forma
     *   nativa los tipos de datos, atributos y el estado completo de las instancias sin necesidad
     *   de parsear cadenas de texto.
     * - Reutilización de TAD: Por cada objeto Paciente recuperado, se invoca generarTurno(),
     *   garantizando que entren a las colas según su tipo y a la pila de deshacer siguiendo
     *   las reglas del sistema.
     */
    public void cargarDatosPrueba() {
        File archivo = new File(ARCHIVO_DATOS_PRUEBA);
        if (!archivo.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Paciente[] pacientesPrueba = (Paciente[]) ois.readObject();
            for (Paciente p : pacientesPrueba) {
                generarTurno(p.getNombre(), p.getTipo());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos de prueba: " + e.getMessage());
        }
    }

    /**
     * Guarda el historial completo de pacientes atendidos en un archivo binario.
     *
     * Justificación del TAD y Persistencia:
     * - Persistencia Binaria: Se utiliza ObjectOutputStream sobre FileOutputStream para serializar
     *   el objeto historico (instancia de Lista<Paciente>) en la ruta datos/historico.dat.
     *   Dado que la clase Lista implementa Serializable, la Máquina Virtual de Java guarda la
     *   estructura con su arreglo interno de elementos en un solo paso, conservando el orden
     *   exacto de atención sin necesidad de convertir cada Paciente a formato de texto (CSV o JSON).
     * - Seguridad de Directorio: Se verifica que la carpeta datos/ exista antes de proceder con
     *   la escritura para evitar errores de E/S.
     */
    public void guardarHistoricoBinario() {
        File carpeta = new File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivo = new File(carpeta, "historico.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(historico);
            System.out.println("Histórico guardado exitosamente en " + archivo.getPath());
        } catch (IOException e) {
            System.err.println("Error al guardar el histórico en archivo binario: " + e.getMessage());
        }
    }

    // ----- Métodos de visualización (ya implementados, no los modifiques) -----

    public void mostrarHistorico() {
        System.out.println("----- HISTORICO DE ATENCION -----");
        if (historico.estaVacia()) {
            System.out.println("Aun no se ha atendido a ningun paciente.");
        } else {
            int i = 1;
            for (Paciente p : historico) {
                System.out.println(i + ". " + p);
                i++;
            }
        }
    }

    public void mostrarEstadoColas() {
        System.out.println("----- ESTADO ACTUAL DE LAS COLAS -----");
        System.out.println("Prioritaria (" + colaPrioritaria.tamano() + "): " + colaPrioritaria);
        System.out.println("Premium (" + colaPremium.tamano() + "): " + colaPremium);
        System.out.println("General (" + colaGeneral.tamano() + "): " + colaGeneral);
    }
}