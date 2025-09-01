package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TurmaTest {
    @Test
    void testGettersAndSetters() {
        Turma turma = new Turma();
        turma.setId(1);
        turma.setSerie("1A");
        turma.setAnoLetivo(2025);
        turma.setProfessorId(10);
        List<Integer> alunos = new ArrayList<>();
        alunos.add(100);
        alunos.add(101);
        turma.setAlunosIds(alunos);

        assertEquals(1, turma.getId());
        assertEquals("1A", turma.getSerie());
        assertEquals(2025, turma.getAnoLetivo());
        assertEquals(10, turma.getProfessorId());
        assertEquals(alunos, turma.getAlunosIds());
    }
}

