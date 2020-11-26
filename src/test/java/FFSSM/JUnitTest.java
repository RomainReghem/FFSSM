package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author romai
 */
public class JUnitTest {
    
    Plongeur plongeurA, plongeurB;
    Moniteur moniteurA, moniteurB;
    LocalDate naissance;
    LocalDate datePlongee, datePlongee2;
    Site s;
    Plongee plongee, plongee2;
    Club club, club2;
    Licence licence;
    
    @BeforeEach
    public void setUp(){
        datePlongee = LocalDate.of(2020, 12, 24);
        plongeurA = new Plongeur("01", "Jeff", "Bezos", "12 avenue de l'argent", "0678894563", naissance);
        plongeurB = new Plongeur("03", "Tim", "Sweeney", "15 rue épique", "0754876935", naissance);
        moniteurA = new Moniteur("05", "Elon", "Musk", "45 rue de l'espace", "0752145689", naissance, 50);
        moniteurB = new Moniteur("04", "Jean", "Lassalle", "2 rue de la campagne", "0457693214", naissance, 62);
        club = new Club(moniteurA,"Club de fou !", "0467521489");
        club2 = new Club(moniteurA,"Meilleur club", "0259476144");
        plongee = new Plongee(s, moniteurA, datePlongee, 20, 120); 
        plongee2 = new Plongee(s, moniteurB, datePlongee2, 30, 240);
    }
    
    @Test
    public void testLicenceValide(){
        plongeurA.ajouteLicence("0100", LocalDate.of(2018, 10, 22), club);
        // la licence est censée être toujours valide
        assertTrue(plongeurA.getLicenceActuelle().estValide(LocalDate.of(2018, 11, 22)));
        // La licence est censé être périmée
        assertFalse(plongeurA.getLicenceActuelle().estValide(LocalDate.of(2019, 10, 24)));        
    }
    
    @Test
    public void testAjouteParticipant(){
        assertEquals(0, plongee.getListeParticipants().size(), "La liste n'est pas vide alors qu'aucun plongeur n'a été add");
        plongee.ajouteParticipant(plongeurA);
        plongee.ajouteParticipant(plongeurB);
        assertEquals(2, plongee.getListeParticipants().size(),"Les plongeurs n'ont pas été ajoutés à la liste");
    }
    
    @Test
    public void testEstConforme(){        
        plongee.ajouteParticipant(plongeurA);
        plongeurA.ajouteLicence("0100", LocalDate.of(2020, 5, 12), club);
        // La plongée se passe le 24 décembre 2020 donc elle est censée être conforme
        assertTrue(plongee.estConforme());
        plongee.ajouteParticipant(plongeurB);
        plongeurA.ajouteLicence("0100", LocalDate.of(2019, 5, 12), club);
        // Sa licence expire le 12 mai 2020 donc elle est censée est non conforme
        assertFalse(plongee.estConforme());
    }
    
    @Test
    public void testNouvelleEmbauche(){
        moniteurB.nouvelleEmbauche(club, LocalDate.of(2020, 2,10));
        assertEquals(moniteurB.employeurActuel(), Optional.of(club), "L'employeur actuel ne correspond pas");
        
        // On refait une embauche dans un autre club pour vérifier que son embauche précédente est bien annulée
        moniteurB.nouvelleEmbauche(club2, LocalDate.of(2020, 8, 15));
        assertEquals(moniteurB.employeurActuel(), Optional.of(club2), "L'employeur n'a pas changé");
        
        // On teste s'il y a bien 2 emplois enregistrés pour ce moniteur
        assertEquals(2, moniteurB.emplois().size());
    }
    
    @Test
    public void testOrganisePlongee(){
        club.organisePlongee(plongee);
        club.organisePlongee(plongee2);
        assertEquals(2, club.getPlongees().size());
    }
    
    @Test 
    public void testPlongeesNonConformeClub(){
        club.organisePlongee(plongee);
        club.organisePlongee(plongee2);
        plongee.ajouteParticipant(plongeurA);
        // On ajoute un plongeur avec une licence périmée dans la plongée 1
        plongeurA.ajouteLicence("0100", LocalDate.of(2017, 5, 12), club);
        // La plongée 2 est conforme vu qu'il n'y a personne dedans, la plongée 1 n'est pas censée l'être
        assertEquals(1, club.plongeesNonConformes().size());
        
        
    }
    
    
    
    
    
    
    
}
