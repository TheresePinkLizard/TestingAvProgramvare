package oslomet.testing;

import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;

import java.util.ArrayList;
import java.util.Random;

public class Hjelp {

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    public static ArrayList<Konto> kontoGenerator(long antall)   {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            //kontoer.add(new Konto(tilfeldigString(11), tilfeldigString(11), tilfeldigDouble(5), "Lønnskonto", "NOK", null));
            kontoer.add(new Konto());
        }

        return kontoer;
    }

    //Lager n antall kontoer til samme person
    public static ArrayList<Konto> kontoGenerator(long antall, String personnummer)    {
        ArrayList<Konto> kontoer = new ArrayList<>();

        for (int i = 0; i < antall; i++)   {
            kontoer.add(new Konto(personnummer, "1234567890", 1000, "Lønnskonto", "NOK", transaksjonsGenerator(10)));
        }

        return kontoer;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    public static ArrayList<Transaksjon> transaksjonsGenerator(int antall)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            //transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), 100, "", tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
            transaksjoner.add(new Transaksjon(10, "0987654321", 100, "29-01-2002", "Hei", "1", "1234567890"));
        }

        return transaksjoner;
    }

    // HJELPE-METODER TIL Å FULLFØRE TESTER
    public static ArrayList<Transaksjon> transaksjonsGenerator(int antall, String kontonr)   {
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        // dato er "" fordi repository-metoden endrer det for oss
        for (int i = 0; i < antall; i++)   {
            //transaksjoner.add(new Transaksjon(tilfeldigInt(5), tilfeldigString(11), 100, "", tilfeldigString(50), tilfeldigString(5), tilfeldigString(11)));
            transaksjoner.add(new Transaksjon(10, "0987654321", 100, "29-01-2002", "Hei", "1", kontonr));
        }

        return transaksjoner;
    }

    // JEG PRØVDE Å BRUKE DE UNDER, MEN FUNGERER IKKE, SKAL FIKSE SENERE SÅ BLIR DET 100% ORDENTLIG TEST :D

    public static String tilfeldigString(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return String.valueOf(randomNumber).substring(1);
    }

    public static int tilfeldigInt(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return (int) randomNumber;
    }

    public static double tilfeldigDouble(int n) {
        long limit = (long) Math.pow(10, n);
        long randomNumber = limit + new Random().nextInt((int)limit);
        return (double) randomNumber;
    }
}
