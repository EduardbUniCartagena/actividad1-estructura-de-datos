import java.util.Scanner;

/**
 * Clase de prueba del sistema de digiturno.
 *
 * Esta clase YA ESTÁ COMPLETA. No debes modificarla.
 * Úsala para probar los métodos que completes en Digiturno.java:
 * cada opción del menú llama directamente a un método de esa clase.
 *
 * Al iniciar, carga automáticamente los pacientes de prueba desde el
 * archivo binario datos/datos_prueba.dat (una vez que completes el
 * método cargarDatosPrueba() de Digiturno, estos pacientes deben
 * aparecer ya encolados según su tipo).
 */
public class Main {

    public static void main(String[] args) {
        Digiturno digiturno = new Digiturno();
        Scanner sc = new Scanner(System.in);
        int opcion;

        // Carga datos binarios de prueba al iniciar, si existen.
        digiturno.cargarDatosPrueba();

        do {
            System.out.println("\n===== DIGITURNO - CONSULTORIO MEDICO =====");
            System.out.println("1. Tomar turno (agregar paciente a la cola segun su tipo)");
            System.out.println("2. Atender siguiente paciente");
            System.out.println("3. Deshacer ultimo turno generado");
            System.out.println("4. Ver historico de atencion");
            System.out.println("5. Ver estado de las colas");
            System.out.println("6. Guardar historico en archivo binario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(sc.nextLine().trim());

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre del paciente: ");
                    String nombre = sc.nextLine();
                    System.out.print("Tipo (1=GENERAL, 2=PREMIUM, 3=PRIORITARIO): ");
                    int tipoOp = Integer.parseInt(sc.nextLine().trim());
                    TipoPaciente tipo = switch (tipoOp) {
                        case 2 -> TipoPaciente.PREMIUM;
                        case 3 -> TipoPaciente.PRIORITARIO;
                        default -> TipoPaciente.GENERAL;
                    };
                    // Al tomar el turno, el paciente se agrega a la cola
                    // correspondiente segun su tipo (metodo generarTurno).
                    digiturno.generarTurno(nombre, tipo);
                }
                case 2 -> digiturno.atenderSiguiente();
                case 3 -> digiturno.deshacerUltimoTurno();
                case 4 -> digiturno.mostrarHistorico();
                case 5 -> digiturno.mostrarEstadoColas();
                case 6 -> digiturno.guardarHistoricoBinario();
                case 0 -> System.out.println("Cerrando aplicacion...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}
