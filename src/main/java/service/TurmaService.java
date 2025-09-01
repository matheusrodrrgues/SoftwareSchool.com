// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package service;
import model.Turma;
import repository.TurmaRepository;

// Importando biblioteca
import java.util.List;
import java.util.Optional;

/**
 * Service responsável pela lógica de negócio relacionada à Turma.
 * Realiza validações e manipulação de dados antes de acessar o repository.
 * Útil para estudantes entenderem o padrão Service.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */
// Início do código
public class TurmaService {
    private final TurmaRepository repo;

    public TurmaService(TurmaRepository repo){
        this.repo = repo;
    }
    /**
     * Cria uma nova turma com os dados informados.
     * @param serie Série da turma
     * @param ano Ano letivo
     * @return ID da turma criada
     */
    public int criar(String serie, Integer ano){
        Turma t = new Turma();
        t.setSerie(serie);
        t.setAnoLetivo(ano);
        return repo.save(null, t);
    }
    /**
     * Busca uma turma pelo ID.
     * @param id ID da turma
     * @return Optional com a turma encontrada
     */
    public Optional<Turma> buscar(Integer id){
        return repo.buscarId(id);
    }
    /**
     * Lista todas as turmas cadastradas.
     * @return lista de turmas
     */
    public List<Turma> listar(){
        return repo.findAll();
    }
    /**
     * Atualiza os dados de uma turma existente.
     * @param id ID da turma
     * @param serie Nova série
     * @param ano Novo ano letivo
     * @param professorId Novo professor
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Integer id, String serie, Integer ano, Integer professorId){
        Optional<Turma> opt = repo.buscarId(id);
        if(opt.isPresent()){
            Turma t = opt.get();
            if(serie != null) t.setSerie(serie);
            if(ano != null) t.setAnoLetivo(ano);

            if(professorId != null){
                if(t.getProfessorId() == null){
                    System.out.println("Adicionando professor " + professorId + " à turma " + id);
                } else {
                    System.out.println("Trocando professor " + t.getProfessorId() + " por " + professorId + " na turma " + id);
                }
                t.setProfessorId(professorId);
            }
            repo.save(id, t);
            return true;
        }
        return false;
    }
    /**
     * Remove uma turma pelo ID.
     * @param id ID da turma
     * @return true se removida com sucesso
     */
    public boolean deletar(Integer id){
        return repo.deletar(id);
    }
}
