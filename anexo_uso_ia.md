# Anexo de Uso de IA Generativa

## 1. Datos Generales
* **Nombre del estudiante:** Eduard David Barrios Padilla
* **CIPA (si aplica):** zzz
* **Herramienta(s) de IA utilizada(s):** Gemini
---

## 2. Registro de Interacciones

### **Día 1: 11/09/2026 — Contextualización, Entendimiento del Código y Planificación**

| # prompt | Prompt textual utilizado                                                                                                                                                                                                                                                                                                                                    | Resumen de la respuesta recibida | Qué modificó el estudiante y por qué |
| :---: |:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| :--- | :--- |
| **1** | *"Hola, geminis. aquí te estoy pasando un enunciado en cual está completamente detallada la actividad a realizar y los entregables a los que hay que llegar... lo primero que vamos a hacer es entender el enunciado y el código, para así generar una guia detallada paso a paso del lo que debemos hacer para llegar a la conclusión de esta actividad."* | Resumió las directrices de la actividad, identificó los 5 métodos pendientes por implementar en `Digiturno.java` y propuso una guía de trabajo paso a paso. | Evalué la propuesta general para asegurar que la estrategia cumpliera con la prohibición estricta de usar `java.util` y cubriera todos los entregables del curso. |
| **2** | *"Explicame el código fuente, el flujo del código desde el main, como en un hilo conductor, las llamadas que hace, lo que se va implementando y de dónde lo trae, lo que debería hacer y dónde se rompe el codigo..."*                                                                                                                                      | Explicó la arquitectura general por clases, el flujo de ejecución desde `Main.java` hacia `Digiturno.java` y el propósito de los métodos en `Cola`, `Pila` y `Lista`. | Solicité mayor profundidad técnica para identificar los puntos exactos de ruptura del flujo de control en la clase `Digiturno.java`. |
| **3** | *"Explícamelo más detallado por archivo, primero qué es un TAD, cómo se van enlazando los archivos, y dónde se rompe la lógica con el código actual..."*                                                                                                                                                                                                    | Desglosó el concepto de TAD (abstracción), la función de cada archivo del proyecto e identificó los 5 puntos de ruptura específicos donde la lógica se corta. | Validé mentalmente el hilo conductor entre las clases y analicé la razón de usar tres colas independientes en lugar de una cola de prioridad. |
| **4** | *"¿Por qué la funcionalidad de deshacer exige el uso de una Pila y no de una Cola o una Lista?"*                                                                                                                                                                                                                                                            | Explicó que la acción de deshacer requiere acceder al último turno registrado (principio LIFO), comportamiento que el TAD Pila resuelve naturalmente en $O(1)$. | Comprobé cómo este comportamiento LIFO complementa a las colas FIFO de atención, entendiendo el diseño híbrido requerido sin mezclar estructuras. |
| **5** | *"Aclárame cómo funciona la persistencia binaria en este proyecto y por qué no se usan archivos CSV o JSON."*                                                                                                                                                                                                                                               | Explicó que la serialización binaria nativa de Java (`ObjectInputStream`/`ObjectOutputStream`) permite guardar y leer objetos directos (`Paciente[]` y `Lista<Paciente>`) preservando tipos y estados. | Confirmé el mecanismo a utilizar en las tareas de persistencia del día siguiente, asegurando el uso de `try-with-resources`. |

---

### **Día 2: 12/09/2026 — Escritura de Código, Documentación y Pruebas del Software**

| # prompt | Prompt textual utilizado | Resumen de la respuesta recibida | Qué modificó el estudiante y por qué |
| :---: | :--- | :--- | :--- |
| **6** | *"Analicemos la estructura lógica del método generarTurno. ¿Cuál es el orden adecuado para encolar el paciente y registrarlo en la pila de deshacer?"* | Explicó la secuencia lógica: instanciar `Paciente`, derivar a la cola correspondiente según su `TipoPaciente` mediante una estructura condicional y apilar al final. | Diseñé e implementé el método usando una sentencia `switch` para mantener el código limpio y legibilidad en las tres opciones de encolado. |
| **7** | *"Evaluemos las opciones de diseño para implementar atenderSiguiente respetando la regla estricta de prioridad entre colas..."* | Propuso la selección de la cola adecuada con un encadenamiento `if/else if` comprobando `!estaVacia()`, para desencolar y registrar en `historico`. | Implementé el método guardando la referencia de la cola en una variable auxiliar para realizar la llamada a `desencolar()` e `historico.agregar()` una sola vez. |
| **8** | *"Analicemos la solución para deshacerUltimoTurno combinando la Pila y el método eliminar de la Cola..."* | Explicó la desapilación de `pilaDeshacer` y el uso del método `.eliminar(paciente)` de la `Cola` correspondiente para remover el nodo sin romper el orden FIFO. | Implementé el método asegurando el manejo del caso donde la pila esté vacía y ajustando los nombres exactos de las variables como `colaPrioritaria`. |
| **9** | *"Evaluemos la implementación de cargarDatosPrueba y guardarHistoricoBinario usando try-with-resources..."* | Detalló la lectura de `datos_prueba.dat` para deserializar `Paciente[]` (reutilizando `generarTurno`) y la escritura directa de `historico` en `historico.dat`. | Estructuré el manejo de recursos con `try-with-resources` para evitar fugas de memoria y añadí la verificación del directorio `datos/` antes de escribir. |
| **10** | *"¿Cómo debemos manejar los comentarios en el código? ¿Dejamos los TODO del docente o solo nuestra documentación Javadoc?"* | Aclaró que se deben remover las marcas `TODO` e instrucciones docentes por buenas prácticas, conservando los comentarios Javadoc de justificación técnica redactados por el estudiante. | Eliminé todos los bloques `TODO` del código base en `Digiturno.java` y mantuve únicamente mis comentarios Javadoc con las justificaciones de uso de TADs. |

---

## 3. Reflexión Final

### Respuestas a las Preguntas Guía:
1. **Dificultades y correcciones realizadas por el estudiante:**
   Durante la implementación, corregí el nombre de la variable de la cola de atención preferencial (que en el código entregado se llama `colaPrioritaria`). Además, decidí optimizar el método `atenderSiguiente()` utilizando una variable auxiliar de tipo `Cola<Paciente>` para evitar la duplicación de llamadas a `.desencolar()` y `.agregar()` en cada bloque condicional, garantizando un código más limpio y mantenible. También me aseguré de remover las marcas `TODO` del profesor para presentar un producto terminado de estándar profesional.

2. **Aprendizaje sobre TADs (Cola, Pila, Lista):**
   Comprendí de forma práctica la diferencia entre el contrato conceptual de un TAD y su implementación interna. Entendí que la arquitectura de utilizar tres colas simples e independientes para resolver prioridades permite mantener intacto el comportamiento FIFO puro en cada categoría sin recurrir a estructuras híbridas complejas. Asimismo, la necesidad de acceder al último elemento insertado en la función deshacer justificó claramente el principio LIFO del TAD Pila, algo que una cola o una lista no resuelven con la misma eficiencia en $O(1)$.

---

## 4. Declaración de Autenticidad
Declaro que la información registrada en este anexo refleja de manera honesta y transparente el uso de herramientas de inteligencia artificial generativa como apoyo conceptual e instructivo para el desarrollo de esta actividad.