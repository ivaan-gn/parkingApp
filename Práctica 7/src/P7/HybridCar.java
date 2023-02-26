package P7;

import java.io.Serializable;

public class HybridCar extends Car implements Serializable {

	private static final long serialVersionUID = 7558906366093469276L;
	
	//Atributos de instancia
	private int mechanicalPower;
	private int electricPower;
	private float batteryCharge;

	//Atributos estaticos
	public static final float MIN_BATTERY_CHARGE = 0.0F;
	public static final float MAX_BATTERY_CHARGE = 100.0F;
	public static int MIN_MECHANICAL_POWER = 60;
	public static int MAX_MECHANICAL_POWER = 500;
	public static int MIN_ELECTRIC_POWER = 50; // Estos no son constantes porque tiene sentido que varien a lo largo del prog
	public static int MAX_ELECTRIC_POWER = 800;
	public static int BATTERY_CAPACITY = 15;


	//Constructor
	public HybridCar (String plate, String manufacturer, int mechanicalPower, int electricPower, float batteryCharge) {
		super(plate, manufacturer);
		this.mechanicalPower = mechanicalPower;
		this.electricPower = electricPower;
		this.batteryCharge = batteryCharge;
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


	//Comprobamos que la potencia electrica este entre los valores correctos
	public static boolean isValidElectricPower (int electricPower) {
		if ((electricPower < MIN_ELECTRIC_POWER) || (electricPower > MAX_ELECTRIC_POWER)) {
			return false;
		}
		else {
			return true;
		}
	}


	//Comprobamos que la bateria este entre los valores correctos
	public static boolean isValidBatteryCharge (float batteryCharge) {
		if ((batteryCharge < MIN_BATTERY_CHARGE) || (batteryCharge > MAX_BATTERY_CHARGE)) {
			return false;
		}
		else {
			return true;
		}
	}


	//Metodos de instancia

	//Metodo que nos devuelve la potencia total de un coche hibrido
	public int getTotalPower() {
		return (electricPower + mechanicalPower);
	}


	//Metodo que incrementa la bateria de un coche en el tanto por ciento que le indiquemos
	public void increaseBatteryChargeLevel (float chargeTime) {

		this.batteryCharge = this.batteryCharge + (chargeTime * ElectricCharger.POWER / BATTERY_CAPACITY) * 100;

		//La bateria no puede ser mayor del cien por ciento
		if (batteryCharge > 100)
			batteryCharge = 100;
	}


	//Metodo toString de coches hibridos
	public String toString () {	
		return "H;" + super.toString() + this.mechanicalPower + ";" + this.electricPower + ";" + Float.toString(this.batteryCharge).replace(".", ",");
	}


	//Getters y Setters
	public int getMechanicalPower() {
		return mechanicalPower;
	}

	public void setMechanicalPower(int mechanicalPower) {
		this.mechanicalPower = mechanicalPower;
	}

	public int getElectricPower() {
		return electricPower;
	}

	public void setElectricPower(int electricPower) {
		this.electricPower = electricPower;
	}

	public float getBatteryCharge() {
		return batteryCharge;
	}

	public void setBatteryCharge(float batteryCharge) {
		this.batteryCharge = batteryCharge;
	}

}