// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package service;
import model.Aluno;
import model.Endereco;
import repository.AlunoRepository;
import repository.ResponsavelRepository;
import repository.TurmaRepository;
import repository.ProfessorRepository;

// Importando bibliotecas
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Início do código
public class AlunoService {
    private final AlunoRepository repo;
    private final ResponsavelRepository responsavelRepo;
    private final TurmaRepository turmaRepo;
    private final ProfessorRepository professorRepo;
    private final ResponsavelRepository respRepo; //

    public AlunoService(AlunoRepository repo, ResponsavelRepository rRepo, TurmaRepository tRepo, ProfessorRepository pRepo, ResponsavelRepository respRepo) {
        this.repo = repo;
        this.responsavelRepo = rRepo;
        this.turmaRepo = tRepo;
        this.professorRepo = pRepo;
        this.respRepo = respRepo;
    }
    /**
     * Cria um novo aluno, faz validações e vincula à turma e responsável.
     * @param nome Nome do aluno
     * @param nasc Data de nascimento
     * @param nat Naturalidade
     * @param end Endereço
     * @param respId ID do responsável
     * @param respProf Se o responsável é professor
     * @param turmaId ID da turma
     * @return ID do aluno criado
     */
    public int criar(String nome, LocalDate nasc, String nat, Endereco end, Integer respId, boolean respProf, Integer turmaId) {
        if(respId != null) {
            boolean respValido = (respProf && professorRepo.buscarId(respId).isPresent()) || (!respProf && responsavelRepo.buscarId(respId).isPresent());
            if(!respValido) throw new IllegalArgumentException("Aluno precisa estar vinculado a um responsável válido!");
        }
        if(turmaId == null || turmaRepo.buscarId(turmaId).isEmpty())
            throw new IllegalArgumentException("Aluno precisa estar vinculado a uma turma válida!");

        Aluno a = new Aluno();
        a.setNome(nome);
        a.setDataNascimento(nasc);
        a.setNaturalidade(nat);
        a.setEndereco(end);
        a.setTurmaId(turmaId);
        a.setResponsavelId(respId);
        a.setResponsavelProfessor(respProf);

        int id = repo.save(null, a);
        a.setId(id);

        turmaRepo.buscarId(turmaId).ifPresent(t -> {
            t.getAlunosIds().add(id);
            turmaRepo.save(turmaId, t);
        });
        return id;
    }
    /**
     * Busca um aluno pelo ID.
     * @param id ID do aluno
     * @return Optional com o aluno encontrado
     */
    public Optional<Aluno> buscar(Integer id){
        return repo.buscarId(id);
    }
    /**
     * Lista todos os alunos cadastrados.
     * @return lista de alunos
     */
    public List<Aluno> listar(){
        return repo.findAll();
    }
    /**
     * Atualiza os dados de um aluno existente, validando turma e responsável.
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
    public boolean atualizar(Integer id, String nome, LocalDate nasc, String nat, Endereco end,
                             Integer novaTurmaId, Integer novoRespId, boolean respEhProf) {
        var opt = repo.buscarId(id);
        if (opt.isEmpty()) return false;
        var a = opt.get();

        if (nome != null && !nome.isBlank()) a.setNome(nome);
        if (nasc != null) a.setDataNascimento(nasc);
        if (nat != null && !nat.isBlank()) a.setNaturalidade(nat);
        if (end != null) a.setEndereco(end);
        if (novaTurmaId != null) {
            if (turmaRepo.buscarId(novaTurmaId).isEmpty()) throw new IllegalArgumentException("Turma inválida");
            a.setTurmaId(novaTurmaId); }
        if (novoRespId != null) {
            boolean respValido = (respEhProf && professorRepo.buscarId(novoRespId).isPresent())
                    || (!respEhProf && responsavelRepo.buscarId(novoRespId).isPresent());
            if (!respValido) throw new IllegalArgumentException("Responsável inválido");
            a.setResponsavelId(novoRespId);
            a.setResponsavelProfessor(respEhProf); }
        repo.save(id, a);
        return true;
    }
    /**
     * Vincula um professor ao aluno.
     * @param alunoId ID do aluno
     * @param profId ID do professor
     */
    public void vincularProfessor(int alunoId, Integer profId){
        buscar(alunoId).ifPresent(a -> a.setProfessorId(profId));
    }
    /**
     * Remove um aluno pelo ID.
     * @param id ID do aluno
     * @return true se removido com sucesso
     */
    public boolean deletar(Integer id){
        if(!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
