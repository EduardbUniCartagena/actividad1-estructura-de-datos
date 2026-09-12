import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Sistema de digiturno para un consultorio médico.
 * CÓDIGO BASE - actividad inicial de unidad (Listas, Pilas y Colas).
 *
 * Debes completar los métodos marcados con // TODO.
 * No modifiques las firmas de los métodos ni los atributos.
 *
 * Esta clase contiene ÚNICAMENTE la lógica del digiturno (las estructuras
 * de datos y las operaciones sobre ellas). NO contiene menú de consola ni
 * método main. Para PROBAR los métodos que completes aquí, ejecuta la
 * clase Main (archivo Main.java), que ya está completamente implementada
 * y te ofrece un menú de texto: cada opción del menú llama directamente
 * a uno de los métodos que debes completar en esta clase.
 *
 * Estructuras (TAD) que se usan en esta clase: NO son las colecciones de
 * java.util. Son implementaciones propias de este proyecto, ya
 * completamente resueltas en los archivos Cola.java, Pila.java y
 * Lista.java. Antes de completar los métodos de esta clase, revisa el
 * Javadoc de esas tres clases para conocer los nombres exactos de sus
 * métodos (encolar/desencolar, apilar/desapilar, agregar/obtener, etc.).
 *
 *  - Cola  -> orden de llegada dentro de cada tipo de paciente (FIFO).
 *  - Lista -> historico de atención del día.
 *  - Pila  -> deshacer el último turno generado (LIFO).
 *
 * Persistencia (archivos binarios):
 *  - Lectura de datos de prueba desde "datos/datos_prueba.dat" (ya incluido
 *    en este proyecto) para poder probar la aplicación sin digitar datos.
 *    El archivo contiene un arreglo Paciente[] serializado.
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
     * TODO 1 - Tomar turno (generar turno y encolar según tipo de paciente).
     * Este es el método que la clase Main invoca cuando el usuario elige
     * la opción "1. Tomar turno" en el menú.
     * Debes:
     *  1) Crear un objeto Paciente con el nombre y tipo recibidos.
     *  2) Agregarlo a la cola que corresponda según su tipo
     *     (colaGeneral, colaPremium o colaPrioritaria), usando el
     *     método encolar(...) de la clase Cola.
     *  3) Apilarlo en pilaDeshacer (para poder deshacerlo más adelante),
     *     usando el método apilar(...) de la clase Pila.
     *  4) Imprimir en consola: "Turno generado: " + paciente
     *
     * TAD que debes usar aquí: Cola.encolar() y Pila.apilar().
     */
    public void generarTurno(String nombre, TipoPaciente tipo) {
        // TODO: completar este método
    }

    /**
     * TODO 2 - Atender al siguiente paciente.
     * Debes aplicar esta regla de prioridad entre colas:
     *   1) Si colaPrioritaria tiene pacientes, atiende al primero de esa cola.
     *   2) Si colaPrioritaria está vacía pero colaPremium tiene pacientes,
     *      atiende al primero de colaPremium.
     *   3) Si ambas están vacías, atiende al primero de colaGeneral.
     *   4) Si las tres están vacías, imprime: "No hay pacientes en espera."
     *
     * El paciente atendido debe agregarse al histórico (Lista) y se debe
     * imprimir: "Atendiendo a: " + paciente
     *
     * Pista: usa estaVacia() para verificar cada cola, y desencolar()
     * para retirar y obtener el primer elemento en un solo paso. Para
     * agregar al histórico usa el método agregar(...) de la clase Lista.
     *
     * TAD que debes usar aquí: Cola.estaVacia(), Cola.desencolar() y
     * Lista.agregar().
     */
    public void atenderSiguiente() {
        // TODO: completar este método
    }

    /**
     * TODO 3 - Deshacer el último turno generado.
     * Debes:
     *  1) Verificar que pilaDeshacer no esté vacía (estaVacia()); si lo
     *     está, imprime "No hay turnos para deshacer." y termina el método.
     *  2) Sacar el último paciente apilado con desapilar().
     *  3) Intentar eliminarlo de la cola en la que había quedado, según
     *     su tipo (colaGeneral, colaPremium o colaPrioritaria), usando
     *     el método eliminar(objeto) de la clase Cola.
     *  4) Si eliminar() devuelve true, imprime: "Turno deshecho: " + paciente
     *     Si devuelve false (ya fue atendido y no está en ninguna cola),
     *     imprime: "El turno ya había sido atendido, no se puede deshacer: " + paciente
     *
     * TAD que debes usar aquí: Pila.estaVacia(), Pila.desapilar() y
     * Cola.eliminar().
     */
    public void deshacerUltimoTurno() {
        // TODO: completar este método
    }

    /**
     * TODO 4 - Persistencia: lectura de archivo binario.
     * Este método debe cargar los pacientes de prueba incluidos en el
     * archivo "datos/datos_prueba.dat" para que la aplicación tenga datos
     * con los cuales probarse sin necesidad de digitarlos manualmente.
     *
     * Debes:
     *  1) Verificar que el archivo exista (usa la clase File). Si no
     *     existe, imprime un mensaje y termina el método.
     *  2) Abrir un ObjectInputStream sobre un FileInputStream apuntando
     *     al archivo ARCHIVO_DATOS_PRUEBA (usa try-with-resources).
     *  3) Leer el objeto guardado con ois.readObject() y convertirlo
     *     (cast) a Paciente[] (un arreglo, no una lista de java.util).
     *  4) Por cada paciente del arreglo, llamar a generarTurno(nombre, tipo)
     *     para que quede correctamente encolado (reutiliza tu propio
     *     método del TODO 1). Puedes recorrer el arreglo con un for-each:
     *     for (Paciente p : pacientesPrueba) { ... }
     *  5) Capturar IOException y ClassNotFoundException e imprimir un
     *     mensaje de error si algo falla.
     *
     * Pista: el archivo binario contiene un único objeto serializado de
     * tipo Paciente[], escrito con ObjectOutputStream.writeObject().
     */
    public void cargarDatosPrueba() {
        // TODO: completar este método
    }

    /**
     * TODO 5 - Persistencia: escritura de archivo binario.
     * Este método debe guardar el histórico de atención (el objeto
     * Lista completo) en el archivo "datos/historico.dat", para que
     * quede disponible incluso después de cerrar la aplicación.
     *
     * Debes:
     *  1) Verificar que exista la carpeta "datos"; si no existe, crearla
     *     (usa File y el método mkdirs()).
     *  2) Abrir un ObjectOutputStream sobre un FileOutputStream apuntando
     *     a ARCHIVO_HISTORICO (usa try-with-resources).
     *  3) Escribir el histórico completo con oos.writeObject(historico).
     *     (Esto funciona porque la clase Lista implementa Serializable).
     *  4) Imprimir un mensaje confirmando cuántos registros se guardaron
     *     (usa historico.tamano()).
     *  5) Capturar IOException e imprimir un mensaje de error si algo falla.
     */
    public void guardarHistoricoBinario() {
        // TODO: completar este método
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

    // Esta clase NO tiene metodo main. Para probar los metodos que
    // completes aqui, ejecuta la clase Main (Main.java), que ya
    // esta lista y contiene el menu de consola.
}
