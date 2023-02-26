package P7;

import java.io.Serializable;

public class CombustionCar extends Car implements Serializable {

	private static final long serialVersionUID = 2650292707495938005L;
	
	//Atributos de instancia
	private int mechanicalPower;


	//Atributos estaticos
	public static int MIN_MECHANICAL_POWER = 60;
	public static int MAX_MECHANICAL_POWER = 500;


	//Constructor
	public CombustionCar (String plate, String manufacturer, int mechanicalPower) {
		super(plate, manufacturer);
		this.mechanicalPower = mechanicalPower;
	}


	//Metodos estaticos

	//Comprobamos que la potencia mecanica este entre los valores correctos
	public static boolean isValidMechanicalPower (int mechanicalPower) {
		if ((mechanicalPower < MIN_MECHANICAL_POWER) || (mechanicalPower > MAX_MECHANICAL_POWER)) {
			return false;
		}
		else {
			return true;
		}
	}


	//Metodos de instancia

	//Metodo toString de coches de combustion
	public String toString () {	
		return "C;" + super.toString() + this.mechanicalPower;
	}


	//Metodo que nos devuelve la potencia total de un coche de combustion
	public int getTotalPower() {
		return mechanicalPower;
	}


	//Getters y Setters
	public int getMechanicalPower() {
		return mechanicalPower;
	}

	public void setMechanicalPower(int mechanicalPower) {
		this.mechanicalPower = mechanicalPower;
	}

}