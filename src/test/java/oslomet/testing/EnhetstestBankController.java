package oslomet.testing;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.swing.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        ArrayList<Transaksjon> transaksjoner = transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        assertNotNull(resultat);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn()   {
        ArrayList<Transaksjon> transaksjoner = transaksjonsGenerator(10);
        Konto konto = new Konto("1234567890", "1234567890", 1234, "Lønnskonto", "NOK", transaksjoner);

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("1234567890", "", "");

        assertNull(resultat);
    }

    // Terskeltest på 4 millioner transaksjoner på en konto
    @Test
    public void hentTransaksjoner_terskel() {
        ArrayList<Transaksjon> transaksjoner = transaksjonsGenerator(4_000_000);
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
        ArrayList<Konto> konti = kontoGenerator(2_000_000);

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
        kontoer.add(new Konto("1234567890", "1234567890", 1000, "Lønnskonto", "NOK", transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn()   {

        ArrayList<Konto> kontoer = new ArrayList<>();
        kontoer.add(new Konto("1234567890", "1234567890", 1000, "Lønnskonto", "NOK", transaksjonsGenerator(1)));

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_terskel()     {

        // Generer 100 000 kontoer med samme personnummer;
        ArrayList<Konto> kontoer = kontoGenerator(2, "1234567890");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(kontoer);

        List<Konto> resultat = bankController.hentSaldi();

        assertNotNull(resultat);
        assertEquals(kontoer, resultat);
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
    i mens hentBetalinger henter liste med transaksjoner basert på personnummer.
*/

  @Test
    public void hentBetalinger_loggetInn()    {

  }



    // HJELPE-METODER TIL Å FULLFØRE TESTER
    private static ArrayList<Konto> kontoGenerator(long antall)   {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            //kontoer.add(new Konto(tilfeldigString(11), tilfeldigString(11), tilfeldigDouble(5), "Lønnskonto", "NOK", null));
            kontoer.add(new Konto());
        }

        return kontoer;
    }

    //Lager n antall kontoer til samme person
    private static ArrayList<Konto> kontoGenerator(long antall, String personnummer)    {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kontoer.add(new Konto(personnummer, "1234567890", 1000, "Lønnskonto", "NOK", transaksjonsGenerator(10)));
        }

        return kontoer;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    private static ArrayList<Transaksjon> transaksjonsGenerator(int antall)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            //transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), 100, "", tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
            transaksjoner.add(new Transaksjon());
        }

        return transaksjoner;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    private static ArrayList<Transaksjon> transaksjonsGenerator(int antall, String kontonr)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            //transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), 100, "", tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
            transaksjoner.add(new Transaksjon(10, "0987654321", 100, "29-01-2002", "Hei", "Hei2", kontonr));
        }

        return transaksjoner;
    }

    // JEG PRØVDE Å BRUKE DE UNDER, MEN FUNGERER IKKE, SKAL FIKSE SENERE SÅ BLIR DET 100% ORDENTLIG TEST :D

    private static String tilfeldigString(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return String.valueOf(randomNumber).substring(1);
    }

    private static int tilfeldigInt(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return (int) randomNumber;
    }

    private static double tilfeldigDouble(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return (double) randomNumber;
    }

}

