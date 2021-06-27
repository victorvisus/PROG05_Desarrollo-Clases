package aplicacioncuentabancaria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Objects;

/**
 * @author Elena Ortiz
 */
public class AplicacionCuentaBancaria {

    public static void main(String[] args) {
        //Librería para que el usuario introduzca los datos
        BufferedReader entradaDatos = new BufferedReader(new InputStreamReader(System.in));

        //Inicializamos las variables dni y número de cuenta y declaramos un booleano para volver a pedir los datos si se lanza alguna excepción
        String dni = null;
        String numeroCuenta = null;
        boolean volver;


        /*Para la entrada de datos se ha escogido el mismo patrón en todos, si la entrada de datos no tiene el formato adecuado el validador
		devolverá false por lo que se pedirá al usuario que introduzca los datos correctos o el booleano "volver" esté a false.
		Si se produce alguna excepción será capturada y el booleano "volver" se pondrá a true dentro del catch para que vuelva a pedir los
		datos al usuario
         */
        String nombre = introducirNombre(entradaDatos);

        do {
            System.out.println("Introduzca su DNI");
            try {
                do {
                    volver = false;
                    dni = entradaDatos.readLine();
                } while (dni == null || !CuentaBancaria.validarDni(dni));
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                volver = true;
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error en el sistema."
                        + "\nInténtelo de nuevo más tarde."
                        + "\nDisculpe las molestias");
                volver = true;
            }

        } while (volver);

        do {
            System.out.println("Introduzca su número de cuenta");
            try {
                do {
                    //Ejemplo de cuenta válida 4651 0444 26 4435436057
                    numeroCuenta = entradaDatos.readLine();
                    //Quitar todos los espacios y guiones que haya podido introducir
                    numeroCuenta = numeroCuenta.replaceAll(" ", "").replaceAll("-", "").trim();
                } while (!CuentaBancaria.validarCuentaBancaria(numeroCuenta));

            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                volver = true;
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error en el sistema."
                        + "\nInténtelo de nuevo más tarde."
                        + "\nDisculpe las molestias");
                volver = true;
            }
        } while (volver);

        //Con Objects.requireNonNull nos aseguramos de que el número de cuenta no es nulo antes de crear el objeto
        CuentaBancaria cuentaBancaria = new CuentaBancaria(nombre, dni, Objects.requireNonNull(numeroCuenta));

        String opcionesMenu = "\n¿Qué desea hacer?: \n"
                + "1. Ver número completo de cuenta \n"
                + "2. Ver titular de la cuenta \n"
                + "3. Ver código de la entidad \n"
                + "4. Ver código de la oficina \n"
                + "5. Ver número de cuenta \n"
                + "6. Ver dígitos de control \n"
                + "7. Realizar un ingreso \n"
                + "8. Retirar dinero \n"
                + "9. Consultar saldo \n"
                + "10. Salir";

        int opcion;
        try {
            do {
                System.out.println(opcionesMenu);
                opcion = Integer.parseInt(entradaDatos.readLine());
                switch (opcion) {

                    case 1:
                        System.out.printf("El número completo de la cuenta es: %s \n", cuentaBancaria.getCCC());
                        break;

                    case 2:
                        System.out.printf("El titular de la cuenta es: %s", cuentaBancaria.getNombreTitular());
                        break;

                    case 3:
                        System.out.printf("El código de la entidad es: %s", cuentaBancaria.getCodigoEntidad());
                        break;

                    case 4:
                        System.out.printf("El código de la oficina es: %s", cuentaBancaria.getCodigoOficina());
                        break;

                    case 5:
                        System.out.printf("El número de cuenta es: %s", cuentaBancaria.getNumeroCuenta());
                        break;

                    case 6:
                        System.out.printf("Los dígitos de control de su cuenta bancaria son: %s", cuentaBancaria.getDigitoControl());
                        break;

                    case 7:
                        ingresarDinero(entradaDatos, cuentaBancaria);
                        break;

                    case 8:
                        retirarDinero(entradaDatos, cuentaBancaria);
                        break;

                    case 9:
                        System.out.printf("El saldo actual de su cuenta es: %s\n", cuentaBancaria.getSaldo());
                        break;

                    case 10:
                        System.out.println("Ha solicitado salir de la aplicación. Gracias por su visita");
                        break;

                    default:
                        System.out.printf("La opción escogida: %d no se encuentra entre las disponibles escoja"
                                + " una opción válida del menú: %s\n", opcion, opcionesMenu);
                        break;

                }

            } while (opcion != 10);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Ha ocurrido un fallo en el programa, la avería se solucionará lo más pronto posible."
                    + "\nDisculpe las molestias");
        }

    }

    private static String introducirNombre(BufferedReader entradaDatos) {
        String nombre = null;
        try {
            System.out.println("Introduzca su nombre a continuación:");
            do {
                nombre = entradaDatos.readLine();
                //Poner la primera letra en mayúscula
                nombre = String.format("%s%s", nombre.substring(0, 1).toUpperCase(),
                        nombre.substring(1));
            } while (nombre == null || !CuentaBancaria.validarNombre(nombre));
        } catch (IOException e) {
            System.out.println("Se ha producido un error en la entrada de datos");
        }
        return nombre;
    }

    private static void ingresarDinero(BufferedReader entradaDatos, CuentaBancaria cuentaBancaria) {
        boolean volver;
        System.out.println("Teclee el importe que desee ingresar: ");
        double importeIngresar;
        do {
            try {
                volver = false;
                importeIngresar = Double.parseDouble(entradaDatos.readLine());

                cuentaBancaria.ingresarDinero(importeIngresar);
            } catch (InputMismatchException e) {
                System.out.println("El importe introducido no es válido introduzca un número de 0 a 10.000€");
                volver = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Introduzca un importe de 0 a 10.000€");
                volver = true;
            } catch (IOException e) {
                System.out.println("Se ha producido un error al ingresar el importe, intentelo de nuevo más tarde. Disculpe las molestias");
                volver = false;
            }
        } while (volver);

        System.out.printf("\nSu saldo actual es: %s\n", cuentaBancaria.getSaldo());
    }

    private static void retirarDinero(BufferedReader entradaDatos, CuentaBancaria cuentaBancaria) {
        boolean volver;
        System.out.println("Teclee el importe que desea retirar");
        do {
            try {
                volver = false;
                double importeRetirar = Double.parseDouble(entradaDatos.readLine());
                cuentaBancaria.retirarDinero(importeRetirar);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                volver = true;
            } catch (IOException e) {
                System.out.println("Se ha producido un error al ingresar el importe, intentelo de nuevo más tarde. Disculpe las molestias");
                volver = false;
            }
        } while (volver);
        System.out.printf("Su saldo actual es %s\n", cuentaBancaria.getSaldo());
    }
    
}
