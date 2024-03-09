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
        // ARRANGE:
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        // ACT:
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHei");

        // ASSERT:
        assertEquals("OK", resultat);
        verify(session).setAttribute("Innlogget", "12345678901");
    }

    @Test
    public void testSjekkLoggInn_altFeil()    {
        // ARRANGE:
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // ACT:
        // Feil passord:
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "NeiNei");

        // ASSERT:
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    // Feil passord i følge regex:
    @Test
    public void testSjekkLoggInn_feilPassord()    {
        // ARRANGE:
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // ACT:
        // Feil passord, er under 6 tegn:
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "Hei");

        // ASSERT:
        assertEquals("Feil i passord", resultat);
    }


    @Test
    public void testSjekkLoggInn_feilPersonnummer()    {
        // ARRANGE:
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // ACT:
        // Feil personnummer i følge regex, er under 6 tegn:
        String resultat = sikkerhetsController.sjekkLoggInn("123", "HeiHei");

        // ASSERT:
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void testInnlogget() {
        // ARRANGE:
        session.setAttribute("Innlogget", "12345678901");

        // ACT:
        String resultat = sikkerhetsController.loggetInn();

        // ASSERT:
        assertEquals("12345678901", resultat);
        verify(session).setAttribute("Innlogget", "12345678901");
    }

    @Test
    public void testInnlogget_altFeil()    {
        // ARRANGE:
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // ACT:
        String resultat = sikkerhetsController.loggetInn();

        // ASSERT:
        assertNull(resultat);
    }

    @Test
    public void testLoggInnAdmin(){
        // ARRANGE:
        session.setAttribute("Innlogget", "Admin");

        // ACT:
        String resultat = sikkerhetsController.loggetInn();

        // ASSERT:
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


