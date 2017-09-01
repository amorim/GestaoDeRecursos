package tk.amorim;

import tk.amorim.db.DatabaseProxy;
import tk.amorim.decorator.Researcher;
import tk.amorim.decorator.Student;
import tk.amorim.decorator.Teacher;
import tk.amorim.decorator.User;

import java.util.Scanner;

/**
 * Created by lucas on 01/09/2017.
 */
public class LoliMain {
    static Scanner scanner = new Scanner(System.in);
    static DatabaseProxy db = new DatabaseProxy("admin", "1234");

    private static void printBlankLines() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private static int displayMenu() {
        printBlankLines();
        System.out.println("Bem-vindo ao sistema\n\n\nEscolha uma opção:\n\n1. Cadastro de Usuários\n2. Criar uma Atividade\n3. Cadastrar um recurso\n4. Alocações\n5. Consulta Usuário\n6. Consulta Recurso\n7. Emitir Relatório\n8. Sair");
        return scanner.nextInt();
    }

    private static void registerUser() throws Exception {
        printBlankLines();
        System.out.print("Cadastro de Usuário\n\n\nFavor informar o nome do usuário:\n");
        String username = scanner.nextLine();
        System.out.println("Informe o e-mail do usuário:");
        String email = scanner.nextLine();
        String cpf;
        System.out.println("Informe o CPF do Usuário: ");
        cpf = scanner.nextLine();
        User filter = new User();
        filter.setCpf(cpf);
        if (db.getUser(filter) != null) {
            System.out.println("Este CPF já está cadastrado. Verifique.");
            System.out.println("Pressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.print("Favor informar o tipo de usuário (1 para aluno, 2 para professor, 3 para pesquisador):\n");
        int role = scanner.nextInt();
        User user = new User();
        user.setCpf(cpf);
        user.setEmail(email);
        user.setId(db.getUniqueIdForInstance());
        user.setName(username);
        if (role == 1) {
            user = new Student(user);
        }
        else if (role == 2) {
            user = new Teacher(user);
        }
        else {
            user = new Researcher(user);
        }
        db.addUser(user);
        System.out.println("\n\nUsuário " + username + " cadastrado com sucesso. \nFoi gerada a identificação " + user.getId() + ". \nVocê pode obter essa identificação posteriormente pelo sistema de busca.");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void main(String[] args) {
        //loadData();
        scanner = new Scanner(System.in);
        boolean exit = false;
        while (true) {
            int option = displayMenu();
            scanner.nextLine();
            switch (option) {
                case 1:
                    try {
                        registerUser();
                    }
                    catch (Exception ex) {
                        System.out.println("error");
                    }
                    break;
                case 2:
                    //createActivity();
                    break;
                case 3:
                    //registerResource();
                    break;
                case 4:
                    //allocation();
                    break;
                case 5:
                    //queryUser();
                    break;
                case 6:
                    //queryResource();
                    break;
                case 7:
                    //report();
                    break;
                case 8:
                    exit = true;
                    break;
            }
            if (exit)
                break;
        }
    }
}
