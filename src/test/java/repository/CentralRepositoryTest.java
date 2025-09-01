package repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class CentralRepositoryTest {
    @Test
    void testCrudOperations() {
        CentralRepository<String> repo = new CentralRepository<>();
        int id = repo.save(null, "Teste");
        assertTrue(repo.existsById(id));
        assertEquals(Optional.of("Teste"), repo.buscarId(id));
        assertEquals(List.of("Teste"), repo.findAll());
        assertTrue(repo.deleteById(id));
        assertFalse(repo.existsById(id));
    }
}

