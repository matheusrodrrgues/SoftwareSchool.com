// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package view;
import controller.*;
import model.*;
import repository.*;
import service.*;
import java.util.Optional;

/**
 * Classe utilitária para exibição de informações de Turma, Aluno, Professor e Responsável.
 */
public class BuscarView {
    /**
     * Exibe informações detalhadas de uma turma.
     * @param turma Turma a ser exibida
     * @param profCtrl Controller de Professor
     * @param alunoCtrl Controller de Aluno
     */
    public static void mostrarTurma(Turma turma, ProfessorController profCtrl, AlunoController alunoCtrl ) {
        if (turma == null) {
            System.out.println("Turma não encontrada.");
            return;
        }
        System.out.println("========== INFORMAÇÕES DA TURMA ==========");
        System.out.println("ID: " + turma.getId());
        System.out.println("Série: " + turma.getSerie());
        System.out.println("Ano Letivo: " + turma.getAnoLetivo());
        if (turma.getProfessorId() != null) {
            profCtrl.buscar(turma.getProfessorId()).ifPresent(p -> {
                System.out.println("Professor: " + p.getNome());
            });
        } else {
            System.out.println("Professor: nenhum registrado à turma.");
        }
        if (turma.getAlunosIds().isEmpty()) {
            System.out.println("Alunos: nenhum registrado à turma.");
        } else {
            System.out.println("Alunos:");
            turma.getAlunosIds().forEach(id ->
                    alunoCtrl.buscar(id).ifPresent(a ->
                            System.out.println("  - " + a.getNome() + " | ID " + a.getId())
                    )
            );
        }
    }
    /**
     * Exibe informações detalhadas de um aluno.
     * @param aluno Aluno a ser exibido
     * @param respCtrl Controller de Responsável
     * @param turmaCtrl Controller de Turma
     * @param endereco Endereço do aluno
     * @param professorRepo Repositório de Professor
     * @param responsavelRepo Repositório de Responsável
     */
    public static void mostrarAluno(Aluno aluno, ResponsavelController respCtrl, TurmaController turmaCtrl, Endereco endereco, ProfessorRepository professorRepo, ResponsavelRepository responsavelRepo) {
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        System.out.println("========== INFORMAÇÕES DOS ALUNOS ==========");
        System.out.println("ID: " + aluno.getId());
        System.out.println("Nome completo: " + aluno.getNome());
        System.out.println("Naturalidade: " + aluno.getNaturalidade());
        System.out.println("Data de Nascimento: " + aluno.getDataNascimento());
        System.out.println("Endereço: " + (aluno.getEndereco() != null ? aluno.getEndereco() : "não informado"));

        if (aluno.isResponsavelProfessor()) {
            professorRepo.buscarId(aluno.getResponsavelId()).ifPresent(p ->
                    System.out.println("Responsável: Professor " + p.getNome())
            );
        } else {
            responsavelRepo.buscarId(aluno.getResponsavelId()).ifPresent(r ->
                    System.out.println("Responsável: " + r.getNome() + " (Tel: " + r.getTelefone() + ")")
            );
        }

        if (aluno.getTurmaId() != null) {
            turmaCtrl.buscar(aluno.getTurmaId()).ifPresent(t -> {
                System.out.println("Turma: " + t.getSerie() + " (Ano: " + t.getAnoLetivo() + ")");
            });
        }
    }
    /**
     * Exibe informações detalhadas de um professor.
     * @param prof Professor a ser exibido
     * @param turmaCtrl Controller de Turma
     * @param alunoCtrl Controller de Aluno
     */
    public static void mostrarProfessor(Professor prof, TurmaController turmaCtrl, AlunoController alunoCtrl) {
        if (prof == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        System.out.println("========== INFORMAÇÕES DO PROFESSOR ==========");
        System.out.println("ID: " + prof.getId());
        System.out.println("Nome completo: " + prof.getNome());
        System.out.println("Formação: " + prof.getFormacao());
        System.out.println("Data de Nascimento: " + prof.getDataNascimento());
        System.out.println("Telefone: " + prof.getTelefone());
        System.out.println("Endereço: " + (prof.getEndereco() != null ? prof.getEndereco() : "não informado"));

        var dependentes = alunoCtrl.listar().stream()
                .filter(a -> a.getResponsavelId() != null
                        && a.getResponsavelId().equals(prof.getId())
                        && a.isResponsavelProfessor())
                .toList();

        if (dependentes.isEmpty()) {
            System.out.println("Este professor não possui dependentes.");
        } else {
            System.out.println("Este professor é responsável por:");
            dependentes.forEach(a ->
                    System.out.println("  - " + a.getNome() + " (ID " + a.getId() + ")")
            );
        }

        // --- Turmas ---
        var turmas = turmaCtrl.listar().stream()
                .filter(t -> t.getProfessorId() != null && t.getProfessorId().equals(prof.getId()))
                .toList();

        if (turmas.isEmpty()) {
            System.out.println("Não registrado à nenhuma turma.");
        } else {
            System.out.println("Turmas vinculadas:");
            turmas.forEach(t ->
                    System.out.println("  - " + t.getSerie() + " (" + t.getAnoLetivo() + ")")
            );
        }
    }
    /**
     * Exibe informações detalhadas de um responsável.
     * @param resp Responsável a ser exibido
     * @param alunoCtrl Controller de Aluno
     * @param endereco Endereço do responsável
     */
    public static void mostrarResponsavel(Responsavel resp, AlunoController alunoCtrl, Endereco endereco) {
        if (resp == null) {
            System.out.println("Responsável não encontrado.");
            return;
        }

        System.out.println("========== INFORMAÇÕES DO RESPONSÁVEL ==========");

        System.out.println("ID: " + resp.getId());
        System.out.println("Nome: " + resp.getNome());
        System.out.println("Telefone: " + resp.getTelefone());
        System.out.println("Data de Nascimento: " + resp.getDataNascimento());
        System.out.println("Endereço: " + resp.getEndereco());

        var dependentes = alunoCtrl.listar().stream()
                .filter(a -> a.getResponsavelId() != null
                        && a.getResponsavelId().equals(resp.getId())
                        && !a.isResponsavelProfessor())
                .toList();

        if (dependentes.isEmpty()) {
            System.out.println("Nenhum aluno é dependente deste responsável.");
        } else {
            System.out.println("Alunos dependentes:");
            dependentes.forEach(a ->
                    System.out.println("  - " + a.getNome() + " (ID " + a.getId() + ")")
            );
        }
    }
}