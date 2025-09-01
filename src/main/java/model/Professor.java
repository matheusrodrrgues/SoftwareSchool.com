/**
 * Classe que representa um professor da escolinha.
 * Herda de Responsavel e adiciona dados de formação, turmas e alunos dependentes.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */
// Pacotes MVC
package model;

// Importando bibliotecas
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Responsavel {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private Endereco endereco;
    private String formacao;
    private List<Integer> alunosDependentesIds = new ArrayList<>();
    private List<Integer> turmasIds = new ArrayList<>();
    /**
     * Adiciona uma turma à lista de turmas do professor.
     * @param turmaId ID da turma
     */
    public void addTurma(int turmaId) {
        turmasIds.add(turmaId);
    }
    /**
     * Retorna a lista de IDs das turmas do professor.
     * @return lista de IDs das turmas
     */
    public List<Integer> getTurmasIds() {
        return turmasIds;
    }
    /**
     * Retorna a lista de IDs dos alunos dependentes do professor.
     * @return lista de IDs dos alunos dependentes
     */
    public List<Integer> getAlunosDependentesIds() {
        return alunosDependentesIds;
    }
    /**
     * Adiciona um aluno dependente à lista, se ainda não estiver presente.
     * @param alunoId ID do aluno dependente
     */
    public void addAlunosDependentes(Integer alunoId) {
        if (!alunosDependentesIds.contains(alunoId)) {
            alunosDependentesIds.add(alunoId);
        }
    }
    /**
     * Retorna uma representação em texto do professor.
     * @return String com dados do professor
     */
    @Override public String toString() {
        return "Professor{"+"id="+id+", nome='"+nome+'\''+", tel='"+telefone+'\''+", formacao='"+formacao+'\''+", turmasIds="+turmasIds+", dependentes="+alunosDependentesIds+'}';
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }
    /**
     * Retorna a formação do professor.
     * @return formação
     */
    public String getFormacao() {
        return formacao;
    }
}