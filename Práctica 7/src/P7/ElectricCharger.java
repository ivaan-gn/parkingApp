package P7;

public class ElectricCharger {

	//Atributos de instancia
	private boolean connected;

	//Atributos estaticos
	public static final int POWER = 25;

	//Constructor vacio
	ElectricCharger () {
		this.connected = false;
	}


	//Metodos de instancia

	//Metodo para indicar que un coche esta conectado al cargador
	public void connect() {
		this.connected = true;
	}


	//Metodo para indicar que un coche esta desconectado del cargador
	public void disconnect() {
		this.connected = false;
	}


	//Getters y Setters
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}