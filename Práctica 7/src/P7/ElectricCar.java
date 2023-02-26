package P7;

import java.io.Serializable;

public class ElectricCar extends Car implements Serializable {

	private static final long serialVersionUID = 2314952153963792566L;
	
	//Atributos de instancia
	private int electricPower;
	private float batteryCharge;


	//Atributos estaticos
	public static final float MIN_BATTERY_CHARGE = 0.0F;
	public static final float MAX_BATTERY_CHARGE = 100.0F;
	public static int MIN_ELECTRIC_POWER = 50;
	public static int MAX_ELECTRIC_POWER = 800;
	public static int BATTERY_CAPACITY = 100;


	//Constructor
	public ElectricCar (String plate, String manufacturer, int electricPower, float batteryCharge) {
		super(plate, manufacturer);
		this.electricPower = electricPower;
		this.batteryCharge = batteryCharge;
	}


	//Metodos estaticos

	//Comprobamos que la bateria este entre los valores correctos
	public static boolean isValidBatteryCharge (float batteryCharge) {
		if ((batteryCharge < MIN_BATTERY_CHARGE) || (batteryCharge > MAX_BATTERY_CHARGE)) {
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


	//Metodos de instancia

	//Metodo que incrementa la bateria de un coche segun el tiempo que haya estado en el parking
	public void increaseBatteryChargeLevel (float chargeTime) {

		this.batteryCharge = this.batteryCharge + (chargeTime * ElectricCharger.POWER / BATTERY_CAPACITY) * 100;

		//La bateria no puede ser mayor del cien por ciento
		if (batteryCharge > 100)
			batteryCharge = 100;
	}


	//Metodo que nos devuelve la potencia total de un coche electrico
	public int getTotalPower() {
		return electricPower;
	}


	//Metodo toString de coches electricos
	public String toString () {	
		return "E;" + super.toString() + this.electricPower + ";" + Float.toString(this.batteryCharge).replace(".", ",");
	}


	//Getters y Setters
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