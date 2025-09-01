package service;

import model.Endereco;
import model.Responsavel;
import repository.ResponsavelRepository;
import repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ResponsavelServiceTest {
    @Test
    void testCriarResponsavel() {
        ResponsavelRepository repo = new ResponsavelRepository();
        AlunoRepository alunoRepo = new AlunoRepository();
        ResponsavelService service = new ResponsavelService(repo, alunoRepo);
        int id = service.criar("Teste", "Tel", LocalDate.now(), "Nat", new Endereco());
        Responsavel r = repo.buscarId(id).orElse(null);
        assertNotNull(r);
        assertEquals("Teste", r.getNome());
        assertEquals("Tel", r.getTelefone());
        assertEquals("Nat", r.getNat());
        assertEquals(LocalDate.now(), r.getDataNascimento());
        assertEquals(new Endereco().toString(), r.getEndereco().toString());
    }
}
