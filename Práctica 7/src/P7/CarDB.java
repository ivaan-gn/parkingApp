package P7;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class CarDB implements PIIDigitizable{

	//Atributos
	private LinkedHashMap<String, Car> cityCars;

	//Constructor
	CarDB(){
		this.cityCars = new LinkedHashMap<String, Car>();
	}

	//Metodos
	//Metodo para leer coches de un fichero
	public void readCityCarsFile (String fileName) throws PIIFileArgumentNotFound {

		String linea;
		String[] arrayLineaSeparada;
		ElectricCar electricCar;
		CombustionCar combustionCar;
		HybridCar hybridCar;
		boolean error = false;

		//Creamos el fichero y el Scanner para leerlo
		File fd = new File(fileName);

		Scanner input;
		try {
			input = new Scanner (fd);
		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero .txt, buscamos el .dat");
			throw new PIIFileArgumentNotFound(fileName);
		}

		//Creamos un bucle para recorrer todo el fichero, cada vez que se ejecuta un continue, volvemos al principio del bucle
		while (input.hasNext()) {
			error = false;
			linea = input.nextLine();
			//Descartamos las lineas que sean comentarios
			if(linea.startsWith("#")) continue;

			//Almacenamos en un array la linea separada
			arrayLineaSeparada = linea.split(";");

			//Averiguamos de que tipo de coche se trata
			//Comprobamos si es de combustion
			if (arrayLineaSeparada[0].equals("C")) {

				//Si es de combustion, comprobamos que cumpla los formatos correctos
				if (CombustionCar.isValidMechanicalPower(Integer.parseInt(arrayLineaSeparada[3])) == false)
					error = true;
				if (CombustionCar.isValidPlate(arrayLineaSeparada[1]) == false)
					error = true;

				//Si existe alguno de los errores, salimos y volvemos al principio del bucle
				if (error == true) continue;

				//Creamos un nuevo coche de combustion si todo es correcto y volvemos al principio del bucle
				combustionCar = new CombustionCar(arrayLineaSeparada[1], arrayLineaSeparada[2], Integer.parseInt(arrayLineaSeparada[3]));
				cityCars.put(arrayLineaSeparada[1], combustionCar);
				continue;
			}

			//Comprobamos si es electrico
			if (arrayLineaSeparada[0].equals("E")) {

				//Si es electrico comprobamos que cumpla los formatos correctos
				if (ElectricCar.isValidBatteryCharge(Float.parseFloat(arrayLineaSeparada[4].replace(",", "."))) == false)
					error = true;
				if (ElectricCar.isValidElectricPower(Integer.parseInt(arrayLineaSeparada[3])) == false)
					error = true;
				if (ElectricCar.isValidPlate(arrayLineaSeparada[1]) == false)
					error = true;

				//Si existe alguno de los errores, salimos y volvemos al principio del bucle
				if (error == true) continue;

				//Creamos un nuevo coche de electrico si todo es correcto y volvemos al principio del bucle
				electricCar = new ElectricCar(arrayLineaSeparada[1], arrayLineaSeparada[2], Integer.parseInt(arrayLineaSeparada[3]), Float.parseFloat(arrayLineaSeparada[4].replace(",", ".")));
				cityCars.put(arrayLineaSeparada[1], electricCar);
				continue;
			}

			//Comprobamos si es hibrido
			if (arrayLineaSeparada[0].equals("H")) {

				//Si es hibrido comprobamos que cumpla los formatos correctos
				if (HybridCar.isValidBatteryCharge(Float.parseFloat(arrayLineaSeparada[5].replaceAll(",", "."))) == false)
					error = true;
				if (HybridCar.isValidMechanicalPower(Integer.parseInt(arrayLineaSeparada[3])) == false)
					error = true;
				if (HybridCar.isValidElectricPower(Integer.parseInt(arrayLineaSeparada[4])) == false)
					error = true;
				if (HybridCar.isValidPlate(arrayLineaSeparada[1]) == false)
					error = true;

				//Si existe alguno de los errores, salimos y volvemos al principio del bucle
				if (error == true) continue;

				//Creamos un nuevo coche de electrico si todo es correcto y volvemos al principio del bucle
				hybridCar = new HybridCar(arrayLineaSeparada[1], arrayLineaSeparada[2], Integer.parseInt(arrayLineaSeparada[3]), Integer.parseInt(arrayLineaSeparada[4]), Float.parseFloat(arrayLineaSeparada[5].replaceAll(",", ".")));
				cityCars.put(arrayLineaSeparada[1], hybridCar);
				continue;
			}

		} //Cerramos bucle while
		input.close();

	} //Cerramos metodo readCityCarsFile


	//Metodo que recibe una matricula y devuelve el objeto Car del mapa cityCars correspondiente a esa matricula
	public Car getCarFromPlate (String plate) {
		return cityCars.get(plate);
	}


	//Metodo que calcula la potencia total de todos los coches
	public int computeTotalPower() {
		int resultado = 0;

		//Recorremos la coleccion de coches que nos da el metodo values del mapa actualizando el resultado en cada iteracion del bucle
		for (Car c: cityCars.values())
			resultado = resultado + c.getTotalPower();

		return resultado;
	}


	//Metodo que calcula y devuelve el nivel de bateria medio de todos los coches electricos e hibridos
	public float computeAverageBatteryLevel() {
		float resultado = 0;
		int contadorCoches = 0;

		//Recorremos la coleccion de coches que nos da el metodo values del mapa buscando los coches electricos e hibridos actualizando el resto en cada iteracion
		for (Car c: cityCars.values()) {

			if ((c instanceof ElectricCar)) {
				resultado  = resultado + ((ElectricCar)c).getBatteryCharge();
				contadorCoches++;
				continue;
			}

			if ((c instanceof HybridCar)) {
				resultado  = resultado + ((HybridCar)c).getBatteryCharge();
				contadorCoches++;
				continue;
			}
		}
		//Cuando tenemos el nivel de bateria total lo dividimos entre el numero de coches de los que lo hemos calculado para obtener el resultado final
		resultado = resultado/contadorCoches;

		return resultado;
	}


	//Metodo para guardar en un fichero los coches del mapa cityCars
	public void saveCarsToFile (String filename) throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(new File(filename));

		//Recorremos la coleccion de objetos Car que nos da el metodo values imprimiendo cada coche en el fichero
		for (Car c: cityCars.values())
			pw.println((c.toString()));

		pw.close();	
	}


	//Metodo para actualizar el nivel de la bateria de uno de los coche almacenados en el fichero cityCars
	public void increaseCarBatteryChargeLevel (String plate, String entryTime, String departureTime) {

		//Pedimos al metodo getCarFromPlate que nos de el coche corresponiente a la matricula indicada
		Car c = getCarFromPlate(plate);

		//Calculamos el tiempo entre la entrada y la salida del coche usando el metodo intervalInHours
		float tiempoInOut = intervalInHours(entryTime, departureTime);

		//Si el coche es hibrido o electrico, calculamos el incremento de bateria
		if (c instanceof ElectricCar)
			((ElectricCar)c).increaseBatteryChargeLevel(tiempoInOut);
		if (c instanceof HybridCar)
			((HybridCar)c).increaseBatteryChargeLevel(tiempoInOut);
	}


	//Metodo que calcula el tiempo, en horas, entre la hora de entrada y salida de un coche
	private float intervalInHours (String inTime, String outTime) {
		int hi = Integer.parseInt(inTime.split(":")[0].trim());
		int mi = Integer.parseInt(inTime.split(":")[1].trim());
		int ho = Integer.parseInt(outTime.split(":")[0].trim());
		int mo = Integer.parseInt(outTime.split(":")[1].trim());

		int dif = (ho*60+mo)-(hi*60+mi);
		return ((float)dif/60);
	}


	//Metodo para ordenar la coleccion cityCars por matricula
	public void sortByPlate () {
		//Creamos un ArrayList temporal de coches poblandolo con los objetos Car del mapa
		ArrayList<Car> arrayListCityCars = new ArrayList<Car>(cityCars.values());

		//Este uso de sort nos ordena los coches segun el criterio dado en el compareTo de la interfaz Comparable, es decir, por matricula en orden ascendente
		Collections.sort(arrayListCityCars);

		//Borramos todo el contenido del mapa
		cityCars.clear();

		//Volvemos a poblar el mapa a traves del arrayList ordenado
		for (Car c: arrayListCityCars)
			cityCars.put(c.getPlate(), c);
	}


	//Metodo para ordenar la coleccion cityCars por su nivel de bateria y matricula
	public void sortByBatteryChargeAndPlate () {
		//Creamos un ArrayList temporal de coches poblandolo con los objetos Car del mapa
		ArrayList<Car> arrayListCityCars = new ArrayList<Car>(cityCars.values());

		//Este uso de sort nos ordena los coches segun el criterio dado en el compare de la interfaz Comparator, es decir, por nivel de bateria y matricula en orden ascendente
		Collections.sort(arrayListCityCars, new CarComparatorByBatteryLevelAndPlate());

		//Borramos todo el contenido del mapa
		cityCars.clear();

		//Volvemos a poblar el mapa a traves del arrayList ordenado
		for (Car c: arrayListCityCars)
			cityCars.put(c.getPlate(), c);
	}


	//Metodo que guarda en el fichero binario que recibe como parametro la secuencia de objetos Car almacenados en cityCars
	public boolean saveToBinary(String fileName) {

		//Capturamos las posibles excepciones
		try {
			//Usamos la clase ObjectOutputStream para guardar los objetos Car en el fichero
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
			//Recorremos la coleccion de cityCars guardando elemento a elemento cada Car, si lo conseguimos, devolvemos true
			for (Car c: cityCars.values())
				oos.writeObject(c);
			oos.close();
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


	//Metodo que lee del fichero binario que recibe como parametro una secuencia de objetos Car y los guarda en el atributo cityCars
	public boolean readBinaryCityCars(String fileName) {

		//Usamos la clase ObjectInputStream para leer los objetos Car del fichero
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			//Recorremos el fichero guardando elemento a elemento cada Car en cityCars
			while (true) {
				Car oneCar = (Car)ois.readObject();
				cityCars.put(oneCar.getPlate(), oneCar);
			}

			//En caso de producirse alguna excepcion, devolvemos un false indicando que hubo algun problema
			//Si se produce la EOFException es porque hemos leido el fichero, devolvemos true
		} catch (EOFException e) {
			System.out.println("Se han leido todos los coches");
			try {
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	//Metodo que guarda en un fichero la lista de potencias totales de cada coche
	public boolean savePowersToBinary(String fileName) {

		//Capturamos las posibles excepciones
		try {
			//Usamos la clase DataOutputStream para guardar los objetos Car en el fichero
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));
			//Recorremos la coleccion de cityCars guardando elemento a elemento la potencia de cada coche, si lo conseguimos, devolvemos true
			for (Car c: cityCars.values())
				dos.writeInt(c.getTotalPower());
			dos.close();
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


	//Metodo que lee el fichero binario que recibe como parametro y devuelve un array de bytes con su contenido
	public byte[] readBinaryFile (String filename) {

		File fd = new File(filename);

		//El array de bytes tendra el tamano del fichero
		byte[] arrayFicheroBinario = new byte[(int)fd.length()];

		try {
			FileInputStream fis = new FileInputStream(filename);

			//Leemos los bytes uno a uno y cuando lo logremos, devolvemos un true
			int num = fis.read(arrayFicheroBinario); 
			fis.close();
			//Comprobamos posibles errores con la lectura del fichero
			if (num == -1) {
				System.out.println("Ha habido un error leyendo el fichero. No hay fichero");
				return null;
			}
			if (num != fd.length()) {
				System.out.println("Ha habido un error leyendo el fichero. No se corresponde con el tamano del array");
				System.out.println("He leido " + num + " y esperaba leer " + fd.length());
				return null;
			}

			//Verificamos las posibles excepciones
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return arrayFicheroBinario;
	}


	//Metodo que recibe un array de bytes, lee en el una serie de objetos Car y los guarda en un TreeMap
	public TreeMap<String,Car> streamToMap (byte[] arrayByteCars) {

		//Declaro el TreeMap donde voy a guardar los objetos Car y los InputStream necesarios
		TreeMap<String,Car> carObjectMap = new TreeMap<String, Car>();
		ObjectInputStream ois;
		ByteArrayInputStream bais;

		//Usamos la clase ObjectInputStream para guardar los objetos Car en el TreeMap
		bais = new ByteArrayInputStream(arrayByteCars);
		try {
			ois = new ObjectInputStream(bais);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		//Leemos el array de bytes
		while (true) {
			Car oneCar;

			try {
				//Leemos cada Car y lo almacenamos en el TreeMap
				oneCar = (Car)ois.readObject();
				carObjectMap.put(oneCar.getPlate(), oneCar);
				//La EOFException nos indica que hemos llegado al final de la lectura
			} catch (EOFException e) {
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return carObjectMap;
	}


	//Metodo que guarda en tres ficheros diferentes objetos Car dependiendo de su tipo
	public boolean saveSeparatedCarTypes (TreeMap<String,Car> tm, String prefix) {

		//Capturamos las posibles excepciones
		try {
			//Usamos la clase ObjectOutputStream para guardar los objetos Car en los ficheros
			ObjectOutputStream oosC = new ObjectOutputStream(new FileOutputStream(prefix + "_cc.dat"));
			ObjectOutputStream oosE = new ObjectOutputStream(new FileOutputStream(prefix + "_ec.dat"));
			ObjectOutputStream oosH = new ObjectOutputStream(new FileOutputStream(prefix + "_hc.dat"));	

			//Recorremos el set de claves del mapa cityCars
			for (String keyCars: tm.keySet()) {
				//En cada iteracion obtenemos el coche a partir de su clave
				Car oneCar = tm.get(keyCars);
				//Comprobamos el tipo de coche que es para meterlo en el fichero correspondiente
				if (oneCar instanceof CombustionCar)
					oosC.writeObject(oneCar);
				if (oneCar instanceof ElectricCar)
					oosE.writeObject(oneCar);
				if (oneCar instanceof HybridCar)
					oosH.writeObject(oneCar);
			}
			//Una vez acabamos, cerramos todos los ficheros y devolvemos true
			oosC.close();
			oosE.close();
			oosH.close();
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

}