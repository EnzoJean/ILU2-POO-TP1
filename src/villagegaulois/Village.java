package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	public Marche marche;
		
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom()+ " cherche un endroit pour "
				+ "vendre "+ nbProduit + " " + produit + ".\n");
		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre == -1) {
			chaine.append("Aucun étal n'est libre. Le vendeur "
			+ vendeur.getNom() + " rentre chez soi.\n");
			return chaine.toString();
		}
		marche.etals[etalLibre].occuperEtal(vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit
				+ " à l'étal n°" + (etalLibre+1) + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] vendeurs = marche.rechercheProduit(produit);
		int nbTrouves = 0;
		StringBuilder chaine = new StringBuilder();
		if (vendeurs[0] == null) 
			return "Il n'y a pâs de vendeur qui propose des " + produit + " au marché.\n";
		if (vendeurs[1] == null) {
			chaine.append("Seul le vendeur " + vendeurs[0].getVendeur().getNom() +
					" propose des "+ produit + " au marché.\n");
			return chaine.toString();
		}
		chaine.append("Les vendeurs qui proposent des "+ produit + " sont :\n");
		while (vendeurs[nbTrouves] != null) {
			chaine.append("- " + vendeurs[nbTrouves].getVendeur().getNom() +"\n");
			nbTrouves += 1;
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(marche.trouverVendeur(vendeur).libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \""+ this.getNom()+"\" possède ");
		if (marche.trouverEtalLibre() != -1) {
			chaine.append("plusieurs étals :\n");
			chaine.append(marche.afficherMarche());
		}
		else {
			chaine.append("aucun étal.\n");
		}
		return chaine.toString();
	}
	
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i<nbEtals;++i) {
				etals[i] = new Etal();
			}
		}
		

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i<etals.length; ++i) {
				if (etals[i] == null || !etals[i].isEtalOccupe()) 
					return i;
			}
			return -1;
		}
		
		private Etal[] rechercheProduit(String produit) {
			Etal[] etalsProduit = new Etal[etals.length];
			int etalsTrouves = 0;
			for (int i = 0; i<etals.length; ++i) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduit[etalsTrouves] = etals[i];
					etalsTrouves += 1;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i<etals.length; ++i) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalsLibres = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i<etals.length; ++i) {
				if (!etals[i].isEtalOccupe())
					nbEtalsLibres += 1;
				else 
					chaine.append(etals[i].afficherEtal());
			}
			chaine.append("Il reste " + nbEtalsLibres + " étals non utilisés"
					+ " dans le marché.");
			return chaine.toString();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}