package service;

import model.Endereco;
import repository.ProfessorRepository;
import repository.TurmaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class ProfessorServiceTest {
    @Test
    void testCriarComTurmaInvalida() {
        ProfessorRepository repo = new ProfessorRepository();
        TurmaRepository turmaRepo = new TurmaRepository();
        AlunoService alunoService = null; // Não é usado neste teste
        ProfessorService service = new ProfessorService(repo, turmaRepo, alunoService);
        // Não adiciona turma, então turmaId 1 é inválido
        assertThrows(IllegalArgumentException.class, () -> service.criar("Teste", "Formacao", "Tel", LocalDate.now(), 1, new Endereco()));
    }
}
