package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ResponsavelTest {
    @Test
    void testGettersAndSetters() {
        Responsavel resp = new Responsavel();
        resp.setId(1);
        resp.setNome("Maria");
        resp.setDataNascimento(LocalDate.of(1980, 1, 1));
        resp.setNat("Feira");
        resp.setTelefone("75999999999");
        Endereco end = new Endereco("Rua A", "Centro", "44000-000", "Feira", "BA");
        resp.setEndereco(end);
        List<Integer> dependentes = new ArrayList<>();
        dependentes.add(100);
        resp.setDependentesIds(dependentes);

        assertEquals(1, resp.getId());
        assertEquals("Maria", resp.getNome());
        assertEquals(LocalDate.of(1980, 1, 1), resp.getDataNascimento());
        assertEquals("Feira", resp.getNat());
        assertEquals("75999999999", resp.getTelefone());
        assertEquals(end, resp.getEndereco());
        assertEquals(dependentes, resp.getDependentesIds());
    }
}
