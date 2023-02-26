package P7;

public class ElectricCarSpace extends CarSpace {

	//Atributos de instancia
	private ElectricCharger charger;


	//Contructor
	ElectricCarSpace (Coordinate coordinate, String plate, String entryTime) {
		super(coordinate, plate, entryTime);
		this.charger = new ElectricCharger();
	}


	//Getters y Setters
	public ElectricCharger getCharger() {
		return charger;
	}

	public void setCharger(ElectricCharger charger) {
		this.charger = charger;
	}

}