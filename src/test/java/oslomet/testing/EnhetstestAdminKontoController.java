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

import java.util.ArrayList;
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
    public void hentAlleKonti_loggetInn() {
        // ARRANGE:
        // lage en list med kontoer
        List<Konto> kontoer = Hjelp.kontoGenerator(3);

        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");

        //Setter opp et mock når aRepository.hentAlleKonti() blir kalt
        Mockito.when(repository.hentAlleKonti()).thenReturn(kontoer);

        // ACT:
        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat = kontoController.hentAlleKonti();

        // ASSERT:
        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertEquals(kontoer, resultat);
    }

    // INTERESSANT: Systemet vil kun avslå din tilgang om repository-metoden returnerer null!
    // Så man kan legit få repositorien til å returnere hva man vil og man kommer seg inn...
    @Test
    public void hentAlleKonti_ikkeLoggetInn() {
        // ARRANGE:
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());

        // setter opp mock når sjekk.LoggetInn() blir kalt
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Setter opp et mock når aRepository.hentAlleKonti() blir kalt
        Mockito.when(repository.hentAlleKonti()).thenReturn(kontoer);

        // ACT:
        //henter faktiske kontoer fra kontocontroller
        List<Konto> resultat = kontoController.hentAlleKonti();

        // ASSERT:
        // sammenligner resultat og ser om vi får tilbake det vi har sendt
        assertNull(resultat);
    }


    //registrer    // ----TEST GODKJENT----
    @Test
    public void registrerKontoTest(){
        // ARRANGE:
        // Lager ny konto
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.registrerKonto(konto)).thenReturn("OK");

        // ACT:
        String resultat = kontoController.registrerKonto(konto);

        // ARRANGE:
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKontoTest_ikkeLoggetInn()    {
        // ARRANGE:
        // lager ny konto
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn(null);
        Mockito.when(repository.registrerKonto(konto)).thenReturn("OK");

        // ACT:
        String resultat = kontoController.registrerKonto(konto);

        // ASSERT:
        assertEquals("Ikke innlogget", resultat);
    }


    //endre // ----TEST GODKJENT----
    @Test
    public void endreKontoTest(){
        // ARRANGE:
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.endreKonto(konto)).thenReturn("OK");

        // ACT:
        String resultat = kontoController.endreKonto(konto);

        // ASSERT:
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKontoTest_ikkeLoggetInn(){
        // ARRANGE:
        Konto konto = new Konto();

        Mockito.when(sjekk.loggetInn()).thenReturn(null);
        Mockito.when(repository.endreKonto(konto)).thenReturn("OK");

        // ACT:
        String resultat = kontoController.endreKonto(konto);

        // ASSERT:
        assertEquals("Ikke innlogget", resultat);
    }

    //slett // ---TEST GODKJENT-----
    @Test
    public void slettKontoTest(){
        // ARRANGE:
        String kontonummer = "12345678901";

        Mockito.when(sjekk.loggetInn()).thenReturn("Innlogget");
        Mockito.when(repository.slettKonto(kontonummer)).thenReturn("Slettet");

        // ACT:
        String resultat = kontoController.slettKonto(kontonummer);

        // ARRANGE:
        assertEquals("Slettet", resultat);
    }

    @Test
    public void slettKontoTest_ikkeLoggetInn(){
        // ARRANGE:
        String kontonummer = "12345678901";

        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // ACT:
        String resultat = kontoController.slettKonto(kontonummer);

        // ASSERT:
        assertEquals("Ikke innlogget", resultat);
    }

}
