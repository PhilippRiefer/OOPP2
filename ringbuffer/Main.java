package ringbuffer;

/*	Programm : Main . java
*	Autoren : Philipp Riefer, Domenic Heidemann
*	Datum : 10.12.2020
*/

public class Main {
	public static void main(String[] args) {
		Buffer trump = new Buffer();
		trump.put("bitch");
		trump.put("racist");
		trump.put("liar");
		trump.put("mysogonist");
		System.out.println("trump = "+ trump.get());
	}
}
