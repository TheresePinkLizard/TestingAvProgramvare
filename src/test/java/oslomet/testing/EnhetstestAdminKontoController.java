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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {


    @InjectMocks
    private AdminKontoController kontoController;

    @Mock
    private AdminRepository aRepository;


    // hent alle
    @Test
    public void hentAlleKontoTest() {
        // lage en list med kontoer
        List<Konto> kontoer = Arrays.asList(new Konto(), new Konto(), new Konto());
        //kjører en mock når
        Mockito.when(aRepository.hentAlleKonti(anyString())).thenReturn(kontoer);
        //henter faktiske kontoer
        List<Konto> faktiskeKontoer = aRepository.hentAlleKonti();
        // sammenligner
        assertEquals(kontoer, faktiskeKontoer);
    }

    //registrer
    @Test
    public void registrerKontoTest(){

    }
    public void registrerKontoException(){

    }
    public void registrerKontoReturn(){

    }
    //endre
    @Test
    public void endreKontoTest(){

    }
    public void endreKontoException(){

    }
    public void endreKontoReturn(){

    }

    //slett
    @Test
    public void slettKontoTest(){

    }
    public void slettKontoException(){

    }
    public void slettKontoReturn(){

    }
}
