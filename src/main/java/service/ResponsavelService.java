// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package service;
import model.Endereco;
import model.Responsavel;
import repository.ResponsavelRepository;
import repository.AlunoRepository;

// Importando Bibliotecas
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service responsável pela lógica de negócio relacionada ao Responsável.
 * Realiza validações e manipulação de dados antes de acessar o repository.
 * Útil para estudantes entenderem o padrão Service.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */
// Início do código
public class ResponsavelService {
    private final ResponsavelRepository repo;
    private final AlunoRepository alunoRepo;

    public ResponsavelService(ResponsavelRepository repo, AlunoRepository alunoRepo) {
        this.repo = repo;
        this.alunoRepo = alunoRepo;
    }
    /**
     * Cria um novo responsável com os dados informados.
     * @param nome Nome do responsável
     * @param telefone Telefone
     * @param dn Data de nascimento
     * @param nat Naturalidade
     * @param end Endereço
     * @return ID do responsável criado
     */
    public int criar(String nome, String telefone, LocalDate dn, String nat, Endereco end) {
        Responsavel r = new Responsavel();
        r.setNome(nome);
        r.setTelefone(telefone);
        r.setEndereco(end);
        r.setNat(nat);
        r.setDataNascimento(dn);

        int id = repo.save(null, r);
        r.setId(id); return id;
    }
    /**
     * Busca um responsável pelo ID.
     * @param id ID do responsável
     * @return Optional com o responsável encontrado
     */
    public Optional<Responsavel> buscar(Integer id) {
        return repo.buscarId(id);
    }
    /**
     * Lista todos os responsáveis cadastrados.
     * @return lista de responsáveis
     */
    public List<Responsavel> listar() {
        return repo.findAll();
    }
    /**
     * Atualiza os dados de um responsável existente.
     * @param id ID do responsável
     * @param nome Novo nome
     * @param telefone Novo telefone
     * @param dn Nova data de nascimento
     * @param nat Nova naturalidade
     * @param end Novo endereço
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Integer id, String nome, String telefone, LocalDate dn, String nat, Endereco end) {
        return repo.buscarId(id).map(r -> {
            if (nome != null) r.setNome(nome);
            if (telefone != null) r.setTelefone(telefone);
            if (dn != null) r.setDataNascimento(dn);
            if (nat != null); r.setNat(nat);
            if (end != null); r.setEndereco(end);
            repo.save(id, r);
            return true;
        }).orElse(false);
    }
    // ResponsavelService
    public int criarComIdExistente(Responsavel r) {
        if (repo.existsById(r.getId())) return r.getId();
        repo.save(r.getId(), r);
        return r.getId();
    }
    public int criarComoProfessor(int profId, String nome, String telefone, LocalDate dn, String nat, Endereco end) {
        // Verifica se já existe
        if (repo.existsById(profId)) return profId;

        Responsavel r = new Responsavel();
        r.setId(profId);
        r.setNome(nome);
        r.setTelefone(telefone);
        r.setDataNascimento(dn);
        r.setNat(nat);
        r.setEndereco(end);

        repo.save(profId, r);
        return profId;
    }
    /**
     * Remove um responsável pelo ID.
     * @param id ID do responsável
     * @return true se removido com sucesso
     */
    public boolean deletar(Integer id) {
        if (!repo.existsById(id)) return false;
        var alunos = alunoRepo.findAll().stream()
                .filter(a -> a.getResponsavelId() != null && a.getResponsavelId().equals(id))
                .toList();
        for (var aluno : alunos) {
            alunoRepo.deleteById(aluno.getId());
}
        repo.deleteById(id);
        return true;
    }
}