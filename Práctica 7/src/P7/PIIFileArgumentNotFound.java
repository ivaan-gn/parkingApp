package P7;

public class PIIFileArgumentNotFound extends Exception{

	//Atributos
	private String filename;

	
	//Constructor
	public PIIFileArgumentNotFound (String filename) {
		this.setFilename(filename);
	}

	
	//Getters y Setters
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}