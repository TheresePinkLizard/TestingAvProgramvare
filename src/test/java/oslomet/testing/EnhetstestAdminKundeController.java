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
    public void lagreKunde_loggetInn() {
        // ARRANGE:
        List<Kunde> kunder = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn("Innlogget");
        when (repository.hentAlleKunder()).thenReturn(kunder);

        // ACT:
        List<Kunde> ekteKunder = adminKundeController.hentAlle();

        // ASSERT:
        assertEquals(kunder, ekteKunder);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn()    {
        // ARRANGE:
        List<Kunde> kunder = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn(null);

        when (repository.hentAlleKunder()).thenReturn(kunder);

        // ACT:
        List<Kunde> resultat = adminKundeController.hentAlle();

        // ASSERT:
        assertNull(resultat);
    }

    @Test
    public void hentAlle_loggetInn(){
        // ARRANGE:
        List<Kunde> kunder = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn("Innlogget");

        when (repository.hentAlleKunder()).thenReturn(kunder);

        // ACT:
        List<Kunde> resultat = adminKundeController.hentAlle();

        // ASSERT:
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_ikkeLoggetInn(){
        // ARRANGE:
        List<Kunde> forventedeKunder = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn(null);

        when (repository.hentAlleKunder()).thenReturn(forventedeKunder);

        // ACT:
        List<Kunde> resultat = adminKundeController.hentAlle();

        // ASSERT:
        assertNull(resultat);
    }

    @Test
    public void hentAlle_terskel()  {
        // ARRANGE:
        List<Kunde> forventedeKunder = Hjelp.kundeGenerator(2_000_000);

        when(sjekk.loggetInn()).thenReturn("Innlogget");

        when(repository.hentAlleKunder()).thenReturn(forventedeKunder);

        // ACT:
        List<Kunde> ekteKunder = adminKundeController.hentAlle();

        // ASSERT:
        assertEquals(forventedeKunder, ekteKunder);
    }

    @Test
    public void endre_loggetInn() {
        // ARRANGE:
        Kunde kunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn("Innlogget");

        when(repository.endreKundeInfo(kunde)).thenReturn("OK");

        // ACT:
        String resultat = adminKundeController.endre(kunde);

        // ASSERT:
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn() {
        // ARRANGE:
        Kunde kunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.endreKundeInfo(kunde)).thenReturn("OK");

        // ACT:
        String resultat = adminKundeController.endre(kunde);

        // ASSERT:
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slett_loggetInn() {
        // ARRANGE:
        String personnummer = "123456789";

        when(sjekk.loggetInn()).thenReturn("Innlogget");

        when(repository.slettKunde(personnummer)).thenReturn("OK");

        // ACT:
        String resultat = adminKundeController.slett(personnummer);

        // ASSERT:
        assertEquals("OK", resultat);
    }

    @Test
    public void slett_ikkeLoggetInn() {
        // ARRANGE:
        String personnummer = "123456789";

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.slettKunde(personnummer)).thenReturn("OK");

        // ACT:
        String resultat = adminKundeController.slett(personnummer);

        // ASSERT:
        assertEquals("Ikke logget inn", resultat);
    }
}