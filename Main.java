package vecinos;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Aliados a = new Aliados("vecinos_mismoCosto.in");
		a.resolver();
	}
}
