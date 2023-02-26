package P7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CarSpace implements Comparable<CarSpace> {

	//Atributos de instancia
	private Coordinate coordinate;
	private String plate = null;
	private String entryTime;

	//Atributo para almacenar la foto de la matricula del coche que este en una plaza
	byte[] plateImage;


	//Constructores
	CarSpace(){
	}

	CarSpace(Coordinate coordinate, String plate, String entryTime){
		this.coordinate = coordinate;
		this.plate = plate;
		this.entryTime = entryTime;
	}


	//Metodos de instancia

	//Metodo toString de una coordinada
	public String toString () {
		if (plate == null) {
			return coordinate.toString();
		}
		else {
			return coordinate.toString() + ";" + this.plate + ";" + this.entryTime;
		}
	}


	//Metodo compareTo que compara dos CarSpaces segun sus coordenadas
	public int compareTo(CarSpace cs2) {
		return this.coordinate.compareTo(cs2.coordinate);
	}


	//Metodo hashCode
	public int hashCode () {
		return 0;
	}


	//Metodo equals (dos CarSpaces son iguales si sus coordenadas coinciden
	public boolean equals (Object o) {	
		return this.coordinate.equals(((CarSpace)o).coordinate);
	}


	//Metodo para leer un fichero que contiene una foto de una matricula
	public boolean readPlateImage(String fileName) {

		File fd = new File(fileName);

		//El array de bytes tendra el tamano del fichero
		this.plateImage = new byte[(int)fd.length()];

		try {
			FileInputStream fis = new FileInputStream(fileName);

			//Leemos los bytes uno a uno y cuando lo logremos, devolvemos un true
			int num = fis.read(this.plateImage); 
			fis.close();

			//Comprobamos que todo ha ido bien para devolver el true
			if (num == -1) {
				System.out.println("Ha habido un error leyendo la foto de las matricula. No hay foto");
				return false;
			}
			if (num != fd.length()) {
				System.out.println("Ha habido un error leyendo la foto de las matricula. No se corresponde con el tamano del array");
				System.out.println("He leido " + num + " y esperaba leer " + fd.length());
				return false;
			}
			return true;

			//En caso de producirse alguna excepcion, devolvemos un false indicando que hubo algun problema
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	//Metodo que almacena en un fichero la foto de la matricula
	public boolean savePlateImage(String fileName) {

		File fd = new File(fileName);

		try {
			FileOutputStream fos = new FileOutputStream(fd);

			//Escribimos la foto en el fichero y cuando lo logremos, devolvemos un true 
			fos.write(this.plateImage);
			fos.close();
			return true;

			//Capturamos las posibles excepciones que se puedan producir
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	//Getters y Setters
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

}