package controller;

import org.junit.jupiter.api.Test;
import service.TurmaService;
import model.Turma;
import repository.TurmaRepository;
import static org.junit.jupiter.api.Assertions.*;

class TurmaControllerTest {
    @Test
    void testCriarEBuscarTurma() {
        TurmaRepository repo = new TurmaRepository();
        TurmaService service = new TurmaService(repo);
        TurmaController controller = new TurmaController(service);
        int id = controller.criar("Teste", 2025);
        assertTrue(controller.buscar(id).isPresent());
        assertEquals("Teste", controller.buscar(id).get().getSerie());
        assertEquals(2025, controller.buscar(id).get().getAnoLetivo());
    }
}
