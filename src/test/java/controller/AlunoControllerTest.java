package controller;

import org.junit.jupiter.api.Test;
import service.AlunoService;
import model.Aluno;
import model.Endereco;
import repository.AlunoRepository;
import repository.ResponsavelRepository;
import repository.TurmaRepository;
import repository.ProfessorRepository;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AlunoControllerTest {
    @Test
    void testCriarEBuscarAluno() {
        AlunoRepository alunoRepo = new AlunoRepository();
        ResponsavelRepository respRepo = new ResponsavelRepository();
        TurmaRepository turmaRepo = new TurmaRepository();
        ProfessorRepository profRepo = new ProfessorRepository();
        ResponsavelRepository respRepo2 = new ResponsavelRepository();
        int turmaId = turmaRepo.save(null, new model.Turma());
        AlunoService service = new AlunoService(alunoRepo, respRepo, turmaRepo, profRepo, respRepo2);
        AlunoController controller = new AlunoController(service);
        int id = controller.criar("Teste", LocalDate.now(), "Cidade", null, false, turmaId, new Endereco());
        assertTrue(controller.buscar(id).isPresent());
        assertEquals("Teste", controller.buscar(id).get().getNome());
    }
}
