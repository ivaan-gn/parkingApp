package P7;

import java.util.Comparator;

//Clase de comparacion de dos plazas implementando Comparator
public class CarSpaceComparatorByEntryTimeAndCoordinate implements Comparator <CarSpace>{

	//Metodo compare con el que compararemos dos plazas segun la hora de entrada
	public int compare(CarSpace cs1, CarSpace cs2) {
		if (cs1.getEntryTime().compareTo(cs2.getEntryTime()) < 0) return -1;
		if (cs1.getEntryTime().compareTo(cs2.getEntryTime()) > 0) return 1;

		//Si la hora de entrada coincide, nos fijamos en su coordenada
		return cs1.getCoordinate().compareTo(cs2.getCoordinate());
	}

}