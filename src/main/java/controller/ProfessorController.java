/**
 * Controller responsável por operações relacionadas ao Professor.
 * Recebe requisições da camada de visão e delega para o serviço.
 * Útil para estudantes entenderem o padrão MVC e manipulação de professores.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */

// Pacotes MVC
package controller;
import model.Aluno;
import model.Endereco;
import model.Professor;
import service.ProfessorService;
import service.AlunoService;

// Importando Bibliotecas
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Início do código
public class ProfessorController {
    private final ProfessorService service;
    private final AlunoService alunoService;

    public ProfessorController(ProfessorService s, AlunoService a) {
        this.service = s;
        this.alunoService = a;
    }
    /**
     * Cria um novo professor com os dados informados.
     * @param nome Nome do professor
     * @param formacao Formação do professor
     * @param tel Telefone
     * @param dn Data de nascimento
     * @param turmaId ID da turma
     * @param end Endereço
     * @return ID do professor criado
     */
    public int criar(String nome, String formacao, String tel, LocalDate dn, Integer turmaId, Endereco end) {
        return service.criar(nome, formacao, tel, dn, turmaId, end);
    }
    /**
     * Busca um professor pelo ID.
     * @param id ID do professor
     * @return Optional contendo o professor, se encontrado
     */
    public Optional<Professor> buscar(Integer id){
        return service.buscar(id);
    }
    /**
     * Lista todos os professores cadastrados.
     * @return lista de professores
     */
    public List<Professor> listar(){
        return service.listar();
    }
    /**
     * Atualiza os dados de um professor existente.
     * @param id ID do professor
     * @param nome Novo nome
     * @param formacao Nova formação
     * @param telefone Novo telefone
     * @param end Novo endereço
     * @param nasc Nova data de nascimento
     * @param respId Novo responsável
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Integer id, String nome, String formacao, String telefone, Endereco end, LocalDate nasc, Integer respId) {
        return service.atualizar(id, nome, formacao, telefone, end, nasc, respId);
    }
    /**
     * Adiciona um aluno dependente ao professor.
     * @param profId ID do professor
     * @param alunoId ID do aluno
     */
    public void adicionarDependente(int profId, int alunoId){
        service.adicionarDependente(profId, alunoId);
    }
    /**
     * Remove um aluno dependente do professor.
     * @param profId ID do professor
     * @param alunoId ID do aluno
     */
    public void removerDependente(int profId, int alunoId){
        service.removerDependente(profId, alunoId);
    }
    /**
     * Remove um professor pelo ID, excluindo também os alunos vinculados.
     * @param id ID do professor
     * @return true se removido com sucesso
     */
    public boolean deletar(int id) {
        List<Aluno> alunosVinculados = alunoService.listar().stream()
                .filter(a -> a.getResponsavelId() == id && a.isResponsavelProfessor()).toList();
        for (Aluno aluno : alunosVinculados) {
            alunoService.deletar(aluno.getId());
        }
        return service.deletar(id);
    }
        public void vincularTurma(Integer profId, Integer turmaId) {
        service.buscar(profId).ifPresent(p -> p.addTurma(turmaId));
    }
}
