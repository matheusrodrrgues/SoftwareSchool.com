// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela mat��ria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package controller;
import model.Endereco;
import model.Aluno;
import service.AlunoService;


// Importando bibliotecas
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Início do código
public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService s){
        this.service = s;
    }
    /**
     * Cria um novo aluno com os dados informados.
     * @param nome Nome do aluno
     * @param dn Data de nascimento
     * @param naturalidade Cidade de nascimento
     * @param responsavelId ID do responsável
     * @param responsavelProfessor Se o responsável é professor
     * @param turmaId ID da turma
     * @param end Endereço do aluno
     * @return ID do aluno criado
     */
    public int criar(String nome, LocalDate dn, String naturalidade, Integer responsavelId, boolean responsavelProfessor, Integer turmaId, Endereco end){
        return service.criar(nome, dn, naturalidade, end, responsavelId, responsavelProfessor, turmaId);
    }
    /**
     * Atualiza os dados de um aluno existente.
     * @param id ID do aluno
     * @param nome Novo nome
     * @param nasc Nova data de nascimento
     * @param nat Nova naturalidade
     * @param end Novo endereço
     * @param novaTurmaId Nova turma
     * @param novoRespId Novo responsável
     * @param respEhProf Se o responsável é professor
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Integer id, String nome, LocalDate nasc, String nat, Endereco end, Integer novaTurmaId, Integer novoRespId, boolean respEhProf) {
        return service.atualizar(id, nome, nasc, nat, end, novaTurmaId, novoRespId, respEhProf);
    }
    /**
     * Busca um aluno pelo ID.
     * @param id ID do aluno
     * @return Optional contendo o aluno, se encontrado
     */
    public Optional<Aluno> buscar(Integer id){
        return service.buscar(id);
    }
    /**
     * Lista todos os alunos cadastrados.
     * @return lista de alunos
     */
    public List<Aluno> listar(){
        return service.listar();
    }
    /**
     * Remove um aluno pelo ID.
     * @param id ID do aluno
     * @return true se removido com sucesso
     */
    public boolean deletar(Integer id){
        return service.deletar(id);
    }
}