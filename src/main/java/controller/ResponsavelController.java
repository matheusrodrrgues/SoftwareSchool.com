/**
 * Controller responsável por operações relacionadas ao Responsável.
 * Recebe requisições da camada de visão e delega para o serviço.
 * Útil para estudantes entenderem o padrão MVC e manipulação de responsáveis.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */

// Pacotes MVC
package controller;
import model.Endereco;
import model.Responsavel;
import model.Aluno; // Importação corrigida
import service.ResponsavelService;
import service.AlunoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ResponsavelController {
    private final ResponsavelService service;
    private final AlunoService alunoService; // Campo adicionado

    public ResponsavelController(ResponsavelService s, AlunoService a){
        this.service = s;
        this.alunoService = a;
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
    public int criar(String nome, String telefone, LocalDate dn, String nat, Endereco end){
        return service.criar(nome, telefone, dn, nat, end);
    }
    /**
     * Busca um responsável pelo ID.
     * @param id ID do responsável
     * @return Optional contendo o responsável, se encontrado
     */
    public Optional<Responsavel> buscar(Integer id){
        return service.buscar(id);
    }
    /**
     * Lista todos os responsáveis cadastrados.
     * @return lista de responsáveis
     */
    public List<Responsavel> listar(){
        return service.listar();
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
    public boolean atualizar(Integer id, String nome, String telefone, LocalDate dn, String nat, Endereco end){
        return service.atualizar(id, nome, telefone, dn, nat, end);
    }
    /**
     * Remove um responsável pelo ID, excluindo também os alunos vinculados.
     * @param id ID do responsável
     * @return true se removido com sucesso
     */
    public boolean deletar(int id) {
        List<Aluno> alunosVinculados = alunoService.listar().stream()
                .filter(a -> a.getResponsavelId() == id && !a.isResponsavelProfessor()).toList();
        for (Aluno aluno : alunosVinculados) {
            alunoService.deletar(aluno.getId());
        }
        return service.deletar(id);
    }
}
