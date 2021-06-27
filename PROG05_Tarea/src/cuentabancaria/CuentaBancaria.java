package cuentabancaria;

import java.util.*;

/**
 * Clase Cuenta Bancaria donde se definen los atributos del objeto, el
 * constructor y los métodos para trabajar con él.
 *
 * @author Víctor Visús
 *
 */
public class CuentaBancaria {

    /**
     * --- Atributos del Objeto CuentaBancaria ---
     */
    //Atributo nombre del titular
    private String nombreTitular;

    //Atributo que almacenará el saldo
    private double saldo;

    //Atributo número de cuenta
    private String codCompleto;

    //Defino las variables para almacenar los datos del número de la cuenta corriente
    private String codEntidad;
    private String codOficina;
    private String digControl;
    private String numCuenta;

    /**
     * --- Constructor del Objeto CuentaBancaria ---
     *
     * @param nombreTitular : Recibe el nombre del titular de la cuenta
     * @param codCompleto : Recibe el número de cuenta completo CCC
     *
     * @description Una vez que recibe los datos, llama al método cortarCuenta()
     * para mandar los datos a los atributos correspondientes a las distintas
     * partes del número de cuenta
     */
    public CuentaBancaria(String nombreTitular, String codCompleto) {
        this.nombreTitular = nombreTitular;
        this.codCompleto = codCompleto;

        cortarCodCuenta(codCompleto);
    }

    /**
     * --- Método que descompone el número de cuenta completo en sus diferentes
     * partes ---
     *
     * @param codCompleto : Recibe el número de cuenta completo
     *
     */
    private void cortarCodCuenta(String codCompleto) {
        codEntidad = codCompleto.substring(0, 4);
        codOficina = codCompleto.substring(4, 8);
        digControl = codCompleto.substring(8, 10);
        numCuenta = codCompleto.substring(10, 20);

        this.codCompleto = codCompleto.format("%s-%s-%s-%s", codEntidad, codOficina, digControl, numCuenta);
    }

    /**
     * --- Métodos get y set, para poder trabajar con los atributo ---
     */
    //get y set para el nombre del titular
    String getNombreTitular() {
        return nombreTitular;
    }

    void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    //get y set para el saldo de la cuenta
    double getSaldo() {
        return saldo;
    }

    void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //get y set para el CCC
    String getCodCompleto() {
        return codCompleto;
    }

    void setCodCompleto(String codCompleto) {
        this.codCompleto = codCompleto;
    }

    //Para los atributos de las distintas partes del número de la cuenta solo establezco el metodo get
    String getCodEntidad() {
        return codEntidad;
    }

    String getCodOficina() {
        return codOficina;
    }

    String getDigControl() {
        return digControl;
    }

    String getNumCuenta() {
        return numCuenta;
    }

    /* Fin de métodos get y set */
    /**
     * * Método toString para que nos muestre el estado del objeto **
     *
     * No esta en uso en la aplicación
     */
//    public String estadoCuenta() {
//        return "Titular de la cuenta: " + nombreTitular
//                + "\nCódigo cuenta corriente: " + codCompleto
//                + "\nCódigo de Entidad: " + codEntidad
//                + "\nCódigo de Oficina: " + codOficina
//                + "\nDígitos de control: " + digControl
//                + "\nNúmero de cuenta: " + numCuenta
//                + "\nSaldo actual: " + saldo;
//    }

    /**
     * -- Métodos estático para recibir y validar el nombre del titular de la
     * cuenta, antes de crear el objeto --
     */
    /**
     * Recibe el nombre del titular introducido por el usuario y lo envia a
     * validar al método nombreValidar, que comprueba que su longitud sea mayor
     * que 3 e inferior de 30 y que solo contenga caracteres alfabéticos.
     *
     * @param nombreTitular : String que recibe con el nombre introducido por
     * consola.
     *
     * @return error: envía un boleano true o false dependiendo de la validación
     * del nombre.
     *
     * @throws Exception : envía el mensaje de la excepción recogida en el
     * método validarNombre
     */
    static boolean recibeNombre(String nombreTitular) throws Exception {
        boolean error = true;
        //do {
            try {
                error = false;
                nombreValidar(nombreTitular);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                error = true;
            }
        //} while (error);
        return error;
//        return true;
    }

    /**
     * Recibe el nombre del titular, que comprueba que su longitud sea mayor que
     * 3 e inferior de 30 y que solo contenga caracteres alfabéticos.
     *
     * @param nombreTitular : String que recibe con el nombre introducido por
     * consola.
     *
     * @throws Exception : envía el error recogido.
     */
    private static void nombreValidar(String nombreTitular) throws Exception {

        if (nombreTitular.length() < 3 || nombreTitular.length() > 30) {
            throw new Exception("El nombre tiene que tener entre 3 y 30 caracteres.");
        }
        if (esTexto(nombreTitular)) {
            throw new Exception("El nombre solo puede tener caracteres alfabéticos.");
        }
    }

    /**
     * -- Método estático de la clase para validar el numero de cuenta --
     *
     * Método estático, para poder realizar la operación de "validar" el número
     * de cuenta sin haber creado el objeto
     *
     * @param codCompleto : Recibe el número de cuenta introducido por teclado
     * @return valida : Devuelve true, si el String mide 20 caracteres, o false
     * si no es correcto
     *
     * @anotacion Lo único que hace esta validación es comprobar que el String
     * recibido tiene una longitud de 20 caracteres. -- No es lo que pide el
     * ejercicio --
     *
     * Hago una validación tan básica porque debo estudiar y entender como se
     * validan los números de cuenta antes de poder realizar la función.
     *
     */
    static boolean validarCCC(String codCompleto) {
        if (codCompleto.length() != 20) {
            System.out.println("El código de cuenta es demasiado corto.");
            return false;
        } else if (esNumero(codCompleto)) {
            System.out.println("El código de cuenta solo puede tener caracteres numéricos.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * * Método privado que comprueba que el String recibido solo contenga
     * caracteres numéricos **
     *
     * @param numero : Recibe el String recogido por consola para comprobar que
     * sea un número.
     *
     * Utiliza el método matches() del objeto de java String. Que devuelve true
     * o false dependiendo si el String coincide con la expresión regular
     * indicada.
     *
     * y el método equals comparando el parámetro con una cadena vacía.
     */
    private static boolean esNumero(String numero) {
        return ((numero.matches("[+-]?\\d*(\\.\\d+)?") || numero.equals("")) == false);
    }

    /**
     * * Método privado que comprueba que el String recibido solo contenga
     * caracteres alfabéticos **
     *
     * @param numero : Recibe el String recogido por consola para comprobar que
     * no contenga números. La acción la realiza del mismo modo que el método
     * esNumero de esta clase.
     */
    private static boolean esTexto(String textoValidar) {
        return (textoValidar.matches("[+-]?\\d*(\\.\\d+)?") != false);
    }

    /**
     * --- Métodos públicos para operar con el objeto ---
     */
    /**
     * * Método que aumenta el saldo de la cuenta con el importe recibido en el
     * parámetro **
     *
     * @param efectivo : Recibe el importe (int) en el que se desea incrementar
     * el saldo de la cuenta, modificando el atributo saldo
     *
     * @throws Exception : Devuelve un error si el importe introducido es
     * negativo.
     */
    public void ingreso(double efectivo) throws Exception {
        if (efectivo < 0) {
            throw new Exception("El importe introducido no es correcto.");
        }
        this.saldo += efectivo;
        System.out.println("El saldo después de esta operación de la cuenta es: "
                + this.getSaldo());
    }

    /**
     * * Método que decrementa el saldo de la cuenta con el importe recibido en
     * el parámetro **
     *
     * @param efectivo : Recibe el importe (int) que se desea retirar del saldo
     * de la cuenta, modificando el atributo saldo
     *
     * @throws Exception(1) : Devuelve un error si el importe es superior al
     * existente en la cuenta.
     * @throws Exception(2) : Devuelve un error si el importe introducido es
     * negativo.
     */
    public void retiradaEfectivo(double efectivo) throws Exception {
        if (efectivo > this.saldo) {
            throw new Exception("Saldo insuficiente\n"
                    + "El saldo del que dispone en su cuenta es de: " + this.saldo);
        } else if (efectivo < 0) {
            throw new Exception("El importe introducido no puede ser negativo.");
        } else {
            this.saldo -= efectivo;
            System.out.println("El saldo después de esta operación de la cuenta es: "
                    + this.getSaldo());
        }
    }
}
