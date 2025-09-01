/**
 * Classe que representa um responsável pelo aluno.
 * Armazena dados pessoais, endereço e lista de dependentes.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */

// Pacotes MVC
package model;

// Importando bibliotecas
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Início do código
public class Responsavel {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private String nat;
    private Endereco endereco;
    private List<Integer> dependentesIds = new ArrayList<>(); // ids de alunos

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNat() {
        return nat;
    }
    public void setNat(String nat) {
        this.nat = nat;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    /**
     * Retorna a lista de IDs dos dependentes (alunos).
     * @return lista de IDs dos dependentes
     */
    public List<Integer> getDependentesIds() {
        return dependentesIds;
    }
    /**
     * Define a lista de IDs dos dependentes (alunos).
     * @param dependentesIds lista de IDs dos dependentes
     */
    public void setDependentesIds(List<Integer> dependentesIds) {
        this.dependentesIds = dependentesIds;
    }
    /**
     * Adiciona um dependente à lista, se ainda não estiver presente.
     * @param alunoId ID do aluno dependente
     */
    public void adicionarDependente(Integer alunoId) {
        if (!dependentesIds.contains(alunoId)) {
            dependentesIds.add(alunoId);
        }
    }
    /**
     * Retorna uma representação em texto do responsável.
     * @return String com dados do responsável
     */
    @Override public String toString() {
        return "Responsavel{"+"id="+id+", nome='"+nome+'\''+", tel='"+telefone+'\''+", dependentes="+dependentesIds+'}';
    }
}