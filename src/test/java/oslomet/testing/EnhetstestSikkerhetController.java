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
    }

    @Test
    public void testInnlogget() {

        session.setAttribute("Innlogget", "12345678901");
        String resultat = sikkerhetsController.loggetInn();
        assertEquals("12345678901", resultat);

    }

    @Test
    public void testLoggInnAdmin(){

        session.setAttribute("Innlogget", "Admin");
        String resultat = sikkerhetsController.loggetInn();
        assertEquals("Admin", resultat);

    }
}


