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
        ArrayList<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        assertNotNull(resultat);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn()   {
        ArrayList<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        assertNull(resultat);
    }

    // Terskeltest på 4 millioner transaksjoner på en konto
    @Test
    public void hentTransaksjoner_terskel() {
        ArrayList<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(4_000_000);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        Konto resultat = bankController.hentTransaksjoner("123456789", "", "");

        assertNotNull(resultat);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentKonti_loggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNotNull(resultat);
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_ikkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    // Terskeltest på 2 millioner kontoer
    @Test
    public void hentKonti_terskel() {
        ArrayList<Konto> konti = Hjelp.kontoGenerator(400_000);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNotNull(resultat);
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {

        ArrayList<Konto> kontoer = new ArrayList<>();
        kontoer.add(new Konto("1234567890", "1234567890", 1000, "Lønnskonto", "NOK", Hjelp.transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn()   {

        ArrayList<Konto> kontoer = new ArrayList<>();
        kontoer.add(new Konto("1234567890", "1234567890", 1000, "Lønnskonto", "NOK", Hjelp.transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_terskel()     {

        // Generer 100 000 kontoer med samme personnummer;
        ArrayList<Konto> kontoer = Hjelp.kontoGenerator(2, "1234567890");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
    }


    @Test
    public void registrerBetaling_loggetInn()     {
        Transaksjon transaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        String resultat = bankController.registrerBetaling(transaksjon);

        assertNotNull(resultat);
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_ikkeLoggetInn()   {
        Transaksjon transaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        String resultat = bankController.registrerBetaling(transaksjon);

        assertNull(resultat);
    }

    /* Notat (slett senere):
    hentTransaksjoner henter den første kontoen med et spesielt kontonr i databasen
    i mens hentBetalinger henter liste med transaksjoner basert på personnummer og om den avventer.
*/

    @Test
    public void hentBetalinger_loggetInn()    {

      List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);

      when(sjekk.loggetInn()).thenReturn("01010110523");

      when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

      List<Transaksjon> resultat = bankController.hentBetalinger();

      assertNotNull(resultat);
      assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentBetalinger_ikkeLoggetInn()    {

      List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);

      when(sjekk.loggetInn()).thenReturn(null);

      when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

      List<Transaksjon> resultat = bankController.hentBetalinger();

      assertNull(resultat);
    }

    @Test
    public void hentBetalinger_terksel()      {
      List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(4_000_000);

      when(sjekk.loggetInn()).thenReturn("01010110523");

      when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

      List<Transaksjon> resultat = bankController.hentBetalinger();

      assertNotNull(resultat);
      assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_loggetInn()   {

        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn("1234567890");
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(10);

        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_ikkeLoggetInn()   {

        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(10);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(10);

        assertNull(resultat);
    }

    @Test
    public void utforBetaling_terskel()     {
        List<Transaksjon> transaksjoner = Hjelp.transaksjonsGenerator(4_000_00);
        transaksjoner.get(0).setAvventer("0");

        when(sjekk.loggetInn()).thenReturn("1234567890");
        when(repository.utforBetaling(10)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(10);

        assertNotNull(resultat);
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNotNull(resultat);
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void endre_loggetInn()   {
        // Forventet kunde:
        Kunde kunde = new Kunde("0987654321",
                "Lene", "Jensen", "Askerveien 22", null,
                "Asker", "22224444", "HeiHei");

        // Personnummer vil da bli endret til dette:
        when(sjekk.loggetInn()).thenReturn("1234567890");

        when(repository.endreKundeInfo(any())).thenReturn("OK");

        String resultat = bankController.endre(kunde);

        assertNotNull(resultat);
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn()   {
        // Forventet kunde:
        Kunde kunde = new Kunde("0987654321",
                "Lene", "Jensen", "Askerveien 22", null,
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.endreKundeInfo(any())).thenReturn("OK");

        String resultat = bankController.endre(kunde);

        assertNull(resultat);
    }

}

