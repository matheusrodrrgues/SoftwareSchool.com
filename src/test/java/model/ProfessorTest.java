package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    @Test
    void testGettersAndSetters() {
        Professor prof = new Professor();
        prof.setId(1);
        prof.setNome("Carlos");
        prof.setDataNascimento(LocalDate.of(1975, 6, 15));
        prof.setTelefone("75988888888");
        Endereco end = new Endereco("Rua B", "Centro", "44000-001", "Feira", "BA");
        prof.setEndereco(end);
        prof.setFormacao("Pedagogia");
        List<Integer> alunos = new ArrayList<>();
        alunos.add(200);
        prof.getAlunosDependentesIds().clear();
        prof.getAlunosDependentesIds().addAll(alunos);
        List<Integer> turmas = new ArrayList<>();
        turmas.add(300);
        prof.getTurmasIds().clear();
        prof.getTurmasIds().addAll(turmas);
        prof.addTurma(301);

        assertEquals(1, prof.getId());
        assertEquals("Carlos", prof.getNome());
        assertEquals(LocalDate.of(1975, 6, 15), prof.getDataNascimento());
        assertEquals("75988888888", prof.getTelefone());
        assertEquals(end, prof.getEndereco());
        assertEquals("Pedagogia", prof.getFormacao());
        assertEquals(alunos, prof.getAlunosDependentesIds());
        assertTrue(prof.getTurmasIds().contains(300));
        assertTrue(prof.getTurmasIds().contains(301));
    }
}
