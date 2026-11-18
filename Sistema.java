import java.util.Scanner;

public class Sistema {
    private ArvoreFuncionarios funcionarios = new ArvoreFuncionarios();
    private FilaTarefas backlog = new FilaTarefas();
    private PilhaDesfazer desfazer = new PilhaDesfazer();
    private Scanner entrada = new Scanner(System.in);
    private int proximoIdTarefa = 1;

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.iniciar();
    }

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha: ");
            executar(opcao);
        } while (opcao != 0);
        System.out.println("Sistema encerrado.");
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("===== GERENCIADOR DE TAREFAS CORPORATIVAS =====");
        System.out.println("1  - Cadastrar funcionario");
        System.out.println("2  - Buscar funcionario por ID");
        System.out.println("3  - Remover funcionario");
        System.out.println("4  - Listar funcionarios (percursos)");
        System.out.println("5  - Criar tarefa no backlog");
        System.out.println("6  - Listar backlog");
        System.out.println("7  - Executar proxima tarefa");
        System.out.println("8  - Excluir tarefa do backlog");
        System.out.println("9  - Desfazer ultima exclusao");
        System.out.println("10 - Listar exclusoes desfaziveis");
        System.out.println("0  - Sair");
    }

    private void executar(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarFuncionario();
                break;
            case 2:
                buscarFuncionario();
                break;
            case 3:
                removerFuncionario();
                break;
            case 4:
                listarFuncionarios();
                break;
            case 5:
                criarTarefa();
                break;
            case 6:
                backlog.listar();
                break;
            case 7:
                executarProximaTarefa();
                break;
            case 8:
                excluirTarefa();
                break;
            case 9:
                desfazerExclusao();
                break;
            case 10:
                desfazer.listar();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private void cadastrarFuncionario() {
        int id = lerInteiro("ID do funcionario: ");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("Cargo: ");
        String cargo = entrada.nextLine();
        if (funcionarios.inserir(new Funcionario(id, nome, cargo))) {
            System.out.println("Funcionario cadastrado.");
        } else {
            System.out.println("Ja existe funcionario com esse ID.");
        }
    }

    private void buscarFuncionario() {
        int id = lerInteiro("ID para buscar: ");
        Funcionario f = funcionarios.buscar(id);
        if (f == null) {
            System.out.println("Funcionario nao encontrado.");
        } else {
            System.out.println(f);
        }
    }

    private void removerFuncionario() {
        int id = lerInteiro("ID para remover: ");
        if (backlog.temResponsavel(id)) {
            System.out.println("Nao e possivel remover: ha tarefas pendentes deste funcionario.");
            return;
        }
        if (funcionarios.remover(id)) {
            System.out.println("Funcionario removido.");
        } else {
            System.out.println("Funcionario nao encontrado.");
        }
    }

    private void listarFuncionarios() {
        if (funcionarios.isVazia()) {
            System.out.println("Nenhum funcionario cadastrado.");
            return;
        }
        funcionarios.percursos();
    }

    private void criarTarefa() {
        int idResp = lerInteiro("ID do funcionario responsavel: ");
        if (funcionarios.buscar(idResp) == null) {
            System.out.println("Responsavel nao cadastrado.");
            return;
        }
        System.out.print("Titulo da tarefa: ");
        String titulo = entrada.nextLine();
        Tarefa t = new Tarefa(proximoIdTarefa, titulo, idResp);
        proximoIdTarefa++;
        backlog.adicionar(t);
        System.out.println("Tarefa criada: " + t);
    }

    private void executarProximaTarefa() {
        Tarefa t = backlog.remover();
        if (t == null) {
            System.out.println("Backlog vazio.");
        } else {
            System.out.println("Executando: " + t);
        }
    }

    private void excluirTarefa() {
        int id = lerInteiro("ID da tarefa para excluir: ");
        Tarefa t = backlog.removerPorId(id);
        if (t == null) {
            System.out.println("Tarefa nao encontrada no backlog.");
        } else {
            desfazer.adicionar(t);
            System.out.println("Tarefa excluida (pode ser desfeita): " + t);
        }
    }

    private void desfazerExclusao() {
        Tarefa t = desfazer.remover();
        if (t == null) {
            System.out.println("Nada para desfazer.");
            return;
        }
        if (funcionarios.buscar(t.getIdResponsavel()) == null) {
            System.out.println("Nao foi possivel restaurar: o responsavel nao existe mais.");
            return;
        }
        backlog.adicionar(t);
        System.out.println("Tarefa restaurada ao final do backlog: " + t);
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }
}
