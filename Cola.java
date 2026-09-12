import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TAD Cola (Queue).
 *
 * Implementación propia, sin usar las clases de colecciones de Java
 * ({@code java.util.Queue}, {@code java.util.LinkedList}, etc.).
 *
 * Comportamiento: FIFO (First In, First Out) — el primer elemento que
 * entra es el primero en salir. Se implementa internamente con nodos
 * enlazados (lista enlazada simple), manteniendo referencias al frente
 * y al final para que encolar y desencolar sean operaciones de
 * complejidad O(1).
 *
 * Esta clase es de uso general: puede almacenar cualquier tipo de dato
 * ({@code T}), no solo objetos {@code Paciente}. En este proyecto se usa
 * para representar cada una de las tres colas de atención del digiturno.
 *
 * @param <T> tipo de los elementos que almacena la cola.
 */
public class Cola<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nodo interno de la cola. Cada nodo guarda un dato y una referencia
     * al siguiente nodo de la cadena. Es una clase privada porque es un
     * detalle de implementación: quien usa Cola no necesita conocerla.
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

    /** Referencia al primer nodo de la cola (el próximo en salir). */
    private Nodo<T> frente;

    /** Referencia al último nodo de la cola (el más reciente en entrar). */
    private Nodo<T> ultimo;

    /** Cantidad actual de elementos almacenados. */
    private int tamano;

    /** Crea una cola vacía. */
    public Cola() {
        frente = null;
        ultimo = null;
        tamano = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * Complejidad: O(1), porque se mantiene una referencia directa al
     * último nodo y no es necesario recorrer la cola.
     *
     * @param elemento el elemento a encolar.
     */
    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);

        if (estaVacia()) {
            // Si la cola estaba vacía, el nuevo nodo es a la vez el
            // frente y el último.
            frente = nuevoNodo;
        } else {
            // Se enlaza el nuevo nodo después del último existente.
            ultimo.siguiente = nuevoNodo;
        }

        ultimo = nuevoNodo;
        tamano++;
    }

    /**
     * Retira y retorna el elemento que está en el frente de la cola.
     * Complejidad: O(1).
     *
     * @return el elemento que estaba en el frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desencolar: la cola está vacía.");
        }

        T dato = frente.dato;
        frente = frente.siguiente;

        // Si al retirar el frente la cola quedó vacía, también se debe
        // actualizar la referencia a "ultimo" para que no quede colgando.
        if (frente == null) {
            ultimo = null;
        }

        tamano--;
        return dato;
    }

    /**
     * Retorna el elemento del frente sin retirarlo de la cola.
     *
     * @return el elemento que está en el frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    public T verFrente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return frente.dato;
    }

    /**
     * Indica si la cola no tiene elementos.
     *
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return tamano == 0;
    }

    /**
     * Retorna la cantidad de elementos almacenados actualmente.
     *
     * @return el tamaño de la cola.
     */
    public int tamano() {
        return tamano;
    }

    /**
     * Elimina de la cola la primera aparición del elemento indicado,
     * sin importar en qué posición se encuentre (no necesariamente en
     * el frente). Se usa, por ejemplo, para "deshacer" un turno que
     * todavía no ha sido atendido y por lo tanto sigue en algún punto
     * de la cola.
     *
     * La comparación se hace con el método equals() del elemento; si la
     * clase T no lo redefine, se usa la comparación por referencia
     * (equivalente a ==), que es el comportamiento que necesita este
     * proyecto: se busca exactamente el mismo objeto que fue encolado.
     *
     * Complejidad: O(n), porque en el peor caso hay que recorrer toda
     * la cola para encontrar el elemento.
     *
     * @param elemento el elemento a eliminar.
     * @return true si el elemento fue encontrado y eliminado, false si
     *         no estaba en la cola.
     */
    public boolean eliminar(T elemento) {
        if (estaVacia()) {
            return false;
        }

        // Caso especial: el elemento a eliminar es el que está al frente.
        if (frente.dato.equals(elemento)) {
            desencolar();
            return true;
        }

        // Caso general: se recorre la cola buscando el elemento,
        // llevando siempre el nodo anterior para poder "saltarlo".
        Nodo<T> anterior = frente;
        Nodo<T> actual = frente.siguiente;

        while (actual != null) {
            if (actual.dato.equals(elemento)) {
                anterior.siguiente = actual.siguiente;

                // Si el nodo eliminado era el último, hay que actualizar
                // la referencia "ultimo".
                if (actual == ultimo) {
                    ultimo = anterior;
                }

                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }

        return false; // el elemento no estaba en la cola
    }

    /**
     * Representación en texto de la cola, mostrando sus elementos en
     * orden desde el frente hasta el final, con el mismo formato que
     * usan las colecciones estándar de Java: [elem1, elem2, elem3].
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = frente;
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

    /**
     * Permite recorrer la cola con un ciclo for-each
     * (for (T elemento : miCola) { ... }), sin exponer los nodos
     * internos ni permitir modificarla mientras se recorre.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = frente;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
}
