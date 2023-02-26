package P7;

import java.io.Serializable;

public abstract class Car implements Comparable<Car>, Serializable{

	private static final long serialVersionUID = 7999008895737219541L;
	
	//Atributos

	//Atributos de instancia comunes a todos los coches
	protected String plate;
	protected String manufacturer;

	//Atributos estaticos comunes a todos los coches
	public static final String PLATE_FORMAT = "DDDDLLL";


	//Contructores

	//Constructor vacio
	public Car () {	
	}

	//Constructor con argumentos
	public Car (String plate, String manufacturer) {
		this.plate = plate;
		this.manufacturer = manufacturer;
	}


	//Metodos

	//Metodos estaticos
	//Comprobamos que la matricula siga el formato correcto
	public static boolean isValidPlate (String plate) {
		if((plate.length() == 7) && 
				(Character.isDigit(plate.charAt(0)) == true) && 
				(Character.isDigit(plate.charAt(1)) == true) && 
				(Character.isDigit(plate.charAt(2)) == true) &&
				(Character.isDigit(plate.charAt(3)) == true) &&
				(((plate.charAt(4)) >= 65 && (plate.charAt(4) <= 90)) == true) &&
				(((plate.charAt(5)) >= 65 && (plate.charAt(5) <= 90)) == true) &&
				(((plate.charAt(6)) >= 65 && (plate.charAt(6) <= 90)) == true)){
			return true;
		}
		else {
			return false;
		}
	}

	//Metodos abstractos
	public abstract int getTotalPower();


	//Metodos de instancia

	//Metodo toString de un coche 
	public String toString () {	
		return this.plate + ";" + this.manufacturer + ";";
	}

	//Metodo compareTo para comparar dos Car segun su matricula en orden ascendente
	public int compareTo(Car car2) {
		if (this.plate.compareTo(car2.plate) < 0) return -1;
		if (this.plate.compareTo(car2.plate) > 0) return 1;
		return 0;
	}


	//Getters y Setters
	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}