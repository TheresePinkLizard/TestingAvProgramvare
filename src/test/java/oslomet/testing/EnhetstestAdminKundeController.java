package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void testLagreKunde() {
        when(sjekk.loggetInn()).thenReturn("Innlogget");
        List<Kunde> forventedeKunder = new ArrayList<>();
        when (repository.hentAlleKunder()).thenReturn(forventedeKunder);
        List<Kunde> ekteKunder = adminKundeController.hentAlle();
        assertEquals(forventedeKunder, ekteKunder);
    }

    @Test
    public void testLagreKunde_ikkeLoggetInn()    {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Kunde> forventedeKunder = new ArrayList<>();
        when (repository.hentAlleKunder()).thenReturn(forventedeKunder);
        List<Kunde> ekteKunder = adminKundeController.hentAlle();
        assertNull(ekteKunder);
    }

    @Test
    public void testHentAlle(){
        when(sjekk.loggetInn()).thenReturn("Innlogget");
        List<Kunde> forventedeKunder = new ArrayList<>();
        when (repository.hentAlleKunder()).thenReturn(forventedeKunder);
        List<Kunde> ekteKunder = adminKundeController.hentAlle();
        assertEquals(forventedeKunder, ekteKunder);
    }

    @Test
    public void testHentAlle_ikkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);
        List<Kunde> forventedeKunder = new ArrayList<>();
        when (repository.hentAlleKunder()).thenReturn(forventedeKunder);
        List<Kunde> ekteKunder = adminKundeController.hentAlle();
        assertNull(ekteKunder);
    }

    @Test
    public void testEndre() {
        when(sjekk.loggetInn()).thenReturn("Innlogget");
        Kunde kunde1 = new Kunde();
        when(repository.endreKundeInfo(kunde1)).thenReturn("OK");
        String resultat = adminKundeController.endre(kunde1);
        assertEquals("OK", resultat);
    }

    @Test
    public void testEndre_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        Kunde kunde1 = new Kunde();
        when(repository.endreKundeInfo(kunde1)).thenReturn("OK");
        String resultat = adminKundeController.endre(kunde1);
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void testSlett() {
        when(sjekk.loggetInn()).thenReturn("Innlogget");
        String personnummer = "123456789";
        when(repository.slettKunde(personnummer)).thenReturn("OK");
        String resultat = adminKundeController.slett(personnummer);
        assertEquals("OK", resultat);
    }

    @Test
    public void testSlett_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String personnummer = "123456789";
        when(repository.slettKunde(personnummer)).thenReturn("OK");
        String resultat = adminKundeController.slett(personnummer);
        assertEquals("Ikke logget inn", resultat);
    }
}