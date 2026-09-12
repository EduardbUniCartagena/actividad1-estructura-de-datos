import java.io.Serializable;

/**
 * TAD Pila (Stack).
 *
 * Implementación propia, sin usar las clases de colecciones de Java
 * ({@code java.util.Stack}, {@code java.util.Deque}, etc.).
 *
 * Comportamiento: LIFO (Last In, First Out) — el último elemento que
 * entra es el primero en salir. Se implementa internamente con nodos
 * enlazados (lista enlazada simple), insertando y retirando siempre
 * por el mismo extremo ("el tope"), lo que hace que apilar y desapilar
 * sean operaciones de complejidad O(1).
 *
 * En este proyecto se usa para poder deshacer el último turno generado:
 * el turno más reciente siempre queda en el tope de la pila.
 *
 * @param <T> tipo de los elementos que almacena la pila.
 */
public class Pila<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nodo interno de la pila. Cada nodo guarda un dato y una
     * referencia al nodo que quedó justo debajo de él.
     */
    private static class Nodo<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    /** Referencia al nodo que está en el tope (el último apilado). */
    private Nodo<T> tope;

    /** Cantidad actual de elementos almacenados. */
    private int tamano;

    /** Crea una pila vacía. */
    public Pila() {
        tope = null;
        tamano = 0;
    }

    /**
     * Agrega un elemento en el tope de la pila.
     * Complejidad: O(1).
     *
     * @param elemento el elemento a apilar.
     */
    public void apilar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);

        // El nuevo nodo queda apuntando al que antes era el tope, y
        // pasa a ser el nuevo tope.
        nuevoNodo.siguiente = tope;
        tope = nuevoNodo;
        tamano++;
    }

    /**
     * Retira y retorna el elemento que está en el tope de la pila.
     * Complejidad: O(1).
     *
     * @return el elemento que estaba en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desapilar: la pila está vacía.");
        }

        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return dato;
    }

    /**
     * Retorna el elemento del tope sin retirarlo de la pila.
     *
     * @return el elemento que está en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    public T verTope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return tope.dato;
    }

    /**
     * Indica si la pila no tiene elementos.
     *
     * @return true si la pila está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return tamano == 0;
    }

    /**
     * Retorna la cantidad de elementos almacenados actualmente.
     *
     * @return el tamaño de la pila.
     */
    public int tamano() {
        return tamano;
    }

    /**
     * Representación en texto de la pila, mostrando sus elementos desde
     * el tope hasta la base, con el mismo formato que usan las
     * colecciones estándar de Java: [elem1, elem2, elem3].
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}
