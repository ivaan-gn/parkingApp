package P7;

public class Coordinate implements Comparable<Coordinate>{

	//Atributos de instancia
	private char zone;
	private int number;


	//Constructores
	Coordinate(){
	}

	public Coordinate (char zone, int number) {
		this.zone = zone;
		this.number = number;
	}

	public Coordinate (String coord) {
		this.zone = coord.charAt(0);
		this.number = Integer.parseInt(coord.substring(1));
	}


	//Metodos de instancia

	//Metodo equals que comprueba si una coordenada es igual a otra
	public boolean equals (Object o) {
		Coordinate c = (Coordinate)o;
		if((this.zone == c.zone) && (this.number == c.number)){
			return true;
		}
		else {
			return false;
		}
	}

	//Metodo toString de una coordenada
	public String toString () {
		return zone + String.valueOf(number);
	}


	//Metodo compareTo para comparar dos coordenadas
	public int compareTo(Coordinate c2) {
		if (this.zone < c2.zone) return -1;
		if (this.zone > c2.zone) return 1;
		if (this.number < c2.number) return -1;
		if (this.number > c2.number) return 1;
		return 0;
	}


	//Getters y Setters
	public char getZone() {
		return zone;
	}

	public void setZone(char zone) {
		this.zone = zone;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}