package cuentabancaria;

//Clase InputMismatchException para capturar la exception de posibles errores de tipo al introducir datos.
import java.util.InputMismatchException;
//Clase Scanner de Java para utilizarla para recojer datos del usuario
import java.util.Scanner;

/**
 * Código aplicación para la creación y gestión de un objeto cuenta bancaria.
 * Donde se pueden realizar diferentes acciones sobre la cuenta, tales como ver
 * información sobre ella, ingresar y retirar efectivo.
 *
 * @author Víctor Visús
 *
 */
public class appCuentaBancaria {

    public static void main(String[] args) throws Exception {
        //Declaro una variable para recojer datos por consola
        Scanner consola = new Scanner(System.in);

        //Declaro el String para el nombre del titular
        String nombreTitular;
        //Declaro el String que almacenará el número de cuenta.
        String codCompleto;

        /**
         * * Se solicita que se introduzca el nombre del titular de la cuenta,
         * Se manda validar al método de clase, si este esta vacío o tiene más
         * de 30 caracteres volverá a realizar la acción **
         */
        do {
            //Solicita el nombre de titular de la cuenta al usuario
            System.out.println("Escribe tu nombre: ");
            nombreTitular = consola.nextLine();
            //nombreTitular = "victor";
        } while (CuentaBancaria.recibeNombre(nombreTitular));

        /**
         * * Pide al usuario que introduzca el número de cuenta y lo manda a
         * validar, llamando al método de clase que realiza esta acción.
         *
         * NOTA: No valida tal y como se pide en el ejercicio (por falta de
         * tiempo, lo siento)
         *
         * Solicitará el número de cuenta al usuario mientras que éste no sea
         * válido. *
         */
        do {
            System.out.println("Escribe el número de cuenta: ");
            codCompleto = consola.nextLine();
            //codCompleto = "01234567890123456789";
        } while (!CuentaBancaria.validarCCC(codCompleto));

        /**
         * * Una vez recopilados los dos datos necesarios para crear el objeto,
         * con el CCC validado, se llama al Constructor que creará el objeto
         * "cuentaCliente", enviando los parámetros introducidos por el usuario.
         */
        CuentaBancaria cuentaCliente = new CuentaBancaria(nombreTitular, codCompleto);

        /**
         * * Imprime las opciones de menú **
         */
        System.out.println("\nOperaciones disponibles: \n"
                + "1. Ver número completo de cuenta \n"
                + "2. Ver titular de la cuenta \n"
                + "3. Ver código de la entidad \n"
                + "4. Ver código de la oficina \n"
                + "5. Ver número de cuenta \n"
                + "6. Ver dígitos de control de la cuenta \n"
                + "7. Realizar un ingreso \n"
                + "8. Retirar dinero \n"
                + "9. Consultar saldo \n"
                + "10. Salir" + "\n"
        );

        /**
         * * Menú de Operaciones **
         *
         * Se crea la variable opcion, que recogerá el número de operación que
         * el usuario quiera realizar
         *
         * Mediante un bucle do-while, envuelto en una sentencia try-catch para
         * capturar la posible Exception que se genere porque el usuario haya
         * introducido algún caracter alfabético al seleccionar la opción, se
         * ejecuta una instrucción switch que dependiendo del valor de la
         * variable opcion se conducirá por una vía (case) u otra, las cuales
         * corresponden a:
         *
         * case 1: Ver el número de cuenta completo (CCC). -> Imprime el número
         * de cuenta completo mediante el método de la clase getCodCompleto()
         *
         * case 2: Ver el titular de la cuenta. -> Imprime el titular de cuenta
         * mediante el método de la clase getNombreTitular()
         *
         * case 3: Ver el código de la entidad. -> Imprime el código de la
         * entidad mediante el método de la clase getCodEntidad()
         *
         * case 4: Ver el código de la oficina. -> Imprime el código de la
         * oficina mediante el método de la clase getCodOficina()
         *
         * case 5: Ver el número de la cuenta. -> Imprime el número de cuenta
         * mediante el método de la clase getNumCuenta()
         *
         * case 6: Ver los dígitos de control de la cuenta. -> Imprime los
         * dígitos de control mediante el método de la clase getDigControl()
         *
         * case 7: Realizar un ingreso. -> Llama al método ingresoEfectivo() y
         * le manda los parámetros necesarios.
         *
         * case 8: Retirar efectivo. -> Llama al método retiradaEfectivo() y le
         * manda los parámetros necesarios.
         *
         * case 9: Consultar saldo. -> Imprime el saldo de la cuenta mediante el
         * método de la clase getSaldo()
         *
         * case 10: Salir -> Detiene la ejecución.
         *
         * @throw InputMismatchException : Termina la ejecución si el dato
         * introducido para elegir la opción de menú NO es un número entero
         */
        int opcion;
        try {
            do {
                System.out.println("Elige que operación quieres realizar:");
                opcion = consola.nextInt();

                switch (opcion) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Código de cuenta corriente (CCC): "
                                + cuentaCliente.getCodCompleto());
                        break;
                    case 2:
                        System.out.println("Nombre del Titular: "
                                + cuentaCliente.getNombreTitular());
                        break;
                    case 3:
                        System.out.println("Código de la entidad: "
                                + cuentaCliente.getCodEntidad());
                        break;
                    case 4:
                        System.out.println("Código de la oficina: "
                                + cuentaCliente.getCodOficina());
                        break;
                    case 5:
                        System.out.println("Número de cuenta: "
                                + cuentaCliente.getNumCuenta());
                        break;
                    case 6:
                        System.out.println("Dígitos de control: "
                                + cuentaCliente.getDigControl());
                        break;
                    case 7:
                        ingresoEfectivo(consola, cuentaCliente);
                        break;
                    case 8:
                        retiradaEfectivo(consola, cuentaCliente);
                        break;
                    case 9:
                        System.out.println("El saldo actual de la cuenta es: "
                                + cuentaCliente.getSaldo());
                        break;
                    case 10:
                        //System.out.println("Gracias.");
                        break;

                    default:
                        System.out.println("La operación elejida no es una opción del menú: "
                                + opcion);
                        break;
                }
            } while (opcion != 10);
        } catch (InputMismatchException nfe) {
            System.err.println("Sólo valores entre 0 y 9.\nCarácteres alfabéticos no son validos - Salimos.");

        }
    }

    /**
     * * Método previo al ingreso de efectivo **
     *
     * Solicita el importe en el que se quiere aumentar el saldo y lo envía al
     * método de clase ingreso
     *
     * Comprueba que el dato introducido es de tipo double, si no es así, lanza
     * un mensaje de error y vuelve a solicitar el importe
     *
     * Captura la Exception que pueda ser enviada por el método de clase al que
     * llama.
     */
    private static void ingresoEfectivo(Scanner consola, CuentaBancaria cuentaCliente) {
        boolean error = false;
        do {
            try {
                error = false;
                System.out.print("Indique el importe que desea ingresar: ");
                double efectivo = consola.nextDouble();

                cuentaCliente.ingreso(efectivo);

            } catch (InputMismatchException ex) {
                System.out.println("El importe introducido no es correcto");
                consola.next();
                error = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                error = true;
            }
        } while (error);
    }

    /**
     * * Método previo al reintegro **
     *
     * Solicita el importe que se quiere retirar del saldo y lo envía al método
     * de clase retiradaEfectivo.
     *
     * Comprueba que si el saldo de la cuenta es 0 y si es así lanza un mensaje
     * de que no se dispone de saldo y no se puede realizar esta operación.
     *
     * Comprueba que el dato introducido es de tipo double, si no es asi, lanza
     * un mensaje de error y vuelve a solicitar el importe
     *
     * Captura la Excetion que pueda ser enviada por el método de clase al que
     * llama.
     */
    private static void retiradaEfectivo(Scanner consola, CuentaBancaria cuentaCliente) {
        boolean error = false;
        do {
            if (cuentaCliente.getSaldo() == 0) {
                System.out.println("Operación no disponible. "
                        + "No tiene saldo en su cuenta.");
            } else {
                try {
                    error = false;
                    System.out.print("Indique el importe que desea retirar: ");
                    double efectivo = consola.nextDouble();

                    cuentaCliente.retiradaEfectivo(efectivo);
                } catch (InputMismatchException ex) {
                    System.out.println("El importe introducido no es correcto");
                    consola.next();
                    error = true;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    error = true;
                }
            }
        } while (error);
    }
}
