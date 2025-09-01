package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {
    @Test
    void testConstrutoresEToString() {
        Endereco end = new Endereco("Rua C", "Bairro D", "44000-002", "Feira", "BA");
        assertEquals("Rua C", end.getRua());
        assertEquals("Bairro D", end.getBairro());
        assertEquals("44000-002", end.getCep());
        assertEquals("Feira", end.getCidade());
        assertEquals("BA", end.getEstado());
        assertEquals("Rua C, Bairro D, CEP: 44000-002 — Feira/BA", end.toString());
    }
}
