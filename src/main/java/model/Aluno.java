// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package model;

// Importando Bibliotecas
import java.time.LocalDate;

/**
 * Classe que representa um aluno da escolinha.
 * Armazena dados pessoais, endereço, turma e responsáveis.
 * @version 1.0
 */
// Início do código
public class Aluno {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String naturalidade;
    private Endereco endereco;
    private Integer responsavelId;
    private Integer turmaId;
    private Integer professorId;  // novo campo
    private boolean responsavelProfessor;

    /**
     * Obtém o ID do professor associado ao aluno.
     * @return professorId
     */
    public Integer getProfessorId() {
        return professorId;
    }

    /**
     * Define o ID do professor associado ao aluno.
     * @param professorId ID do professor a ser associado
     */
    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    /**
     * Obtém o ID do aluno.
     * @return id do aluno
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do aluno.
     * @param id ID a ser definido para o aluno
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome do aluno.
     * @return nome do aluno
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do aluno.
     * @param nome nome a ser definido para o aluno
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a data de nascimento do aluno.
     * @return data de nascimento do aluno
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Define a data de nascimento do aluno.
     * @param dataNascimento data de nascimento a ser definida para o aluno
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Obtém a naturalidade do aluno.
     * @return naturalidade do aluno
     */
    public String getNaturalidade() {
        return naturalidade;
    }

    /**
     * Define a naturalidade do aluno.
     * @param naturalidade naturalidade a ser definida para o aluno
     */
    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    /**
     * Obtém o endereço do aluno.
     * @return objeto Endereco representando o endereço do aluno
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do aluno.
     * @param endereco objeto Endereco a ser associado ao aluno
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * Obtém o ID do responsável pelo aluno.
     * @return responsavelId
     */
    public Integer getResponsavelId() {
        return responsavelId;
    }

    /**
     * Define o ID do responsável pelo aluno.
     * @param responsavelId ID do responsável a ser associado ao aluno
     */
    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    /**
     * Indica se o responsável pelo aluno é um professor.
     * @return true se o responsável é professor, false caso contrário
     */
    public boolean isResponsavelProfessor() {
        return responsavelProfessor;
    }

    /**
     * Define se o responsável pelo aluno é um professor.
     * @param responsavelProfessor true se for professor, false caso contrário
     */
    public void setResponsavelProfessor(boolean responsavelProfessor) {
        this.responsavelProfessor = responsavelProfessor;
    }

    /**
     * Obtém o ID da turma do aluno.
     * @return turmaId
     */
    public Integer getTurmaId() {
        return turmaId;
    }

    /**
     * Define o ID da turma do aluno.
     * @param turmaId ID da turma a ser associada ao aluno
     */
    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }

    @Override public String toString() {
        return "Aluno{"+"id="+id+", nome='"+nome+'\''+", naturalidade='"+naturalidade+'\''+", responsavelId="+responsavelId+", turmaId="+turmaId+'}';
    }

}