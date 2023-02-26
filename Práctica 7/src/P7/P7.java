package P7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class P7 {

	//Creamos un objeto de la clase CarDB
	public static CarDB cdb = new CarDB();
	//Declaramos el parking como una variable global
	public static Parking parking;

	public static void main(String[] args) throws FileNotFoundException {

		//Declaramos los ficheros que reciben su nombre por argumentos
		String file1 = args[0];
		String file2 = args[1];
		String file3 = args[2];
		String file4 = args[3];
		String file5 = args[4];
		String file6 = args[5];
		String file7 = args[6];


		//Usamos su metodo readCityCarsFile para leer la informacion del fichero cityCars.txt y guardarla en el ArrayList cityCars
		try {
			cdb.readCityCarsFile(file4);
			//Capturamos la posible excepcion de que no se encuentre el fichero que recibe con los coches existentes
		} catch (PIIFileArgumentNotFound e) {
			e.printStackTrace();
		}

		//Creamos un parking a traves del primer fichero
		parking = new Parking(file1);

		//Actualizamos el parking a traves del metodo processIO
		P7.processIO(file2);

		//Guardamos el estado del parking en el fichero 3
		parking.saveParking(file3);

		//Ordenamos la coleccion cityCars por bateria y matricula usando el metodo sortByBatteryChargeAndPlate()
		cdb.sortByBatteryChargeAndPlate();

		//Guardamos la coleccion cityCars en el fichero 5 invocando al metodo saveCarsToFile
		cdb.saveCarsToFile(file5);

		//Guardamos los objetos Car de cityCars en el fichero 6 llamando al metodo saveToBinary()
		cdb.saveToBinary(file6);

		//Guardamos la lista de potencias de los coches de cityCars en el fichero 7 llamando al metodo savePowersToBinary()
		cdb.savePowersToBinary(file7);


	}//Cerramos main


	//Metodo que lee el fichero de entradas/salidas y actualiza el parking
	public static void processIO (String fileName) throws FileNotFoundException {

		//Leemos el fichero, que contiene entradas y salidas, para actualizar el parking
		File fd = new File(fileName);
		Scanner input = new Scanner (fd);
		String linea;
		String[] arrayLineaSeparada;

		//Recorremos el fichero descartando comentarios
		while (input.hasNext()) {
			linea = input.nextLine();
			if(linea.startsWith("#")) continue;

			//Una vez leemos una linea valida, la separamos y almacenamos en un array
			arrayLineaSeparada = linea.split(";");

			//Comprobamos si es entrada o salida y ejecutamos el metodo correspondiente
			switch(arrayLineaSeparada[0].charAt(0)) {

			case 'I':
				parking.carEntry(cdb.getCarFromPlate(arrayLineaSeparada[1]), arrayLineaSeparada[2]);
				break;
			case 'O':
				String horaEntrada = parking.carDeparture(arrayLineaSeparada[1]);				
				//Al salir un coche, actualizamos su nivel de bateria
				cdb.increaseCarBatteryChargeLevel(arrayLineaSeparada[1], horaEntrada, arrayLineaSeparada[2]);				
				break;
			}
		}
		input.close();
	}

}
