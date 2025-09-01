/**
 * Controller responsável por operações relacionadas à Turma.
 * Recebe requisições da camada de visão e delega para o serviço.
 * Útil para estudantes entenderem o padrão MVC e manipulação de turmas.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */
// Pacotes MVC
package controller;

import model.Turma;
import service.TurmaService;

import java.util.List;
import java.util.Optional;

// Início do código
public class TurmaController {
    private final TurmaService service;

    public TurmaController(TurmaService s){
        this.service = s;
    }
    /**
     * Cria uma nova turma com os dados informados.
     * @param serie Série da turma
     * @param ano Ano letivo
     * @return ID da turma criada
     */
    public int criar(String serie, Integer ano){
        return service.criar(serie,ano);
    }
    /**
     * Busca uma turma pelo ID.
     * @param id ID da turma
     * @return Optional contendo a turma, se encontrada
     */
    public Optional<Turma> buscar(Integer id){
        return service.buscar(id);
    }
    /**
     * Lista todas as turmas cadastradas.
     * @return lista de turmas
     */
    public List<Turma> listar(){
        return service.listar();
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
        return service.atualizar(id, serie, ano, professorId);
    }
    /**
     * Remove uma turma pelo ID.
     * @param id ID da turma
     * @return true se removida com sucesso
     */
    public boolean deletar(Integer id) {
        return service.deletar(id);
    }
}