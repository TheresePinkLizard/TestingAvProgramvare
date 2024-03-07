package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetController {

    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    private BankRepository repository;

    @Mock

    MockHttpSession session;

    @Before
    // Nødvendig for å sette en session-attributt før kallet til controlleren.
    public void initSession(){
        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

    }

    @Test
    public void testSjekkLoggInn() {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHei");

        assertEquals("OK", resultat);
        verify(session).setAttribute("Innlogget", "12345678901");
    }

    @Test
    public void testSjekkLoggInn_altFeil()    {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // Feil passord:
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "NeiNei");

        assertEquals("Feil i personnummer eller passord", resultat);
    }

    // Feil passord i følge regex:
    @Test
    public void testSjekkLoggInn_feilPassord()    {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // Feil passord, er under 6 tegn:
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "Hei");

        assertEquals("Feil i passord", resultat);
    }

    // Feil personnummer i følge regex:
    @Test
    public void testSjekkLoggInn_feilPersonnummer()    {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // Feil personnummer, er under 6 tegn:
        String resultat = sikkerhetsController.sjekkLoggInn("123", "HeiHei");

        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void testInnlogget() {
        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.loggetInn();

        assertEquals("12345678901", resultat);
        verify(session).setAttribute("Innlogget", "12345678901");
    }

    @Test
    public void testInnlogget_altFeil()    {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        String resultat = sikkerhetsController.loggetInn();

        assertNull(resultat);
    }

    @Test
    public void testLoggInnAdmin(){
        session.setAttribute("Innlogget", "Admin");

        String resultat = sikkerhetsController.loggetInn();

        assertEquals("Admin", resultat);
        verify(session).setAttribute("Innlogget", "Admin");
    }

    @Test
    public void testLoggUt(){
        sikkerhetsController.loggUt();

        // Sjekker om session er blitt endret:
        verify(session).setAttribute("Innlogget", null);
    }

}


