package oslomet.testing;

import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;

import java.util.ArrayList;
import java.util.Random;


public class Hjelp {

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    public static ArrayList<Konto> kontoGenerator(long antall)   {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kontoer.add(new Konto(tilfeldigString(11), tilfeldigString(11), tilfeldigDouble(5), "Lønnskonto", "NOK", transaksjonsGenerator(10)));
        }

        return kontoer;
    }

    //Lager n antall kontoer til samme person
    public static ArrayList<Konto> kontoGenerator(long antall, String personnummer)    {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kontoer.add(new Konto(personnummer, tilfeldigString(11), tilfeldigDouble(5), "Lønnskonto", "NOK", transaksjonsGenerator(10)));
        }

        return kontoer;
    }

    public static ArrayList<Kunde> kundeGenerator(long antall)   {
        ArrayList<Kunde> kunder = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kunder.add(new Kunde(tilfeldigString(2), tilfeldigString(7), tilfeldigString(7), tilfeldigString(12), tilfeldigString(4), tilfeldigString(6), tilfeldigString(8), tilfeldigString(20)));
            if (i < 20) {
                System.out.println(kunder.get(i).getFornavn());
            }
        }

        return kunder;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    public static ArrayList<Transaksjon> transaksjonsGenerator(int antall)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), 100, "", tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
        }

        return transaksjoner;
    }

    public static ArrayList<Transaksjon> transaksjonsGenerator(int antall, String kontonr)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            transaksjoner.add(new Transaksjon(10, tilfeldigString(11), tilfeldigDouble(3), "", tilfeldigString(10), "1", kontonr));
        }

        return transaksjoner;
    }

    private static String tilfeldigString(int n) {
        String tegn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
        + "abcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder(n);
        for (int y = 0; y < n; y++) {
            int index = (int)(tegn.length() * Math.random());
            s.append(tegn.charAt(index));
        }
        return s.toString();
    }

    private static int tilfeldigInt(int n) {
        String tegn = "0123456789";

        StringBuilder s = new StringBuilder(n);
        for (int y = 0; y < n; y++) {
            int index = (int)(tegn.length() * Math.random());
            s.append(tegn.charAt(index));
        }
        return Integer.parseInt(s.toString());
    }


    private static double tilfeldigDouble(int n) {
        String tegn = "0123456789";

        StringBuilder s = new StringBuilder(n);
        for (int y = 0; y < n; y++) {
            int index = (int)(tegn.length() * Math.random());
            s.append(tegn.charAt(index));
        }
        return Double.parseDouble(s.toString());
    }
}
