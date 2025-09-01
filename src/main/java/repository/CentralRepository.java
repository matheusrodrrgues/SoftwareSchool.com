// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package repository;

// Importando bibliotecas
import java.util.*;

/**
 * Repository genérico para gerenciar entidades por ID.
 * Oferece métodos CRUD para qualquer tipo de objeto.
 */
public class CentralRepository<T> {
    protected final Map<Integer, T> dados = new HashMap<>();
    protected int nextId = 1;

    /**
     * Salva uma entidade. Se id for nulo, cria novo id. Se id existir, atualiza.
     */
    public int save(Integer id, T entidade) {
        if (id == null) {
            id = nextId++;
        }
        dados.put(id, entidade);
        return id;
    }

    /**
     * Busca uma entidade pelo ID.
     */
    public Optional<T> buscarId(Integer id) {
        return Optional.ofNullable(dados.get(id));
    }

    /**
     * Lista todas as entidades cadastradas.
     */
    public List<T> findAll() {
        return new ArrayList<>(dados.values());
    }

    /**
     * Verifica se existe uma entidade pelo ID.
     */
    public boolean existsById(Integer id) {
        return dados.containsKey(id);
    }

    /**
     * Remove uma entidade pelo ID.
     */
    public boolean deleteById(Integer id) {
        return dados.remove(id) != null;
    }

    /**
     * Remove uma entidade pelo ID (alias para deleteById).
     */
    public boolean deletar(Integer id) {
        return deleteById(id);
    }
}
