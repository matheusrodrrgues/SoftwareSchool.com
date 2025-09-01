package controller;

import org.junit.jupiter.api.Test;
import service.ResponsavelService;
import service.AlunoService;
import model.Responsavel;
import model.Endereco;
import repository.ResponsavelRepository;
import repository.AlunoRepository;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ResponsavelControllerTest {
    @Test
    void testCriarEBuscarResponsavel() {
        ResponsavelRepository respRepo = new ResponsavelRepository();
        AlunoRepository alunoRepo = new AlunoRepository();
        ResponsavelService service = new ResponsavelService(respRepo, alunoRepo);
        AlunoService alunoService = null; // Não é usado neste teste
        ResponsavelController controller = new ResponsavelController(service, alunoService);
        int id = controller.criar("Teste", "Tel", LocalDate.now(), "Nat", new Endereco());
        assertTrue(controller.buscar(id).isPresent());
        assertEquals("Teste", controller.buscar(id).get().getNome());
        assertEquals("Tel", controller.buscar(id).get().getTelefone());
        assertEquals("Nat", controller.buscar(id).get().getNat());
    }
}
