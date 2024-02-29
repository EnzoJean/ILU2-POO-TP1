package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois gg = null;
		etal.libererEtal();
		etal.acheterProduit(10, gg);
		System.out.println("Fin du test");
		}
}
