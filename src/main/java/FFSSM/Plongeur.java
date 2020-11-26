package FFSSM;

import java.time.LocalDate;

public class Plongeur extends Personne {
    
    private int niveau;
    
    private Licence licenceActuelle;
    
    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);        
    }
    
    public void ajouteLicence(String numero, LocalDate delivrance, Club club){
        licenceActuelle = new Licence(this, numero, delivrance, club);
    }
    
    public Licence getLicenceActuelle(){
        return licenceActuelle;
    }
    
    public void setNiveau(int n){
        this.niveau = n;
    }
    
    public int getNiveau(){
        return niveau;
    }
}
