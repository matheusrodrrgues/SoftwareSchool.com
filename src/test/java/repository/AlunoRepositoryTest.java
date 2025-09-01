package repository;

import org.junit.jupiter.api.Test;
import model.Aluno;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class AlunoRepositoryTest {
    @Test
    void testCrudOperations() {
        AlunoRepository repo = new AlunoRepository();
        Aluno aluno = new Aluno();
        aluno.setNome("Teste");
        int id = repo.save(null, aluno);
        assertEquals(Optional.of(aluno), repo.buscarId(id));
        assertTrue(repo.findAll().contains(aluno));
        assertTrue(repo.deletar(id));
        assertFalse(repo.buscarId(id).isPresent());
    }
}

