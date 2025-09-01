package repository;

import org.junit.jupiter.api.Test;
import model.Professor;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class ProfessorRepositoryTest {
    @Test
    void testCrudOperations() {
        ProfessorRepository repo = new ProfessorRepository();
        Professor prof = new Professor();
        prof.setNome("Teste");
        int id = repo.save(null, prof);
        assertEquals(Optional.of(prof), repo.buscarId(id));
        assertTrue(repo.findAll().contains(prof));
        assertTrue(repo.deletar(id));
        assertFalse(repo.buscarId(id).isPresent());
    }
}

