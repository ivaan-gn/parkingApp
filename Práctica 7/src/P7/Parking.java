package P7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Parking {

	//Atributos de instancia
	private char maxZone;
	private int sizeZone;
	private char lowerElectricZone;

	//Declaramos dos sets de plazas, uno ordenado para las libres y otro desordenado para ocupadas
	private HashSet<CarSpace> busyCarSpaces;
	private TreeSet<CarSpace> freeCarSpaces;


	//Constructor de un parking a traves de un fichero
	public Parking (String fileName) throws FileNotFoundException {

		//Declaramos los atributos necesarios
		String linea;
		String[] arrayLineaSeparada;
		int numFilas = 0;
		//Creo las colecciones de plazas, tanto las vacias como las ocupadas
		this.freeCarSpaces = new TreeSet<CarSpace>();
		this.busyCarSpaces = new HashSet<CarSpace>();

		//Creamos el fichero y el escaner para leerlo
		File fd = new File(fileName);
		Scanner input = new Scanner (fd);

		//Creamos un bucle para encontrar la primera linea del fichero, que nos dara la info de dimensionamiento del parking
		while (input.hasNext()) {
			linea = input.nextLine();
			//Descartamos las lineas que sean comentarios
			if(linea.startsWith("#")) continue;

			//Almacenamos en un array la linea separada
			arrayLineaSeparada = linea.split(";");

			//Asignamos cada componente del array a su atributo del parking
			this.maxZone = arrayLineaSeparada[0].charAt(0);
			numFilas = this.maxZone - 64;
			this.sizeZone = Integer.parseInt(arrayLineaSeparada[1]);
			this.lowerElectricZone = arrayLineaSeparada[2].charAt(0);
			break;
		} //Salimos del while tras obtener la informacion de dimensionado


		//Creamos todas las plazas (objetos CarSpace) segun las dimensiones del array
		int zonaElectricos = this.lowerElectricZone - 65;
		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < this.sizeZone; j++) {
				//Creamos las plazas vacias normales o para coches electricos/hibridos segun proceda
				if (i < zonaElectricos)
					this.freeCarSpaces.add(new CarSpace(new Coordinate((char)(i + 65), j + 1), null, null));
				else
					this.freeCarSpaces.add(new ElectricCarSpace(new Coordinate((char)(i + 65), j + 1), null, null));
			}
		}

		//Continuamos leyendo el fichero para almacenar cada coche en su plaza
		while (input.hasNext()) {
			linea = input.nextLine();
			//Descartamos las lineas que sean comentarios
			if(linea.startsWith("#")) continue;

			//Almacenamos en un array la linea separada
			arrayLineaSeparada = linea.split(";");

			//Asignamos cada dato de la linea separada al dato que corresponde
			String coordCar = arrayLineaSeparada[0];
			String plateCar = arrayLineaSeparada[1];
			String entryTimeCar = arrayLineaSeparada[2];

			//Buscamos la plaza donde esta aparcado el coche para moverla del array de plazas libres al de ocupadas
			Coordinate coordenadaDeReferencia = new Coordinate(coordCar);
			CarSpace plazaDeReferencia = new CarSpace(coordenadaDeReferencia, null, null);

			//El metodo ceiling nos va a devolver la plaza buscada
			CarSpace plazaBuscada = freeCarSpaces.ceiling(plazaDeReferencia);
			boolean todoBien = this.freeCarSpaces.remove(plazaBuscada);

			//Verificamos que todo ha ido bien (que nos de un error si algo ha ido mal
			if (todoBien == false) {
				System.out.println("Algo ha ido mal eliminando el coche del TreeSet");
			}

			//Asignamos la matricula y la hora de entrada a la plaza buscada y la anadimos al set de plazas ocupadas
			plazaBuscada.setPlate(plateCar);
			plazaBuscada.setEntryTime(entryTimeCar);
			this.busyCarSpaces.add(plazaBuscada);

			//Usamos el metodo readPlateImage de la plaza pasandole el fichero con la foto de la matricula correspondiente
			plazaBuscada.readPlateImage(plazaBuscada.getPlate() + ".jpg");

		}
		input.close();
	}


	//Metodos de instancia

	//Metodo para almacenar en un fichero el estado del parking
	public void saveParking (String fileName) throws FileNotFoundException {

		File fd = new File(fileName);
		PrintWriter pw = new PrintWriter(fd);

		//Guardamos en la primera linea del fichero las dimensiones del parking
		pw.println(this.maxZone + ";" + this.sizeZone + ";" + this.lowerElectricZone);

		//Creamos un set ordenado de objetos CarSpace empleando el constructor que nos permite especificar el criterio de comparacion (en este caso por hora de entrada)
		TreeSet<CarSpace> busyCarSpacesSortedByEntryTime = new TreeSet<CarSpace>(new CarSpaceComparatorByEntryTimeAndCoordinate());

		//Copiamos todos los objetos del set desordenado de plazas ocupadas en el nuevo set ordenado, donde se almacenaran ordenadas por su hora de entrada
		busyCarSpacesSortedByEntryTime.addAll(busyCarSpaces);

		//Ahora guardamos toda la informacion sobre las plazas ocupadas en el fichero
		for (CarSpace cs: busyCarSpacesSortedByEntryTime) {
			if(cs.getPlate() == null) continue;
			pw.println(cs.toString());
		}
		pw.close();
	}


	//Metodo para gestionar la entrada de un coche al parking
	public void carEntry (Car car, String time) {

		char carType = 'E';

		//Por defecto hemos hecho electricos e hibridos, ya que van al mismo sitio. En caso de ser de combustion, se modifica
		if ((car instanceof CombustionCar))
			carType = 'C';

		//Hacemos un switch para ver en que zona almacenar el coche dependiendo de su tipo, siempre en la primera plaza libre
		switch(carType) {
		case 'C': 
			//Buscamos cual es la primera plaza plaza libre
			CarSpace primeraPlazaLibre = freeCarSpaces.first();

			//Borramos la plaza del set de plazas libres
			boolean todoBien = this.freeCarSpaces.remove(primeraPlazaLibre);
			//Verificamos que todo ha ido bien (que nos de un error si algo ha ido mal
			if (todoBien == false) {
				System.out.println("Algo ha ido mal eliminando el coche del TreeSet");
			}

			//Le asignamos la matricula y la hora de entrada
			primeraPlazaLibre.setPlate(car.getPlate());
			primeraPlazaLibre.setEntryTime(time);

			//Anadimos la plaza al set de plazas ocupadas
			busyCarSpaces.add(primeraPlazaLibre);

			//Usamos el metodo readPlateImage de la plaza pasandole el fichero con la foto de la matricula correspondiente
			primeraPlazaLibre.readPlateImage(primeraPlazaLibre.getPlate() + ".jpg");

			return;

		case 'E':

			//Creamos una plaza de referencia con coordenada la zona de electricos
			Coordinate coordenadaDeRefElectricos = new Coordinate(this.lowerElectricZone, 1);
			CarSpace plazaDeRefZonaElectricos = new CarSpace(coordenadaDeRefElectricos, null, null);

			//El metodo ceiling nos va a devolver la primera plaza libre en la zona de electricos
			CarSpace primeraPlazaLibreElectricos = freeCarSpaces.ceiling(plazaDeRefZonaElectricos);

			//Borramos la plaza del set de plazas libres
			todoBien = this.freeCarSpaces.remove(primeraPlazaLibreElectricos);
			//Verificamos que todo ha ido bien (que nos de un error si algo ha ido mal
			if (todoBien == false) {
				System.out.println("Algo ha ido mal eliminando el coche del TreeSet");
			}
			
			//Le asignamos la matricula y la hora de entrada
			primeraPlazaLibreElectricos.setPlate(car.getPlate());
			primeraPlazaLibreElectricos.setEntryTime(time);

			//Anadimos la plaza al set de plazas ocupadas
			busyCarSpaces.add(primeraPlazaLibreElectricos);
			
			//Usamos el metodo readPlateImage de la plaza pasandole el fichero con la foto de la matricula correspondiente
			primeraPlazaLibreElectricos.readPlateImage(primeraPlazaLibreElectricos.getPlate() + ".jpg");

			//Si el coche entra en la zona de electricos, le enchufamos un cargador
			((ElectricCarSpace)primeraPlazaLibreElectricos).getCharger().connect();

			return;

		}	
	}


	//Metodo para gestionar la salida de un coche del parking
	public String carDeparture (String plate) {
		String horaEntrada;

		//Recorro el set de plazas ocupadas en busca de la matricula que coincida con la que quiero eliminar
		for (CarSpace cs: busyCarSpaces) {

			if(!plate.equals(cs.getPlate())) continue;
			//Una vez localizado, usamos el metodo savePlateImage para guardar la matricula del coche que estaba aparcado
			cs.savePlateImage("plateImage_" + cs.getPlate() + ".jpg");
			//Ponemos la matricula a null
			cs.setPlate(null);
			//Ademas de la matricula, pongo la hora a null, guardandola primero en otra variable para retornarla
			horaEntrada = cs.getEntryTime();
			cs.setEntryTime(null);
			//Ahora anado la plaza liberada al set de plazas libres y la elimino del de plazas ocupadas
			this.freeCarSpaces.add(cs);
			this.busyCarSpaces.remove(cs);			

			//Tras esto, si el coche es de tipo electrico, desconecto el cargador
			if (cs instanceof ElectricCarSpace)
				((ElectricCarSpace)cs).getCharger().disconnect();
			return horaEntrada;
		}
		return null;
	}


	//Metodo que nos genera un dibujo del parking
	public String toMap () {		
		String map = "";

		//Creamos un set ordenado en el que incluimos los dos sets
		TreeSet<CarSpace> allCarSpaces = new TreeSet<CarSpace>(freeCarSpaces);
		allCarSpaces.addAll(busyCarSpaces);

		//Ahora guardamos toda la informacion sobre las plazas ocupadas en el fichero
		for (CarSpace cs: allCarSpaces) {

			//Ponemos la coordenada y un espacio
			map = map + cs.getCoordinate().toString() + " ";

			//Si el coche es electrico ponemos una E y si no un espacio
			if(cs instanceof ElectricCarSpace) {
				map = map + "E";
			}
			else {
				map = map + " ";
			}

			//Si la plaza esta vacia rellenamos con espacios y si no con la propia matricula
			if(cs.getPlate() == null) {
				map = map + "        ";
			}
			else {
				map = map + " " + cs.getPlate();
			}

			//Terminamos con un separador
			map = map + "|";

			//Comprobamos si llego al ultimo elemento de la linea para anadir un salto de linea cada vez que acabe una fila
			if ((cs.getCoordinate().getNumber()) == this.sizeZone)
				map = map + "\n";

		}
		return map;
	}


	//Getters y Setters
	public char getMaxZone() {
		return maxZone;
	}

	public void setMaxZone(char maxZone) {
		this.maxZone = maxZone;
	}

	public int getSizeZone() {
		return sizeZone;
	}

	public void setSizeZone(int sizeZone) {
		this.sizeZone = sizeZone;
	}

	public char getLowerElectricZone() {
		return lowerElectricZone;
	}

	public void setLowerElectricZone(char lowerElectricZone) {
		this.lowerElectricZone = lowerElectricZone;
	}

}