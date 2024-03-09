package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjoner_loggetInn()   {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        // ACT:
        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn()   {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        when(sjekk.loggetInn()).thenReturn(null);

        // ACT:
        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        // ASSERT:
        assertNull(resultat);
    }

    // Terskeltest på 10 000 transaksjoner på en konto
    @Test
    public void hentTransaksjoner_terskel() {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10_000);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        // ACT:
        Konto resultat = bankController.hentTransaksjoner("123456789", "", "");

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentKonti_loggetInn()  {
        // ARRANGE:
        List<Konto> konti = Hjelp.kontoGenerator(10);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // ACT:
        List<Konto> resultat = bankController.hentKonti();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_ikkeLoggetInn()  {
        // ARRANGE:
        when(sjekk.loggetInn()).thenReturn(null);

        // ACT:
        List<Konto> resultat = bankController.hentKonti();

        // ASSERT:
        assertNull(resultat);
    }

    // Terskeltest på 900 000 kontoer
    @Test
    public void hentKonti_terskel() {
        // ARRANGE:
        List<Konto> konti = Hjelp.kontoGenerator(900_000);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // ACT:
        List<Konto> resultat = bankController.hentKonti();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        // ARRANGE:
        List<Konto> kontoer = new ArrayList<>();
        kontoer.add(new Konto("1234567890", "1234567890",
                1000, "Lønnskonto", "NOK", Hjelp.transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        // ACT:
        List<Konto> resultat = bankController.hentSaldi();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn()   {
        // ARRANGE:
        List<Konto> kontoer = new ArrayList<>();
        kontoer.add(new Konto("1234567890", "1234567890", 1000, "Lønnskonto", "NOK", Hjelp.transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        // ACT:
        List<Konto> resultat = bankController.hentSaldi();

        // ASSERT:
        assertNull(resultat);
    }

    // Terskeltest med 100 000 kontoer med samme personnummer;
    @Test
    public void hentSaldi_terskel()     {
        // ARRANGE:
        List<Konto> kontoer = Hjelp.kontoGenerator(100_000, "1234567890");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        // ACT:
        List<Konto> resultat = bankController.hentSaldi();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
    }


    @Test
    public void registrerBetaling_loggetInn()     {
        // ARRANGE:
        Transaksjon transaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        // ACT:
        String resultat = bankController.registrerBetaling(transaksjon);

        // ASSERT:
        assertNotNull(resultat);
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_ikkeLoggetInn()   {
        // ARRANGE:
        Transaksjon transaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        // ACT:
        String resultat = bankController.registrerBetaling(transaksjon);

        // ARRANGE:
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_loggetInn()    {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentBetalinger_ikkeLoggetInn()    {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // ASSERT:
        assertNull(resultat);
    }

    // Terskeltest med 4 000 000 transaksjoner:
    @Test
    public void hentBetalinger_terskel()      {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(4_000_000);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_loggetInn()   {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn("1234567890");
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.utforBetaling(10);

        // ASSRT:
        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_ikkeLoggetInn()   {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.utforBetaling(10);

        // ASSERT:
        assertNull(resultat);
    }

    // Terskeltest på 4000 transaksjoner:
    @Test
    public void utforBetaling_terskel()     {
        // ARRANGE:
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(4000);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn("1234567890");
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // ACT:
        List<Transaksjon> resultat = bankController.utforBetaling(10);

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {
        // ARRANGE:
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // ACT:
        Kunde resultat = bankController.hentKundeInfo();

        // ASSERT:
        assertNotNull(resultat);
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_ikkeLoggetInn() {

        // ARRANGE:
        when(sjekk.loggetInn()).thenReturn(null);

        // ACT:
        Kunde resultat = bankController.hentKundeInfo();

        // ASSERT:
        assertNull(resultat);
    }

    @Test
    public void endre_loggetInn()   {
        // ARRANGE:
        Kunde kunde = new Kunde("0987654321",
                "Lene", "Jensen", "Askerveien 22", null,
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("1234567890");

        when(repository.endreKundeInfo(any())).thenReturn("OK");

        // ACT:
        String resultat = bankController.endre(kunde);

        // ASSERT:
        assertNotNull(resultat);
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn()   {
        // ARRANGE:
        Kunde kunde = new Kunde("0987654321",
                "Lene", "Jensen", "Askerveien 22", null,
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.endreKundeInfo(any())).thenReturn("OK");

        // ACT:
        String resultat = bankController.endre(kunde);

        // ASSERT:
        assertNull(resultat);
    }

}

