package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {
    @Test
    void testGettersAndSetters() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João Silva");
        LocalDate nascimento = LocalDate.of(2010, 5, 20);
        aluno.setDataNascimento(nascimento);
        aluno.setNaturalidade("Feira de Santana");
        aluno.setProfessorId(10);
        aluno.setResponsavelId(20);
        aluno.setTurmaId(30);
        aluno.setResponsavelProfessor(true);

        assertEquals(1, aluno.getId());
        assertEquals("João Silva", aluno.getNome());
        assertEquals(nascimento, aluno.getDataNascimento());
        assertEquals("Feira de Santana", aluno.getNaturalidade());
        assertEquals(10, aluno.getProfessorId());
        assertEquals(20, aluno.getResponsavelId());
        assertEquals(30, aluno.getTurmaId());
        assertTrue(aluno.isResponsavelProfessor());
    }
}
