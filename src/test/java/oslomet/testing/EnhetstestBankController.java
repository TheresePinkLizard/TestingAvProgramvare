package oslomet.testing;

import org.junit.Test;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        Transaksjon transaksjon = new Transaksjon(12345, "1234567890", 1234, "15122002", "Vær så god!", "Ja", "0987654321");

        when(sjekk.loggetInn()).thenReturn("01010110523");
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
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
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
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
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
        assertEquals(konti, resultat);
    }



    // HJELPE-METODER TIL Å FULLFØRE TESTER
    private static ArrayList<Konto> kontoGenerator(long antall)   {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kontoer.add(new Konto(tilfeldigString(11), tilfeldigString(11), tilfeldigInt(5), "Lønnskonto", "NOK", null));
        }

        return kontoer;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    private static ArrayList<Transaksjon> transaksjonsGenerator(long antall)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), tilfeldigDouble(5), tilfeldigString(8), tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
        }

        return transaksjoner;
    }


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

