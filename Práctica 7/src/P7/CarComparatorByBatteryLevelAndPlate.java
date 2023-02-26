package P7;

import java.util.Comparator;

//Clase de comparacion de dos coches implentando Comparator
public class CarComparatorByBatteryLevelAndPlate implements Comparator<Car> {

	//Metodo compare con el que compararemos dos coches segun su nivel de bateria
	public int compare(Car c1, Car c2) {

		float batteryC1 = 0;
		float batteryC2 = 0;

		//Primero obtenemos el nivel de bateria de los dos coches segun su tipo
		if(c1 instanceof HybridCar)
			batteryC1 = ((HybridCar)c1).getBatteryCharge();
		if(c2 instanceof HybridCar)
			batteryC2 = ((HybridCar)c2).getBatteryCharge();

		if(c1 instanceof ElectricCar)
			batteryC1 = ((ElectricCar)c1).getBatteryCharge();
		if(c2 instanceof ElectricCar)
			batteryC2 = ((ElectricCar)c2).getBatteryCharge();

		if(c1 instanceof CombustionCar)
			batteryC1 = 0;
		if(c2 instanceof CombustionCar)
			batteryC2 = 0;

		//Aplicamos el criterio de comparacion segun su nivel de bateria en orden ascendente
		if (batteryC1 < batteryC2) return -1;
		if (batteryC1 > batteryC2) return 1;
		//En caso de baterias iguales, nos fijamos en su matricula, en orden ascendente tambien
		if (batteryC1 == batteryC2) {
			if(c1.getPlate().compareTo(c2.getPlate()) < 0) return -1;
			if(c1.getPlate().compareTo(c2.getPlate()) > 0) return 1;
			return 0;
		}

		return 0;
	}

}
