package service;

import model.Turma;
import repository.TurmaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TurmaServiceTest {
    @Test
    void testCriarTurma() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        int id = service.criar("Teste", 2025);
        Turma turma = repo.buscarId(id).orElse(null);
        assertNotNull(turma);
        assertEquals("Teste", turma.getSerie());
        assertEquals(2025, turma.getAnoLetivo());
    }

    @Test
    void testBuscarTurma() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        int id = service.criar("Teste", 2025);
        assertTrue(service.buscar(id).isPresent());
        assertEquals("Teste", service.buscar(id).get().getSerie());
    }

    @Test
    void testListarTurmas() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        service.criar("Teste1", 2025);
        service.criar("Teste2", 2026);
        assertEquals(2, service.listar().size());
    }

    @Test
    void testAtualizarTurma() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        int id = service.criar("Teste", 2025);
        boolean atualizado = service.atualizar(id, "NovaSerie", 2030, 99);
        Turma turma = repo.buscarId(id).orElse(null);
        assertTrue(atualizado);
        assertNotNull(turma);
        assertEquals("NovaSerie", turma.getSerie());
        assertEquals(2030, turma.getAnoLetivo());
        assertEquals(99, turma.getProfessorId());
    }

    @Test
    void testAtualizarTurmaInexistente() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        boolean atualizado = service.atualizar(999, "NovaSerie", 2030, 99);
        assertFalse(atualizado);
    }
}
