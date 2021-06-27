package aplicacioncuentabancaria;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase Cuenta Bancaria para gestionar todos los datos necesarios para la cuenta bancaria del cliente
 * @author Elena
 */
public class CuentaBancaria {
    
    //datos del cliente de la cuenta
	private String nombreTitular;
	private String dni;

	//Variables para almacenar los números de la cuenta corriente
	private String codigoEntidad;
	private String codigoOficina;
	private String digitoControl;
	private String numeroCuenta;

	//Saldo del cliente en la cuenta
	private double saldo;

	//String que contiene las posibles letras que pueden aparecer en el DNI
	private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

	//Lista de factores por los que multiplicar para calcular el dígito de control de la cuenta bancaria
	private static final List<Integer> FACTORES = Arrays.asList(1, 2, 4, 8, 5, 10, 9, 7, 3, 6);

	private final int IMPORTE_MINIMO = 10;
	private final int IMPORTE_MAXIMO = 1000;

	//Constructor con todos los parámetros
	private CuentaBancaria(String nombreTitular, String dni,
						   String codigoEntidad, String codigoOficina,
						   String digitoControl, String numeroCuenta) {
		this.nombreTitular = nombreTitular;
		this.dni = dni;
		this.codigoEntidad = codigoEntidad;
		this.codigoOficina = codigoOficina;
		this.digitoControl = digitoControl;
		this.numeroCuenta = numeroCuenta;
	}
	/*
	Todos los métodos tienen acceso package porque solo van a ser usados dentro de este paquete
	 */

	/**
	 * Constructor para construir una cuenta bancaria solo con el nombre, dni y numero de cuenta.
	 * Llamará al constructor que tiene la cuenta bancaria separada en partes
	 *
	 * @param titular      nombre del titular de la cuenta
	 * @param dni          DNI del titular de la cuenta
	 * @param numeroCuenta número de cuenta completo
	 */
	CuentaBancaria(String titular, String dni, String numeroCuenta) {
		this(titular, dni, numeroCuenta.substring(0, 4),
			 numeroCuenta.substring(4, 8), numeroCuenta.substring(8, 10),
			 numeroCuenta.substring(10, 20));
		if (titular == null || dni == null) {
			throw new NullPointerException("Para crear una cuenta debe introducir el titular, el dni y el número de cuenta");
		}

	}

	/*
	Getters de cada una de las partes de la cuenta bancaria
	 */
	String getCodigoEntidad() {
		return codigoEntidad;
	}

	String getCodigoOficina() {
		return codigoOficina;
	}

	String getDigitoControl() {
		return digitoControl;
	}

	String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Método que construye la cuenta bancaria formateada a partir de todos los códigos que la forman: código entidad,
	 * código oficina, el dígito de control y el número de cuenta
	 *
	 * @return número de cuenta completo separado por guiones para que sea más fácil de ver
	 */
	String getCCC() {
		return String.format("%s-%s-%s-%s", codigoEntidad, codigoOficina, digitoControl, numeroCuenta);
	}

	//Ges para conseguir el nombre del titular
	String getNombreTitular() {
		return nombreTitular;
	}

	//Consultar el saldo disponible
	double getSaldo() {
		return saldo;
	}

	/*---------------------------------Métodos para ingresar y retirar dinero--------------------------------------------------*/

	/**
	 * Método para ingresar dinero teniendo en cuenta las posibles excepciones, como que no puede ingresar 0 euros,
	 * el importe no puede ser negativo, ni la cantidad superior a 10.000 € para asemejarse a un banco real
	 *
	 * @param dinero cantidad que desea retirar
	 */
	void ingresarDinero(double dinero) {
		if (dinero == 0) {
			throw new IllegalArgumentException("El importe a ingresar no puede ser cero");
		}
		if (dinero < 0) {
			throw new IllegalArgumentException("El importe a ingresar no puede ser negativo");
		}
		if (dinero > 10000) {
			throw new IllegalArgumentException("No se puede ingresar un importe superior a 10.000 euros. \nPara realizar la" +
											   "transacción acuda a su oficina más cercana. \n Disculpe las molestias");
		}
		this.saldo += dinero;
	}

	/**
	 * Método para retirar dinero teniendo en cuenta las posibles excepciones como que el importe no puede ser negativo o cero, este importe no puede
	 * ser superior a su saldo actual, el importe mínimo deben ser 10 euros, el importe máximo son 1.000 euros y si se trata de un múltiplo de 10
	 * porque muchos cajeros automáticos no disponen de monedas ni billetes de 5
	 *
	 * @param dinero importe que quiere retirar
	 */
	void retirarDinero(double dinero) {
		if (dinero > this.saldo) {
			throw new IllegalArgumentException(String.format("El importe %s que desea retirar es superior a su saldo: %s.", dinero, this.saldo));
		}
		if (dinero <= 0) {
			throw new IllegalArgumentException(String.format("El importe mínimo para retirar es %s €", IMPORTE_MINIMO));
		}
		if (dinero > 1000) {
			throw new IllegalArgumentException(String.format("El importe que desea retirar excede del importe máximo %s €. \n" +
											   "Si desea retirar más dinero espere a mañana o acuda a su oficina más cercana." +
											   "\nDisculpe las molestias", IMPORTE_MAXIMO));
		}
		if (dinero < 10) {
			throw new IllegalArgumentException("El importe mínimo a retirar son 10€. Introduzca el importe otra vez\n");
		}
		if (dinero % 10 != 0){
			throw new IllegalArgumentException("El importe a retirar debe ser múltiplo de 10");
		}
			this.saldo -= dinero;
	}

	/*----------------------Métodos para validar nombre, DNI y cuenta bancaria---------------------------------------------------------------------------------------*/

	/**
	 * Método para validar que el nombre del titular está entre 3 y 100 caracteres
	 *
	 * @param nombreTitular nombre que se quiere validar
	 * @return un booleano que devuelve true si el nombre es válido y false si no lo es
	 */
	static boolean validarNombre(String nombreTitular) {
		if (nombreTitular.length() < 3 || nombreTitular.length() > 100) {
			System.out.println("El nombre del titular debe estar entre 3 y 100 letras. Introduzca el nombre de nuevo");
			return false;
		}
		return true;
	}

	/**
	 * Método para validar el DNI. Primero se hace un trim para quitar los posibles espacios al introducir el DNI y lo pone en mayúsculas
	 * para guardarlo con el formato correcto. A continuación comprueba si tiene más de 9 dígitos, si es así a través de la librería regex que esté formado por
	 * 8 números y una letra. Si cumple todos los requisitos anteriores se calcula la letra del DNI que consiste en coger los 8 números del DNI
	 * dividirlo entre 23, quedarnos con el resto de esa división que será la posición de la letra del DNI. El array de letras lo podemos encontrar
	 * en la página del ministerio del interior  @see <a href="http://www.interior.gob.es/web/servicios-al-ciudadano/dni/calculo-del-digito-de-control-del-nif-nie"></a>
	 *
	 * @param dni El DNI completo que queremos comprobar
	 */
	static boolean validarDni(String dni) {
		dni = dni.trim().toUpperCase();
		if (dni.length() != 9) {
			System.out.println("El DNI tiene 8 números y 1 letra. Introduzca el DNI de nuevo");
			return false;
		}
		if (!Pattern.compile("^[\\d]{8}[\\w]$")
					.matcher(dni).find()) {
			System.out.println("El DNI no tiene el formato adecuado. Introduzca el DNI de nuevo");
			return false;
		}
		String letraCalculada = String.valueOf(LETRAS_DNI.charAt(Integer.valueOf(dni.substring(0, 8)) % 23));
		if (!letraCalculada.equals(dni.substring(8, 9))) {
			throw new IllegalArgumentException("El DNI introducido no es válido");
		}
		return true;
	}

	/**
	 * Método para validar una cuenta bancaria. En primer lugar "limpiamos" la cuenta bancaria que ha introducido el cliente quitando los posibles
	 * espacios o guiones que pueda tener. Seguidamente vemos si tiene 20 dígitos, si no es así se lanza una excepción, Seguidamente se comprueba que
	 * lo introducido son solo números lanzando excepción si contiene algún caracter que no sea un número.
	 * <p>
	 * Página para crear cuentas bancarias: @see <a href="http://www.genware.es/index.php?ver=cuentasbancarias#"></a>
	 * Ejemplo cuanta valida: 4651 0444 26 4435436057
	 * - Número usado para calcular el dígito 1: 0046510444
	 * - sumatorio 1*  = 163
	 * - resto = 9
	 * - Digito 1 = 11 - 9 = 2
	 * -------------------------
	 * - Número usado para calcular el dígito 2: 4435436057
	 * - Sumatorio 2* = 225
	 * - resto = 5
	 * - Digito 2  = 11 - 5 = 6
	 * Ambos sumatorios son la combinación lineal de cada número multiplicado por su factor
	 *
	 * @param cuentaBancaria cuenta bancaria que se quiere validar
	 * @return devuelve un booleano con true si la cuenta es válida y false si no lo es
	 */
	static boolean validarCuentaBancaria(String cuentaBancaria) {
		if(cuentaBancaria.isEmpty()){
			throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
		}
		if (cuentaBancaria.length() != 20) {
			throw new IllegalArgumentException("La cuenta bancaria debe tener 20 números");
		}
		//Comprobar que la cuenta bancaria no contiene otra cosa que no sea un número
		if (!Pattern.compile("^\\d").matcher(cuentaBancaria).find()) {
			throw new IllegalArgumentException("La cuenta bancaria solo puede contener números");
		}
		String digitoControlCalculado;
		//Se captura Exception al calcular el dígito de control para que el cliente no vea la traza de cualquier posible excepción
		try {
			String entidadOficina = "00" + cuentaBancaria.substring(0, 8);
			digitoControlCalculado = calcularDigitoControl(entidadOficina);
			String numeroCuenta = cuentaBancaria.substring(10, 20);
			digitoControlCalculado += calcularDigitoControl(numeroCuenta);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un fallo en el sistema al validar la cuenta, intentelo de nuevo más tarde." +
							   " \n Disculpe las molestias.");
			throw e;

		}
		return digitoControlCalculado.equals(cuentaBancaria.substring(8, 10));
	}

	/**
	 * Método para calcular el dígito de control de una cuenta bancaria. El algoritmo para calcularlo es multiplicar
	 * cada dígito por su factor para obtener un sumatorio. Posteriormente,  el sumatorio se divide entre 11 y nos quedamos con el resto.
	 * Si el resto es 10 el dígito de control pasaría a ser 1, en cambio, si es diferente de 11 se restaría 11 menos el resto y ya obtendríamos el primer digito de control.
	 * Para calcular el segundo dígito la mecánica es la misma.
	 *
	 * @param cuentaBancaria cuenta bancaria de la que se quiere calcular los dígitos de control
	 * @return devuelve los dígitos de control para una cuenta bancaria dada
	 */
	private static String calcularDigitoControl(String cuentaBancaria) {

		int sumatorio = 0;
		for (int i = 0; i < cuentaBancaria.length(); i++) {
			sumatorio += FACTORES.get(i) * Integer.valueOf(cuentaBancaria.substring(i, i + 1));
		}
		int resto = sumatorio % 11;
		int digitoControl;
		if (resto == 10) {
			digitoControl = 1;
		} else {
			digitoControl = 11 - resto;
		}
		return String.valueOf(digitoControl);
	}

}
