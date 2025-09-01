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

// Importando Bibliotecas
import java.time.LocalDate; //
import java.time.format.DateTimeFormatter;
import java.util.Scanner; //
import java.util.concurrent.atomic.AtomicReference;

// Início do código
/**
 * Classe principal do sistema. Gerencia o menu e inicialização dos controllers e serviços.
 */
public class Main {
    /**
     * Método principal. Inicia o menu do sistema.
     * @param args Argumentos da linha de comando
     */
    public static void main(String[] args) {
        var alunoRepo = new AlunoRepository();
        var profRepo = new ProfessorRepository();
        var respRepo = new ResponsavelRepository();
        var turmaRepo = new TurmaRepository();
        var endereco = new Endereco();

        var respService = new ResponsavelService(respRepo, alunoRepo);
        var turmaService = new TurmaService(turmaRepo);
        var alunoService = new AlunoService(alunoRepo, respRepo, turmaRepo, profRepo, respRepo);
        var profService = new ProfessorService(profRepo, turmaRepo, alunoService);

        var respCtrl = new ResponsavelController(respService, alunoService);
        var turmaCtrl = new TurmaController(turmaService);
        var profCtrl = new ProfessorController(profService, alunoService);
        var alunoCtrl = new AlunoController(alunoService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== RaioDeSol.com - Menu Principal ==========");
            System.out.println("1) Cadastrar  2) Atualizar  3) Buscar  4) Deletar  0) Sair");
            System.out.print("Opção: ");
            int op = safeInt(sc);

            switch (op) {
                case 1 -> menuCadastrar(sc, alunoCtrl, respCtrl, turmaCtrl, profCtrl);
                case 2 -> menuAtualizar(sc, alunoCtrl, respCtrl, turmaCtrl, profCtrl);
                case 3 -> menuBuscar(sc, alunoCtrl, respCtrl, turmaCtrl, profCtrl, profRepo, respRepo, endereco);
                case 4 -> menuDeletar(sc, alunoCtrl, respCtrl, turmaCtrl, profCtrl);
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
    /**
     * Verifica se o usuário deseja voltar ao menu.
     * @param s Entrada do usuário
     * @return true se for para voltar
     */
    private static boolean checkVoltar(String s) {return s.trim().equals("0");}

    // [CRUD] inicial do sistema
    /**
     * Menu de cadastro de entidades.
     */
    private static void menuCadastrar(Scanner sc, AlunoController alunoCtrl, ResponsavelController respCtrl, TurmaController turmaCtrl, ProfessorController profCtrl) {
        System.out.println("\n-> CADASTRAR: 1) Aluno  2) Responsável  3) Turma  4) Professor");
        System.out.print("Opção: ");
        int op = safeInt(sc);
        try {
            switch (op) {
                case 1 -> {
                    System.out.println("\nCadastramento de Aluno:");
                    System.out.println("Digite 0 para voltar ao menu inicial:");
                    System.out.print("\nNome completo: ");
                    String nome = sc.nextLine();
                    if (checkVoltar(nome)) return;

                    LocalDate nasc = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");
                    System.out.print("Naturalidade: ");
                    String nat = sc.nextLine();
                    Endereco end = askEndereco(sc);
                    System.out.print("ID Turma [1 a 5]: ");
                    int tId = safeInt(sc);

                    Integer rId = null;
                    while (rId == null) {
                        System.out.print("Vai vincular um Responsável [r] ou Professor [p] como responsável? (s/n para criar responsável): ");
                        String escolha = sc.nextLine().trim().toLowerCase();
                        if (escolha.equals("r") && !respCtrl.listar().isEmpty()) {
                            System.out.println("\nResponsáveis disponíveis:");
                            respCtrl.listar().forEach(r -> System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome()));

                            System.out.print("Digite o ID do responsável: ");
                            rId = safeInt(sc);
                            if (respCtrl.buscar(rId).isEmpty()) {
                                System.out.println("Responsável não encontrado!");
                                rId = null;
                            }
                        } else if (escolha.equals("p") && !profCtrl.listar().isEmpty()) {
                            System.out.println("\nProfessores disponíveis:");
                            profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));

                            System.out.print("Digite o ID do professor: ");
                            rId = safeInt(sc);
                            if (profCtrl.buscar(rId).isEmpty()) {
                                System.out.println("Professor não encontrado!");
                                rId = null;
                            }
                        } else if (escolha.equals("s") || respCtrl.listar().isEmpty()) {
                            System.out.println("\nCadastramento de novo Responsável:");
                            System.out.print("Nome completo: ");
                            String rNome = sc.nextLine();
                            if (checkVoltar(rNome)) return;

                            System.out.print("Naturalidade: ");
                            String rNat = sc.nextLine();
                            LocalDate rDn = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");

                            System.out.print("Telefone: ");
                            String rTel = sc.nextLine();
                            Endereco rEnd = askEndereco(sc);
                            rId = respCtrl.criar(rNome, rTel, rDn, rNat, rEnd);
                            System.out.println("Responsável criado com sucesso! Id=" + rId);

                        } else {
                            System.out.println("Opção inválida.");
                        }
                        if (rId != null) {
                            boolean respEhProf = escolha.equals("p");
                            int id = alunoCtrl.criar(nome, nasc, nat, rId, respEhProf, tId, end);
                            System.out.println("Aluno criado com sucesso! Id=" + id + " vinculado ao responsável/ professor Id=" + rId);
                            respCtrl.buscar(rId).ifPresent(r -> {
                                r.adicionarDependente(id);
                                respCtrl.atualizar(r.getId(), r.getNome(), r.getTelefone(), r.getDataNascimento(), r.getNat(), r.getEndereco());
                            });
                        }
                    }
                }
                case 2 -> {
                    System.out.println("\nCadastramento de Responsável:");
                    System.out.println("Digite 0 para voltar ao menu inicial:");
                    System.out.print("\nNome completo: ");
                    String nome = sc.nextLine();
                    if (checkVoltar(nome)) return;

                    System.out.print("Naturalidade: ");
                    String nat = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                    LocalDate dn = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");
                    Endereco end = askEndereco(sc);
                    int id = respCtrl.criar(nome, tel, dn, nat, end);
                    System.out.println("Responsável criado com sucesso! Id=" + id);
                }
                case 3 -> {
                    System.out.println("\nCadastramento de Turma:");
                    System.out.print("Série [1 a 5]: ");
                    String serie = sc.nextLine();
                    System.out.print("Ano letivo: ");
                    int ano = safeInt(sc);
                    int turmaId = turmaCtrl.criar(serie, ano);
                    System.out.println("\nTurma criada com sucesso! Id=" + turmaId);

                    System.out.print("Vincular um professor a esta turma? (s/n): ");
                    String vincularProf = sc.nextLine().trim().toLowerCase();

                    if (vincularProf.equals("s")) {
                        System.out.print("Professor já existente? (s/n): ");
                        String profExistente = sc.nextLine().trim().toLowerCase();
                        if (profExistente.equals("s") && !profCtrl.listar().isEmpty()) {

                            System.out.println("\n-- LISTA DE PROFESSORES --");
                            profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                            System.out.print("Digite o ID do professor: ");
                            int profId = safeInt(sc);
                            if (profCtrl.buscar(profId).isPresent()) {
                                profCtrl.vincularTurma(profId, turmaId);
                                System.out.println("Professor vinculado à turma " + turmaId);
                            } else {
                                System.out.println("Professor não encontrado. Retornando ao menu.");
                                return;
                            }
                        } else {
                            System.out.println("\n");
                            System.out.println("Cadastro de novo professor vinculado a: " + turmaId);
                            try {
                                System.out.print("Nome completo: ");
                                String nome = sc.nextLine();
                                LocalDate dn = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");
                                System.out.print("Formação Acadêmica: ");
                                String form = sc.nextLine();
                                System.out.print("Telefone (XX-XXXX-XXXX): ");
                                String tel = sc.nextLine();
                                Endereco end = askEndereco(sc);

                                int profId = profCtrl.criar(nome, form, tel, dn, turmaId, end);
                                System.out.println("Professor criado com sucesso! Id=" + profId);
                                profCtrl.vincularTurma(profId, turmaId);

                                System.out.println("Professor vinculado à turma " + turmaId);
                            } catch (Exception e) {
                                System.out.println("Erro ao criar professor: " + e.getMessage());
                                return;
                            }
                        }
                    }
                }
                case 4 -> {
                    System.out.println("\nCadastramento de Professor:");
                    System.out.print("Nome completo: ");
                    String nome = sc.nextLine();
                    LocalDate dn = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");
                    System.out.print("Formação Acadêmica: ");
                    String form = sc.nextLine();
                    System.out.print("Telefone (XX-XXXX-XXXX): ");
                    String tel = sc.nextLine();
                    Endereco end = askEndereco(sc);
                    System.out.print("ID Turma: ");
                    int turmaId = safeInt(sc);

                    int profId = profCtrl.criar(nome, form, tel, dn, turmaId, end);
                    System.out.println("Professor criado com sucesso! Id=" + profId);
                    System.out.println("\n");


                    System.out.print("Deseja adicionar dependentes (alunos) a este professor? (s/n): ");
                    String temDep = sc.nextLine().trim().toLowerCase();

                    if (temDep.equals("s")) {
                        System.out.print("O aluno já está cadastrado? (s/n): ");
                        String alunoExiste = sc.nextLine().trim().toLowerCase();
                        int alunoId;
                        if (alunoExiste.equals("s")) {
                            alunoCtrl.listar().forEach(a -> System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                            System.out.print("Digite o ID do aluno: ");
                            alunoId = safeInt(sc);
                            alunoCtrl.buscar(alunoId).ifPresent(a -> {
                                profCtrl.adicionarDependente(profId, alunoId);
                            });
                        } else {
                            System.out.println("Cadastro de novo aluno vinculado ao professor:");
                            System.out.print("\nNome completo: ");
                            String aNome = sc.nextLine();
                            LocalDate nasc = askDate(sc, "Data de nascimento (dd/mm/aaaa): ");

                            System.out.print("Naturalidade: ");
                            String nat = sc.nextLine();
                            Endereco aEnd = askEndereco(sc);
                            System.out.print("ID Turma: ");
                            int tId = safeInt(sc);

                            boolean respProf = false;
                            if(profCtrl.buscar(profId).isPresent()) respProf = true;
                            int aId = alunoCtrl.criar(aNome, nasc, nat, profId, respProf, tId, aEnd);
                            profCtrl.adicionarDependente(profId, aId);
                            System.out.println("Aluno criado e vinculado como dependente do professor!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    /**
     * Menu de atualização de entidades.
     */
    private static void menuAtualizar(Scanner sc, AlunoController alunoCtrl, ResponsavelController respCtrl,
                                      TurmaController turmaCtrl, ProfessorController profCtrl) {
        System.out.println("-> ATUALIZAR: 1) Aluno  2) Responsável  3) Turma  4) Professor");
        System.out.println("\n Pressione enter para manter o valor atual.");
        System.out.print("Opção: ");

        int op = safeInt(sc);
        switch (op) {
            case 1 -> {
                System.out.println("\n===== LISTA DE ALUNOS =====");
                alunoCtrl.listar().forEach(a -> System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));

                System.out.print("Digite o ID do aluno que deseja atualizar: ");
                int id = safeInt(sc);
                System.out.print("Novo nome: ");
                String nome = sc.nextLine();
                System.out.print("Nova Data de Nascimento (dd/mm/aaaa): ");
                String dnStr = sc.nextLine().trim();
                LocalDate nasc = dnStr.isBlank() ? null : LocalDate.parse(dnStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.print("Nova naturalidade: ");
                String nat = sc.nextLine();

                System.out.print("Atualizar endereço? (s/n): ");
                String opEnd = sc.nextLine().trim().toLowerCase();
                Endereco end = null;
                if (opEnd.equals("s")) {end = askEndereco(sc);}

                System.out.print("Deseja trocar a turma? (s/n): ");
                String opTurma = sc.nextLine().trim().toLowerCase();
                Integer turmaId = null;
                if (opTurma.equals("s")) {
                    System.out.println("\n-- LISTA DE TURMAS --");
                    turmaCtrl.listar().forEach(t -> System.out.println("ID: " + t.getId() + " | Série: " + t.getSerie() + " (" + t.getAnoLetivo() + ")"));
                    System.out.print("Digite o ID da nova turma: ");
                    int tId = safeInt(sc);
                    turmaId = (tId == 0 ? null : tId);
                }

                System.out.print("Deseja trocar o responsável? (s/n): ");
                String opResp = sc.nextLine().trim().toLowerCase();
                Integer respId = null;
                boolean respEhProf = false;

                if(opResp.equals("s")) {
                    System.out.println("\n-- LISTA DE RESPONSÁVEIS E PROFESSORES --");
                    respCtrl.listar().forEach(r -> System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome()));
                    profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                    System.out.print("Digite o ID do novo responsável: ");
                    respId = safeInt(sc);
                    if(profCtrl.buscar(respId).isPresent()) respEhProf = true;
                }
                boolean ok = alunoCtrl.atualizar(id, emptyToNull(nome), nasc, emptyToNull(nat), end, turmaId, respId, respEhProf);
                System.out.println(ok ? "Aluno atualizado com sucesso!" : "Aluno não encontrado!");

                if (respId != null) {
                    respCtrl.buscar(respId).ifPresent(r -> {
                        profCtrl.listar().stream()
                                .filter(p -> p.getNome().equalsIgnoreCase(r.getNome())
                                        && ((p.getTelefone() == null && r.getTelefone() == null) ||
                                        (p.getTelefone() != null && p.getTelefone().equals(r.getTelefone())))
                                        && ((p.getDataNascimento() == null && r.getDataNascimento() == null) ||
                                        (p.getDataNascimento() != null && p.getDataNascimento().equals(r.getDataNascimento())))
                                ).findFirst()
                                .ifPresent(p -> profCtrl.atualizar(
                                        p.getId(),
                                        emptyToNull(p.getNome()),
                                        emptyToNull(p.getFormacao()),
                                        emptyToNull(p.getTelefone()),
                                        p.getEndereco(),
                                        p.getDataNascimento(),
                                        r.getId()
                                ));
                    });
                }
            }
            case 2 -> {
                System.out.println("\n===== LISTA DE RESPONSÁVEIS =====");
                respCtrl.listar().forEach(r -> System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome() + " | Telefone: " + r.getTelefone())
                );
                System.out.print("Digite o ID do responsável que deseja atualizar: ");
                int id = safeInt(sc);
                System.out.println("\n");

                System.out.print("Novo nome: ");
                String nome = sc.nextLine();
                System.out.print("Novo telefone: ");
                String tel = sc.nextLine();

                System.out.print("Atualizar endereço? (s/n): ");
                String opEnd = sc.nextLine().trim().toLowerCase();
                Endereco end;
                if (opEnd.equals("s")) {
                    end = askEndereco(sc);
                } else {
                    end = null;
                }

                System.out.print("Nova data de nascimento (dd/mm/aaaa): ");
                String dnStr = sc.nextLine().trim();
                LocalDate dn = dnStr.isBlank() ? null : LocalDate.parse(dnStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.print("Nova naturalidade: ");
                String nat = sc.nextLine();

                boolean ok = respCtrl.atualizar(id, emptyToNull(nome), emptyToNull(tel), dn, emptyToNull(nat), end);
                System.out.println(ok ? "Responsável atualizado com sucesso!" : "Responsável não encontrado!");

                respCtrl.buscar(id).ifPresent(r -> {
                    profCtrl.listar().stream()
                            .filter(p -> p.getNome().equalsIgnoreCase(r.getNome())
                                    && ((p.getTelefone() == null && r.getTelefone() == null) ||
                                    (p.getTelefone() != null && p.getTelefone().equals(r.getTelefone())))
                                    && ((p.getDataNascimento() == null && r.getDataNascimento() == null) ||
                                    (p.getDataNascimento() != null && p.getDataNascimento().equals(r.getDataNascimento())))
                            ).findFirst()
                            .ifPresent(p -> profCtrl.atualizar(
                                    p.getId(),
                                    emptyToNull(nome),
                                    emptyToNull(p.getFormacao()),
                                    emptyToNull(tel),
                                    end,
                                    p.getDataNascimento(),
                                    r.getId()
                            ));
                });
            }
            case 3 -> {
                System.out.println("\n===== LISTA DE TURMAS =====");
                turmaCtrl.listar().forEach(t ->
                        System.out.println("ID: " + t.getId() + " | Série: " + t.getSerie() + " (" + t.getAnoLetivo() + ")"));
                System.out.print("Digite o ID da turma que deseja atualizar: ");
                int id = safeInt(sc);
                System.out.println("\n");

                System.out.print("Nova série: ");
                String serie = sc.nextLine();

                System.out.print("Novo ano letivo (0 p/ manter): ");
                int ano = safeInt(sc);
                Integer anoObj = (ano == 0 ? null : ano);

                System.out.print("Deseja atualizar o professor da turma? (s/n): ");
                String opProf = sc.nextLine().trim().toLowerCase();
                AtomicReference<Integer> professorId = new AtomicReference<>();

                if (opProf.equals("s")) {
                    turmaCtrl.buscar(id).ifPresentOrElse(turma -> {
                        if (turma.getProfessorId() != null) {
                            System.out.println("Esta turma já possui professor vinculado (ID: " + turma.getProfessorId() + ")");
                            System.out.print("Deseja substituir por outro professor? (s/n): ");
                            String substituir = sc.nextLine().trim().toLowerCase();
                            if (!substituir.equals("s")) {
                                System.out.println("Mantendo professor atual.");
                            } else {
                                System.out.println("\n-- LISTA DE PROFESSORES --");
                                profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                                System.out.print("Digite o ID do novo professor: ");
                                int profId = safeInt(sc);
                                if (profCtrl.buscar(profId).isPresent()) {
                                    professorId.set(profId);
                                } else {
                                    System.out.println("Professor não encontrado. Mantendo atual.");
                                }
                            }
                        } else {
                            System.out.println("Nenhum professor vinculado a esta turma.");
                            System.out.print("Deseja vincular um professor agora? (s/n): ");
                            String vinc = sc.nextLine().trim().toLowerCase();
                            if (vinc.equals("s")) {
                                System.out.println("\n===== LISTA DE PROFESSORES =====");
                                profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                                System.out.print("Digite o ID do professor: ");
                                int profId = safeInt(sc);
                                if (profCtrl.buscar(profId).isPresent()) {
                                    professorId.set(profId);
                                } else {
                                    System.out.println("Professor não encontrado.");
                                }
                            }
                        }
                    }, () -> System.out.println("Turma não encontrada!"));
                }

                boolean ok = turmaCtrl.atualizar(id, emptyToNull(serie), anoObj, professorId.get());
                System.out.println(ok ? "Turma atualizada com sucesso!" : "Turma não encontrada!");
            }
            case 4 -> {
                System.out.println("\n===== LISTA DE PROFESSORES =====");
                profCtrl.listar().forEach(p ->
                        System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | Formação: " + p.getFormacao())
                );
                System.out.print("Digite o ID do professor: ");
                int id = safeInt(sc);

                System.out.print("Novo nome: ");
                String nome = sc.nextLine();
                System.out.print("Nova formação: ");
                String formacao = sc.nextLine();
                System.out.print("Novo telefone: ");
                String telefone = sc.nextLine();
                System.out.print("Nova data de nascimento (dd/mm/aaaa): ");
                String dnStr = sc.nextLine().trim();
                LocalDate nasc = dnStr.isBlank() ? null : LocalDate.parse(dnStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.print("Atualizar turma do professor? (s/n): ");
                String opTurma = sc.nextLine().trim().toLowerCase();
                Integer turmaId = null;

                if (opTurma.equals("s")) {
                    System.out.println("\n==== LISTA DE TURMAS =====");
                    turmaCtrl.listar().forEach(t -> System.out.println("ID: " + t.getId() + " | Série: " + t.getSerie() + " (" + t.getAnoLetivo() + ")"));
                    System.out.print("Digite o ID da nova turma: ");
                    turmaId = safeInt(sc);
                }

                System.out.print("Atualizar endereço? (s/n): ");
                String opEnd = sc.nextLine().trim().toLowerCase();
                Endereco end = null;
                if (opEnd.equals("s")) end = askEndereco(sc);

                boolean ok = profCtrl.atualizar(id, emptyToNull(nome), emptyToNull(formacao), emptyToNull(telefone), end, nasc, turmaId);
                System.out.println(ok ? "Professor atualizado com sucesso!" : "Professor não encontrado!");
                System.out.print("Deseja gerenciar dependentes do professor? (s/n): ");

                String opDep = sc.nextLine().trim().toLowerCase();
                if(opDep.equals("s")){

                    System.out.println("===== LISTA DE ALUNOS =====");
                    alunoCtrl.listar().forEach(a -> System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                    System.out.print("Digite o ID do aluno para vincular (ou 0 para remover um dependente existente): ");
                    int alunoId = safeInt(sc);
                    if(alunoId == 0){
                        System.out.print("Digite o ID do aluno a remover: ");
                        int removeId = safeInt(sc);
                        profCtrl.removerDependente(id, removeId);
                        System.out.println("Dependente removido!");
                    } else {
                        profCtrl.adicionarDependente(id, alunoId);
                        System.out.println("Dependente vinculado ao professor!");
                    }
                }
            }}}
    /**
     * Menu de busca de entidades.
     */
    private static void menuBuscar(Scanner sc, AlunoController alunoCtrl, ResponsavelController respCtrl,
                                   TurmaController turmaCtrl, ProfessorController profCtrl, ProfessorRepository profRepo, ResponsavelRepository respRepo, Endereco endereco) {
        System.out.println("\n-> LISTA: 1) Aluno  2) Responsável  3) Turma  4) Professor");
        int op = safeInt(sc);
        switch (op) {
            case 1 -> {
                System.out.println("===== LISTA DE ALUNOS DISPONÍVEIS =====");
                alunoCtrl.listar().forEach(a -> System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                System.out.print("Digite o ID do aluno: ");
                int id = safeInt(sc);
                alunoCtrl.buscar(id).ifPresentOrElse(
                        a -> BuscarView.mostrarAluno(a, respCtrl, turmaCtrl, endereco, profRepo, respRepo),
                        () -> System.out.println("Aluno não encontrado"));
            }
            case 2 -> {
                System.out.println("===== LISTA DE RESPONSÁVEIS DISPONÍVEIS =====");
                respCtrl.listar().forEach(r -> System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome()));
                System.out.print("Digite o ID do responsável: ");
                int id = safeInt(sc);
                respCtrl.buscar(id).ifPresentOrElse(
                        r -> BuscarView.mostrarResponsavel(r, alunoCtrl, endereco),
                        () -> System.out.println("Responsável não encontrado"));
            }
            case 3 -> {
                System.out.println("===== LISTA DE TURMAS DISPONÍVEIS =====");
                turmaCtrl.listar().forEach(t -> System.out.println("ID: " + t.getId() + " | Turma: " + t.getSerie() + " (" + t.getAnoLetivo() + ")"));

                System.out.print("Digite o ID da turma: ");
                int id = safeInt(sc);
                turmaCtrl.buscar(id).ifPresentOrElse(
                        t -> BuscarView.mostrarTurma(t, profCtrl, alunoCtrl),
                        () -> System.out.println("Turma não encontrada"));
            }
            case 4 -> {
                System.out.println("===== LISTA DE PROFESSORES DISPONÍVEIS =====");
                profCtrl.listar().forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                System.out.print("Digite o ID do professor: ");
                int id = safeInt(sc);
                profCtrl.buscar(id).ifPresentOrElse(
                        p -> BuscarView.mostrarProfessor(p, turmaCtrl, alunoCtrl),
                        () -> System.out.println("Professor não encontrado"));
            }}}
    /**
     * Menu de exclusão de entidades.
     */
    private static void menuDeletar(Scanner sc, AlunoController alunoCtrl, ResponsavelController respCtrl,
                                    TurmaController turmaCtrl, ProfessorController profCtrl) {
        System.out.println("\n-- DELETAR -- 1) Aluno  2) Responsável  3) Turma  4) Professor");
        int op = safeInt(sc);

        switch (op) {
            case 1 -> {
                var alunos = alunoCtrl.listar();
                if (alunos.isEmpty()) {
                    System.out.println("Nenhum aluno cadastrado.");
                    return;
                }
                System.out.println("=== Alunos cadastrados ===");
                alunos.forEach(a -> System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                System.out.print("Digite o ID do aluno: ");
                int id = safeInt(sc);
                boolean ok = alunoCtrl.deletar(id);
                System.out.println(ok ? "Aluno deletado com sucesso!" : "Aluno não encontrado!");
            }
            case 2 -> {
                var resps = respCtrl.listar();
                if (resps.isEmpty()) {
                    System.out.println("Nenhum responsável cadastrado.");
                    return;
                }
                System.out.println("=== Responsáveis cadastrados ===");
                resps.forEach(r -> System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome()));
                System.out.print("Digite o ID do responsável: ");
                int id = safeInt(sc);
                boolean ok = respCtrl.deletar(id);
                System.out.println(ok ? "Responsável deletado com sucesso!" : "Responsável não encontrado!");
            }
            case 3 -> {
                var turmas = turmaCtrl.listar();
                if (turmas.isEmpty()) {
                    System.out.println("Nenhuma turma cadastrada.");
                    return;
                }
                System.out.println("=== Turmas cadastradas ===");
                turmas.forEach(t -> System.out.println("ID: " + t.getId() + " | Série: " + t.getSerie() + " | Ano: " + t.getAnoLetivo()));
                System.out.print("Digite o ID da turma: ");
                int id = safeInt(sc);
                boolean ok = turmaCtrl.deletar(id);
                System.out.println(ok ? "Turma deletada com sucesso!" : "Turma não encontrada!");
            }
            case 4 -> {
                var profs = profCtrl.listar();
                if (profs.isEmpty()) {
                    System.out.println("Nenhum professor cadastrado.");
                    return;
                }
                System.out.println("=== Professores cadastrados ===");
                profs.forEach(p -> System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                System.out.print("Digite o ID do professor: ");
                int id = safeInt(sc);
                boolean ok = profCtrl.deletar(id);
                System.out.println(ok ? "Professor deletado com sucesso!" : "Professor não encontrado!");
            }
            default -> System.out.println("Opção inválida!");
        }
    }
    private static int safeInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine();
            try { return Integer.parseInt(s.trim()); } catch (Exception ignored) { System.out.print("Número inválido, tente de novo: "); }
        }
    }
    private static LocalDate askDate(Scanner sc, String label) {
        System.out.print(label);
        while (true) {
            try { return LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")); } catch (Exception e) { System.out.print("Formato inválido. Use DD/MM/AAAA "); }
        }
    }
    private static Endereco askEndereco(Scanner sc) {
        System.out.println("\nPreencha seu Endereço a seguir:");
        System.out.print("\nRua: "); String rua = sc.nextLine();
        System.out.print("Bairro: "); String bairro = sc.nextLine();
        String cep;
        while (true) {
            System.out.print("CEP (xxxxxxxx): ");
            cep = sc.nextLine().trim();
            boolean num = true;
            for (char c : cep.toCharArray()) {
                if (!Character.isDigit(c)) {
                    num = false;
                    break;
                }
            }
            if (num && !cep.isEmpty()) {
                break;
            } else {
                System.out.println("CEP inválido! Digite apenas números.");
            }
        }
        System.out.print("Cidade: "); String cidade = sc.nextLine();
        System.out.print("Estado: "); String estado = sc.nextLine();
        return new Endereco(rua,bairro,cep,cidade,estado);
    }
    private static String emptyToNull(String s) { return (s==null || s.isBlank()) ? null : s; }
}
