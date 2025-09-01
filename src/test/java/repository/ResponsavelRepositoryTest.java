package repository;

import org.junit.jupiter.api.Test;
import model.Responsavel;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class ResponsavelRepositoryTest {
    @Test
    void testCrudOperations() {
        ResponsavelRepository repo = new ResponsavelRepository();
        Responsavel resp = new Responsavel();
        resp.setNome("Teste");
        int id = repo.save(null, resp);
        assertEquals(Optional.of(resp), repo.buscarId(id));
        assertTrue(repo.findAll().contains(resp));
        assertTrue(repo.deletar(id));
        assertFalse(repo.buscarId(id).isPresent());
    }
}

