/**
 * Service responsável pela lógica de negócio relacionada ao Professor.
 * Realiza validações, vinculações e manipulação de dados antes de acessar o repository.
 * Útil para estudantes entenderem o padrão Service e regras de negócio.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */

// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package service;
import model.Endereco;
import model.Professor;
import model.Responsavel;
import repository.ProfessorRepository;
import repository.TurmaRepository;
import model.Aluno;

// Importando Bibliotecas
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Início do código
public class ProfessorService {
    private final ProfessorRepository repo;
    private final TurmaRepository turmaRepo;
    private final AlunoService aalunoService;

    public ProfessorService(ProfessorRepository repo, TurmaRepository turmaRepo, AlunoService aalunoService){
        this.repo = repo;
        this.turmaRepo = turmaRepo;
        this.aalunoService = aalunoService;
    }
    /**
     * Cria um novo professor, faz validações e vincula à turma.
     * @param nome Nome do professor
     * @param formacao Formação
     * @param tel Telefone
     * @param dn Data de nascimento
     * @param turmaId ID da turma
     * @param end Endereço
     * @return ID do professor criado
     */
    public int criar(String nome, String formacao, String tel, LocalDate dn, Integer turmaId, Endereco end) {
        if(turmaId == null || turmaRepo.buscarId(turmaId).isEmpty())
            throw new IllegalArgumentException("\nProfessor precisa estar vinculado a uma turma válida");
        Professor p = new Professor();
        p.setNome(nome);
        p.setFormacao(formacao);
        p.setTelefone(tel);
        p.setDataNascimento(dn);
        p.setEndereco(end);
        int id = repo.save(null, p);
        p.setId(id);


        turmaRepo.buscarId(turmaId).ifPresent(t -> {
            t.setProfessorId(id);
            turmaRepo.save(turmaId, t);
        });
        return id;
    }
    /**
     * Vincula um professor como responsável de um aluno.
     * Cria o responsável se não existir.
     * @param professorId ID do professor
     * @param alunoId ID do aluno
     * @param aluno Aluno
     * @param respService Serviço de responsável
     * @return true se vinculado com sucesso
     */
    public boolean vincularComoResponsavel(Integer professorId, Integer alunoId, Aluno aluno, ResponsavelService respService) {
        return repo.buscarId(professorId).map(prof -> {
            respService.buscar(professorId).orElseGet(() -> {
                Responsavel r = new Responsavel();
                r.setId(professorId);
                r.setNome(prof.getNome());
                r.setTelefone(prof.getTelefone());
                r.setDataNascimento(prof.getDataNascimento());
                r.setEndereco(prof.getEndereco());
                respService.criarComIdExistente(r);
                return r;
            });

            prof.addAlunosDependentes(alunoId);
            aluno.setResponsavelId(professorId);

            repo.save(professorId, prof);
            return true;
        }).orElse(false);
    }
    /**
     * Adiciona um aluno dependente ao professor.
     * @param profId ID do professor
     * @param alunoId ID do aluno
     */
    public void adicionarDependente(int profId, int alunoId){
        buscar(profId).ifPresent(p -> {
            // usa o alunoService para vincular
            aalunoService.vincularProfessor(alunoId, profId);
        });
    }
    /**
     * Remove um aluno dependente do professor.
     * @param profId ID do professor
     * @param alunoId ID do aluno
     */
    public void removerDependente(int profId, int alunoId){
        buscar(profId).ifPresent(p -> {
            aalunoService.vincularProfessor(alunoId, null);
        });
    }
    /**
     * Busca um professor pelo ID.
     * @param id ID do professor
     * @return Optional com o professor encontrado
     */
    public Optional<Professor> buscar(Integer id){
        return repo.buscarId(id);
    }
    /**
     * Lista todos os professores cadastrados.
     * @return lista de professores
     */
    public List<Professor> listar(){
        return repo.findAll();
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
        return repo.buscarId(id).map(p -> {
            Optional<Professor> opt = buscar(id);
            if (opt.isEmpty()) return false;

            Professor prof = opt.get();
            if (nome != null) prof.setNome(nome);
            if (formacao != null) prof.setFormacao(formacao);
            if (telefone != null) prof.setTelefone(telefone);
            if (end != null) prof.setEndereco(end);
            if (nasc != null) prof.setDataNascimento(nasc);
            if (respId != null && !p.getAlunosDependentesIds().contains(respId)) {
                p.addAlunosDependentes(respId); // respId deve ser usado apenas se for alunoId
            }
            repo.save(id, p);
            return true;
        }).orElse(false);
    }
    /**
     * Atualiza a turma do professor, validando ano letivo.
     * @param profId ID do professor
     * @param novaTurmaId ID da nova turma
     * @return true se atualizado com sucesso
     */
    public boolean atualizarTurmaProfessor(Integer profId, Integer novaTurmaId) {
        Optional<Professor> opt = repo.buscarId(profId);
        if (opt.isEmpty()) return false;
        Professor prof = opt.get();
        return turmaRepo.buscarId(novaTurmaId).map(novaTurma -> {
            for (Integer tId : prof.getTurmasIds()) {
                var turmaExistente = turmaRepo.buscarId(tId);
                if (turmaExistente.isPresent() &&
                        turmaExistente.get().getAnoLetivo().equals(novaTurma.getAnoLetivo())) {
                    System.out.println("\nO professor já possui turma neste ano letivo!");
                    return false;
                }
            }
            prof.addTurma(novaTurmaId);
            repo.save(profId, prof);

            novaTurma.setProfessorId(profId);
            turmaRepo.save(novaTurmaId, novaTurma);

            return true;
        }).orElse(false);
    }
    /**
     * Remove um professor pelo ID.
     * @param id ID do professor
     * @return true se removido com sucesso
     */
    public boolean deletar(Integer id){
        return repo.deletar(id);
    }
}
