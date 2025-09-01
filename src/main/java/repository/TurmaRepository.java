// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package repository;
import model.Turma;

// Importando biblioteca
import java.util.*;

/**
 * Repository para gerenciar objetos Turma.
 * Implementa métodos CRUD próprios usando um Map.
 */
// Início do código
public class TurmaRepository {
    private final Map<Integer, Turma> dados = new HashMap<>();
    private int nextId = 1;

    public int save(Integer id, Turma t) {
        if (id == null) {
            id = nextId++;
        }
        t.setId(id);
        dados.put(id, t);
        return id;
    }

    public Optional<Turma> buscarId(Integer id) {
        return Optional.ofNullable(dados.get(id));
    }

    public List<Turma> findAll() {
        return new ArrayList<>(dados.values());
    }

    public boolean deletar(Integer id) {
        return dados.remove(id) != null;
    }
}
