package repository;

import org.junit.jupiter.api.Test;
import model.Turma;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class TurmaRepositoryTest {
    @Test
    void testCrudOperations() {
        TurmaRepository repo = new TurmaRepository();
        Turma turma = new Turma();
        turma.setSerie("Teste");
        int id = repo.save(null, turma);
        assertEquals(Optional.of(turma), repo.buscarId(id));
        assertTrue(repo.findAll().contains(turma));
        assertTrue(repo.deletar(id));
        assertFalse(repo.buscarId(id).isPresent());
    }
}

