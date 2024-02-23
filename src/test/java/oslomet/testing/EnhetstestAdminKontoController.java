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

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController kontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    // hent alle // ---TEST GODKJENT-----
    @Test
    public void hentAlleKontoTest() {
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());

        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");

        //Setter opp et mock når aRepository.hentAlleKonti() blir kalt
        Mockito.when(repository.hentAlleKonti()).thenReturn(kontoer);

        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat = kontoController.hentAlleKonti();

        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertEquals(kontoer, resultat);
    }

    // INTERESSANT: Systemet vil kun avslå din tilgang om repository-metoden returnerer null!
    // Så man kan legit få repositorien til å returnere hva man vil og man kommer seg inn...
    @Test
    public void hentAlleKontoTest_feil() {
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());

        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn("flkgdkfølgkdjlf");

        //Setter opp et mock når aRepository.hentAlleKonti() blir kalt
        Mockito.when(repository.hentAlleKonti()).thenReturn(kontoer);

        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat = kontoController.hentAlleKonti();

        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertEquals(kontoer, resultat);
    }

    // Sjekker at resultat ikke kommer om man ikke er logget inn:
    @Test
    public void hentAlleKontoTest_ikkeLoggetInn()   {
        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat = kontoController.hentAlleKonti();

        // sjekker om resultatet blir dermed "0".
        assertNull(resultat);
    }

    //registrer    // ----TEST GODKJENT----
    @Test
    public void registrerKontoTest(){
        // lager ny konto
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.registrerKonto(konto)).thenReturn("OK");

        String resultat = kontoController.registrerKonto(konto);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKontoTest_ikkeLoggetInn()    {

        // lager ny konto
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn(null);
        Mockito.when(repository.registrerKonto(konto)).thenReturn("OK");

        String resultat = kontoController.registrerKonto(konto);

        assertEquals("Ikke innlogget", resultat);
    }


    //endre // ----TEST GODKJENT----
    @Test
    public void endreKontoTest(){
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.endreKonto(konto)).thenReturn("OK");

        String resultat = kontoController.endreKonto(konto);

        assertEquals("OK", resultat);
    }

    @Test
    public void endreKontoTest_ikkeLoggetInn(){
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn(null);
        Mockito.when(repository.endreKonto(konto)).thenReturn("OK");

        String resultat = kontoController.endreKonto(konto);

        assertEquals("Ikke innlogget", resultat);
    }

    //slett // ---TEST GODKJENT-----
    @Test
    public void slettKontoTest(){
        String kontonummer = "12345678901";

        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.slettKonto(kontonummer)).thenReturn("Slettet");

        String resultat = kontoController.slettKonto(kontonummer);
        assertEquals("Slettet", resultat);
    }

    @Test
    public void slettKontoTest_ikkeLoggetInn(){
        String kontonummer = "12345678901";

        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        String resultat = kontoController.slettKonto(kontonummer);
        assertEquals("Ikke innlogget", resultat);
    }

}
