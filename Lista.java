import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TAD Lista.
 *
 * Implementación propia, sin usar las clases de colecciones de Java
 * ({@code java.util.List}, {@code java.util.ArrayList}, etc.).
 *
 * Se implementa internamente con un arreglo (array) que crece
 * automáticamente cuando se llena, duplicando su capacidad. A
 * diferencia de la Cola y la Pila, la Lista permite acceso directo a
 * cualquier posición mediante un índice, en complejidad O(1), lo cual
 * es justamente lo que se necesita para el histórico de atención: se
 * agregan pacientes al final y luego se recorren o consultan en
 * cualquier orden.
 *
 * @param <T> tipo de los elementos que almacena la lista.
 */
public class Lista<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    /** Capacidad inicial del arreglo interno cuando se crea la lista. */
    private static final int CAPACIDAD_INICIAL = 10;

    /**
     * Arreglo interno donde se almacenan los elementos.
     * Se declara como Object[] (y no T[]) porque en Java no está
     * permitido crear directamente arreglos de un tipo genérico;
     * esta es la forma estándar de resolver esa limitación del
     * lenguaje. Los métodos que leen del arreglo hacen un cast a T.
     */
    private Object[] elementos;

    /** Cantidad actual de elementos almacenados (no confundir con la
     *  capacidad del arreglo interno, que puede ser mayor). */
    private int tamano;

    /** Crea una lista vacía con la capacidad inicial por defecto. */
    public Lista() {
        elementos = new Object[CAPACIDAD_INICIAL];
        tamano = 0;
    }

    /**
     * Agrega un elemento al final de la lista. Si el arreglo interno
     * está lleno, primero se duplica su capacidad.
     * Complejidad: O(1) amortizado (el redimensionamiento ocurre con
     * poca frecuencia a medida que la lista crece).
     *
     * @param elemento el elemento a agregar.
     */
    public void agregar(T elemento) {
        if (tamano == elementos.length) {
            duplicarCapacidad();
        }
        elementos[tamano] = elemento;
        tamano++;
    }

    /**
     * Retorna el elemento almacenado en la posición indicada.
     * Complejidad: O(1).
     *
     * @param indice posición a consultar, entre 0 y tamaño()-1.
     * @return el elemento en esa posición.
     * @throws IndexOutOfBoundsException si el índice está fuera de rango.
     */
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        validarIndice(indice);
        return (T) elementos[indice];
    }

    /**
     * Retorna la cantidad de elementos almacenados actualmente.
     *
     * @return el tamaño de la lista.
     */
    public int tamano() {
        return tamano;
    }

    /**
     * Indica si la lista no tiene elementos.
     *
     * @return true si la lista está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return tamano == 0;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    /**
     * Crea un nuevo arreglo con el doble de capacidad y copia en él
     * todos los elementos existentes. Es la operación que permite que
     * la lista se comporte como si "creciera" dinámicamente, aunque
     * los arreglos en Java tienen tamaño fijo una vez creados.
     */
    private void duplicarCapacidad() {
        Object[] nuevoArreglo = new Object[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevoArreglo[i] = elementos[i];
        }
        elementos = nuevoArreglo;
    }

    /**
     * Representación en texto de la lista, mostrando sus elementos en
     * orden, con el mismo formato que usan las colecciones estándar de
     * Java: [elem1, elem2, elem3].
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamano; i++) {
            sb.append(elementos[i]);
            if (i < tamano - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Permite recorrer la lista con un ciclo for-each
     * (for (T elemento : miLista) { ... }).
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int indiceActual = 0;

            @Override
            public boolean hasNext() {
                return indiceActual < tamano;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elementos[indiceActual++];
            }
        };
    }
}
