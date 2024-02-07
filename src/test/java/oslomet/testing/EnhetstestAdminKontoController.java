package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController kontoController;

    @Mock
    private AdminRepository aRepository;

    // hent alle
    @Test
    public void hentAlleKontoTest() {
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());
        //kjører en mock når
        Mockito.when(aRepository.hentAlleKonti(anyString())).thenReturn(kontoer);
        //henter faktiske kontoer
        List<Konto> resultat= aRepository.hentAlleKonti();
        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertEquals(kontoer, resultat);
    }

    //registrer
    @Test
    public void registrerKontoFinnesFraFørTest(){
        // lager ny konto
        Konto konto = new Konto();
        Mockito.when(aRepository.registrerKonto(konto.getPersonnummer())).thenReturn(konto);

        String resultat = aRepository.registrerKonto(konto);

        assertEquals("Feil", resultat);
    }
    public void registrerKontoException(){
        Konto konto = new Konto();
        Mockito.when(aRepository.registrerKonto
        String resultat = aRepository.registrerKonto(konto);
        assertEquals("Feil", resultat);

    }
    public void registrerKontoAltGikkBra(){
        Konto konto = new Konto();

        
    }
    //endre
    @Test
    public void endreKontoTest(){

    }
    public void endreKontoException(){

    }
    public void endreKontoReturn(){

    }

    //slett
    @Test
    public void slettKontoTest(){

    }
    public void slettKontoException(){

    }
    public void slettKontoReturn(){

    }
}
