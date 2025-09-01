/**
 * Classe que representa uma turma da escolinha.
 * Armazena série, ano letivo, professor e lista de alunos.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */

// Pacotes MVC
package model;

// Importando bibliotecas
import java.util.ArrayList;
import java.util.List;


// Início do código
public class Turma {
    private Integer id;
    private String serie;
    private Integer anoLetivo;
    private Integer professorId;
    private List<Integer> alunosIds = new ArrayList<>(); // NOVO

    /**
     * Retorna o ID da turma.
     * @return ID da turma
     */
    public Integer getId() {
        return id;
    }
    /**
     * Define o ID da turma.
     * @param id ID da turma
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Retorna a série da turma.
     * @return série da turma
     */
    public String getSerie() {
        return serie;
    }
    /**
     * Define a série da turma.
     * @param serie série da turma
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }
    /**
     * Retorna o ano letivo da turma.
     * @return ano letivo da turma
     */
    public Integer getAnoLetivo() {
        return anoLetivo;
    }
    /**
     * Define o ano letivo da turma.
     * @param anoLetivo ano letivo da turma
     */
    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
    /**
     * Retorna o ID do professor responsável pela turma.
     * @return ID do professor
     */
    public Integer getProfessorId() {
        return professorId;
    }
    /**
     * Define o ID do professor responsável pela turma.
     * @param professorId ID do professor
     */
    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }
    /**
     * Retorna a lista de IDs dos alunos da turma.
     * @return lista de IDs dos alunos
     */
    public List<Integer> getAlunosIds() {
        return alunosIds;
    }
    /**
     * Define a lista de IDs dos alunos da turma.
     * @param alunosIds lista de IDs dos alunos
     */
    public void setAlunosIds(List<Integer> alunosIds) {
        this.alunosIds = alunosIds;
    }
    @Override
    public String toString() {
        return "Turma{id=" + id + ", série=" + serie + ", ano=" + anoLetivo +
                ", professorId=" + professorId + ", alunos=" + alunosIds + "}";
    }
}
