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
import oslomet.testing.Sikkerhet.Sikkerhet;

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

    @Mock
    private Sikkerhet sjekk;

    // hent alle // ---TEST GODKJENT-----
    @Test
    public void hentAlleKontoTest() {
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());
        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn("12345678901");
        //Setter opp et mock når aRepository.hentAlleKonti() blir kalt
        Mockito.when(aRepository.hentAlleKonti()).thenReturn(kontoer);
        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat= kontoController.hentAlleKonti();
        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertEquals(kontoer, resultat);
    }

    //registrer    // ----TEST GODKJENT----
    @Test
    public void registrerKontoTest(){
        // lager ny konto
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn("12345678901");
        Mockito.when(aRepository.registrerKonto(konto)).thenReturn("OK");

        String resultat = kontoController.registrerKonto(konto);

        assertEquals("OK", resultat);
    }


    //endre // ----TEST GODKJENT----
    @Test
    public void endreKontoTest(){
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn("12345678901");
        Mockito.when(aRepository.endreKonto(konto)).thenReturn("OK");

        String resultat = kontoController.endreKonto(konto);

        assertEquals("OK", resultat);
    }

    //slett // ---TEST GODKJENT-----
    @Test
    public void slettKontoTest(){
        String kontonummer = "12345678901";

        Mockito.when(sjekk.loggetInn()).thenReturn("LoggetInn");
        Mockito.when(aRepository.slettKonto(kontonummer)).thenReturn("Slettet");

        String resultat = kontoController.slettKonto(kontonummer);
        assertEquals("Slettet", resultat);
    }

}
