package com.keyin.cli;

import com.keyin.cli.Main.BackendApi;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MainTest {

    // Manual mock, no extension needed
    private final BackendApi api = mock(BackendApi.class);

    private String captureOut(Runnable r) {
        var old = System.out;
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try { r.run(); } finally { System.setOut(old); }
        return out.toString();
    }

    @Test
    void q1_showAirportsByCity_printsAirportsForEachCity() throws Exception {
        var c1 = new City(); c1.setId(1L); c1.setName("Boston");
        var c2 = new City(); c2.setId(2L); c2.setName("Toronto");

        var bos1 = new Airport(); bos1.setName("Logan International"); bos1.setCode("BOS");
        var tor1 = new Airport(); tor1.setName("Pearson");             tor1.setCode("YYZ");

        when(api.getCities()).thenReturn(List.of(c1, c2));
        when(api.getAirportsByCityId(1L)).thenReturn(List.of(bos1));
        when(api.getAirportsByCityId(2L)).thenReturn(List.of(tor1));

        String text = captureOut(() -> {
            try { Main.showAirportsByCity(api); } catch (Exception ignored) {}
        });

        assertTrue(text.contains("Boston ->"));
        assertTrue(text.contains("Toronto ->"));
        assertTrue(text.contains("Logan International (BOS)"));
        assertTrue(text.contains("Pearson (YYZ)"));

        verify(api).getCities();
        verify(api).getAirportsByCityId(1L);
        verify(api).getAirportsByCityId(2L);
    }

    @Test
    void q2_showAircraftByPassenger_printsAircraftForEachPassenger() throws Exception {
        var p1 = new Passenger(); p1.setId(1L); p1.setFirstName("Alice"); p1.setLastName("Smith");
        var p2 = new Passenger(); p2.setId(2L); p2.setFirstName("Bob");   p2.setLastName("Johnson");

        var ac1 = new Aircraft(); ac1.setType("A320"); ac1.setAirlineName("Delta");
        var ac2 = new Aircraft(); ac2.setType("737");  ac2.setAirlineName("United");

        when(api.getPassengers()).thenReturn(List.of(p1, p2));
        when(api.getAircraftByPassenger(1L)).thenReturn(List.of(ac1));
        when(api.getAircraftByPassenger(2L)).thenReturn(List.of(ac2));

        String text = captureOut(() -> {
            try { Main.showAircraftByPassenger(api); } catch (Exception ignored) {}
        });

        assertTrue(text.contains("Alice Smith flew on:"));
        assertTrue(text.contains("Bob Johnson flew on:"));
        assertTrue(text.contains("A320 (Delta)"));
        assertTrue(text.contains("737 (United)"));

        verify(api).getPassengers();
        verify(api).getAircraftByPassenger(1L);
        verify(api).getAircraftByPassenger(2L);
    }

    @Test
    void q3_showAirportsByAircraft_printsAirportsForEachAircraft() throws Exception {
        var a1 = new Aircraft(); a1.setId(10L); a1.setType("A320"); a1.setAirlineName("Delta");
        var a2 = new Aircraft(); a2.setId(20L); a2.setType("737");  a2.setAirlineName("United");

        var jfk = new Airport(); jfk.setName("John F. Kennedy"); jfk.setCode("JFK");
        var lax = new Airport(); lax.setName("Los Angeles");      lax.setCode("LAX");

        when(api.getAircraft()).thenReturn(List.of(a1, a2));
        when(api.getAirportsByAircraft(10L)).thenReturn(List.of(jfk));
        when(api.getAirportsByAircraft(20L)).thenReturn(List.of(lax));

        String text = captureOut(() -> {
            try { Main.showAirportsByAircraft(api); } catch (Exception ignored) {}
        });

        assertTrue(text.contains("A320 (Delta) ->"));
        assertTrue(text.contains("737 (United) ->"));
        assertTrue(text.contains("John F. Kennedy (JFK)"));
        assertTrue(text.contains("Los Angeles (LAX)"));

        verify(api).getAircraft();
        verify(api).getAirportsByAircraft(10L);
        verify(api).getAirportsByAircraft(20L);
    }

    @Test
    void q4_showAirportsByPassenger_printsAirportsUsedByEachPassenger() throws Exception {
        var p1 = new Passenger(); p1.setId(7L); p1.setFirstName("Eve");  p1.setLastName("Brown");
        var p2 = new Passenger(); p2.setId(8L); p2.setFirstName("Ned");  p2.setLastName("Green");

        var yyz = new Airport(); yyz.setName("Pearson");     yyz.setCode("YYZ");
        var yyt = new Airport(); yyt.setName("St. John’s");  yyt.setCode("YYT");

        when(api.getPassengers()).thenReturn(List.of(p1, p2));
        when(api.getAirportsByPassenger(7L)).thenReturn(List.of(yyz));
        when(api.getAirportsByPassenger(8L)).thenReturn(List.of(yyt));

        String text = captureOut(() -> {
            try { Main.showAirportsByPassenger(api); } catch (Exception ignored) {}
        });

        assertTrue(text.contains("Eve Brown used airports:"));
        assertTrue(text.contains("Ned Green used airports:"));
        assertTrue(text.contains("Pearson (YYZ)"));
        assertTrue(text.contains("St. John’s (YYT)"));

        verify(api).getPassengers();
        verify(api).getAirportsByPassenger(7L);
        verify(api).getAirportsByPassenger(8L);
    }
}
