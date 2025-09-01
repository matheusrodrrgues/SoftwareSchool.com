package controller;

import org.junit.jupiter.api.Test;
import service.ProfessorService;
import service.AlunoService;
import model.Professor;
import model.Endereco;
import repository.ProfessorRepository;
import repository.TurmaRepository;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ProfessorControllerTest {
    @Test
    void testCriarEBuscarProfessor() {
        ProfessorRepository profRepo = new ProfessorRepository();
        TurmaRepository turmaRepo = new TurmaRepository();
        int turmaId = turmaRepo.save(null, new model.Turma());
        AlunoService alunoService = null; // Não é usado neste teste
        ProfessorService service = new ProfessorService(profRepo, turmaRepo, alunoService);
        ProfessorController controller = new ProfessorController(service, alunoService);
        int id = controller.criar("Teste", "Formacao", "Tel", LocalDate.now(), turmaId, new Endereco());
        assertTrue(controller.buscar(id).isPresent());
        assertEquals("Teste", controller.buscar(id).get().getNome());
        assertEquals("Formacao", controller.buscar(id).get().getFormacao());
    }
}
