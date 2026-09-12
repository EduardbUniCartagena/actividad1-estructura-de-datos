# Simulación de Digiturno para Consultorio Médico

Este proyecto es una aplicación de consola desarrollada en **Java 21** para la gestión organizada de turnos en un consultorio médico. El sistema clasifica a los pacientes según su condición y aplica principios estrictos de **Tipos Abstractos de Datos (TADs) lineales** para garantizar un flujo de atención eficiente, priorizado y trazable, sin recurrir a bibliotecas de colecciones nativas (`java.util`) ni a patrones de diseño complejos.

---

## 📌 Enlace al Repositorio

El código fuente completo y la documentación del proyecto se encuentran alojados en:
👉 **[Repositorio GitHub: Actividad 1 - Estructura de Datos](https://github.com/eduardbunicartagena/actividad1-estructura-de-datos)**

---

## 🚀 Características Principales

1. **Gestión de Turnos por Categoría:** Clasificación de pacientes en tres categorías con priorización automática[cite: 2]:
   * **Atención Prioritaria:** Adultos mayores, mujeres embarazadas y niños (Atención preferencial por ley)[cite: 2].
   * **Público Premium:** Pacientes con plan de atención preferencial contratado[cite: 2].
   * **Público General:** Pacientes sin condiciones especiales[cite: 2].
2. **Regla de Prioridad de Atención:** La selección del siguiente paciente a atender evalúa las colas en orden de urgencia (`PRIORITARIO` $\rightarrow$ `PREMIUM` $\rightarrow$ `GENERAL`), manteniendo el principio FIFO (*First In, First Out*) dentro de cada categoría[cite: 2].
3. **Funcionalidad de Deshacer (Undo):** Permite revertir la generación del último turno registrado antes de ser atendido[cite: 2].
4. **Persistencia de Datos Binarios:**
   * Carga automática de pacientes de prueba desde `datos/datos_prueba.dat` al iniciar la aplicación[cite: 2].
   * Guardado del histórico acumulado de atenciones en `datos/historico.dat` mediante serialización de objetos (`ObjectOutputStream` / `ObjectInputStream`)[cite: 2].

---

## 🏗️ Arquitectura y Justificación de TADs

El proyecto destaca por utilizar **implementaciones propias de TADs enlazados y dinámicos**, manteniendo el sistema completamente libre de colecciones del paquete `java.util`[cite: 2]:

| TAD | Clase Propia | Estructura Interna | Justificación en el Sistema |
| :--- | :--- | :--- | :--- |
| **Cola** | `Cola<T>` | Nodos Enlazados (`Nodo<T>`) | Garantiza el comportamiento FIFO (el primero en llegar es el primero en salir) para cada categoría de paciente, permitiendo encolar y desencolar en $O(1)$[cite: 2]. |
| **Pila** | `Pila<T>` | Nodos Enlazados (`Nodo<T>`) | Aplica el comportamiento LIFO (*Last In, First Out*) en $O(1)$ para acceder al último turno generado y retirarlo mediante el método de deshacer[cite: 2]. |
| **Lista** | `Lista<T>` | Arreglo Dinámico (`Object[]`) | Permite el registro secuencial del histórico de atenciones del día con acceso directo por índice[cite: 2]. |

---

## 💻 Estructura del Proyecto

```text
actividad1-estructura-de-datos/
├── src/
│   ├── TipoPaciente.java     # Enum con las categorías: GENERAL, PREMIUM, PRIORITARIO
│   ├── Paciente.java         # Modelo de datos (Implementa Serializable)
│   ├── Cola.java             # TAD Cola enlazada propia (FIFO)
│   ├── Pila.java             # TAD Pila enlazada propia (LIFO)
│   ├── Lista.java            # TAD Lista dinámica propia (Arreglo)
│   ├── Digiturno.java        # Lógica del negocio y control de TADs/Persistencia
│   └── Main.java             # Arnés de pruebas e interfaz de consola
├── datos/
│   ├── datos_prueba.dat      # Archivo binario con pacientes de muestra
│   └── historico.dat         # Archivo binario generado con el historial guardado
├── anexo_uso_ia.md           # Registro de uso responsable de IA Generativa
└── README.md                 # Documentación del proyecto