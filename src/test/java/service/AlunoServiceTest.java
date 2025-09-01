package service;

import model.Aluno;
import model.Endereco;
import model.Professor;
import model.Responsavel;
import repository.AlunoRepository;
import repository.ResponsavelRepository;
import repository.TurmaRepository;
import repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AlunoServiceTest {
    @Test
    void testCriarComResponsavelInvalido() {
        AlunoRepository repo = new AlunoRepository();
        ResponsavelRepository respRepo = new ResponsavelRepository();
        TurmaRepository turmaRepo = new TurmaRepository();
        ProfessorRepository profRepo = new ProfessorRepository();
        ResponsavelRepository respRepo2 = new ResponsavelRepository();
        // Adiciona uma turma válida
        int turmaId = turmaRepo.save(null, new model.Turma());
        // Não adiciona responsável nem professor
        AlunoService service = new AlunoService(repo, respRepo, turmaRepo, profRepo, respRepo2);
        assertThrows(IllegalArgumentException.class, () -> service.criar("Teste", LocalDate.now(), "Cidade", new Endereco(), 1, false, turmaId));
    }
}
