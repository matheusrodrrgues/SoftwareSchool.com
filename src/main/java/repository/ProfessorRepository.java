// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package repository;
import model.Professor;

// Importando biblioteca
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar objetos Professor.
 * Implementa métodos CRUD próprios.
 */
public class ProfessorRepository {
    private final List<Professor> dados = new ArrayList<>();
    private int nextId = 1;

    /**
     * Salva um professor. Se id for nulo, cria novo id. Se id existir, atualiza.
     * @param id ID do professor (pode ser nulo para novo)
     * @param p Professor a ser salvo
     * @return ID do professor salvo
     */
    public int save(Integer id, Professor p) {
        if (id == null) {
            p.setId(nextId++);
            dados.add(p);
        } else {
            deletar(id);
            p.setId(id);
            dados.add(p);
        }
        return p.getId();
    }
    /**
     * Busca um professor pelo ID.
     * @param id ID do professor
     * @return Optional com o professor encontrado
     */
    public Optional<Professor> buscarId(Integer id) {
        return dados.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
    /**
     * Lista todos os professores cadastrados.
     * @return lista de professores
     */
    public List<Professor> findAll() {
        return new ArrayList<>(dados);
    }
    /**
     * Remove um professor pelo ID.
     * @param id ID do professor
     * @return true se removido com sucesso
     */
    public boolean deletar(Integer id) {
        return dados.removeIf(p -> p.getId().equals(id));
    }
}