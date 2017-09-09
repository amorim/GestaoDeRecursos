package tk.amorim;

import tk.amorim.db.DatabaseProxy;
import tk.amorim.db.UserFiltering;
import tk.amorim.decorator.*;
import tk.amorim.model.Activity;
import tk.amorim.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

import static tk.amorim.Main.user_name;

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
        int tem = db.getUsers().byCPF(cpf).count();
        if (tem > 0) {
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
        user = user.toRole(role);
        db.addUser(user);
        System.out.println("\n\nUsuário " + username + " cadastrado com sucesso. \nFoi gerada a identificação " + user.getId() + ". \nVocê pode obter essa identificação posteriormente pelo sistema de busca.");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }
    private static void createActivity() throws Exception {
        printBlankLines();
        System.out.println("Criar uma Atividade\n\n\nInformar título da atividade:");
        String title = scanner.nextLine();
        System.out.println("Informar breve descrição da atividade:");
        String desc = scanner.nextLine();
        System.out.println("Informe os materiais que serão usados nessa atividade:");
        String stuff = scanner.nextLine();
        System.out.println("Qual o tipo da atividade? (1 para aula tradicional, 2 para apresentações e 3 para laboratório)");
        int type = scanner.nextInt();
        Activity activity = Activity.getInstanceByType(type);
        int id = db.getUniqueIdForInstance();
        activity.setId(id);
        activity.setTitle(title);
        activity.setDescription(desc);
        activity.setMaterial(stuff);
        System.out.println("Selecione agora os usuários que participarão dessa atividade:\n");
        while (true) {
            System.out.println();
            db.getUsers().getQuerySet().forEach(u -> System.out.println(u.getId() + " - " + u.getName()));
            System.out.println("\nInsira o ID desejado:");
            int userid = scanner.nextInt();
            int matches = db.getUsers().byID(userid).count();
            if (matches != 1) {
                System.out.println("ID inválido. Favor selecionar uma das identificações listadas.");
                continue;
            }
            matches = new UserFiltering(activity.getUsers()).byID(userid).count();
            if (matches > 0) {
                System.out.println("Este usuário já está adicionado nessa atividade.");
            } else {
                User u = db.getUsers().byID(userid).getQuerySet().get(0);
                activity.getUsers().add(u);
            }
            scanner.nextLine();
            System.out.println("Adicionar mais usuários? (S/N)\n");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }

        System.out.println("\n\nAtividade \"" + title + "\" cadastrada com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("Você deseja alocar recursos para essa atividade? (S/N)\n");
        if (scanner.nextLine().equals("S")) {
            newAllocation();
            return;
        }
        System.out.println("Pressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static void registerResource() throws Exception {
        printBlankLines();
        System.out.println("Cadastrar um Recurso\n\n\nInformar o nome do recurso:");
        String name = scanner.nextLine();
        Resource resource = new Resource();
        resource.setId(db.getUniqueIdForInstance());
        resource.setName(name);
        db.addResource(resource);
        System.out.println("\n\nO recurso " + name + " foi cadastrado com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("Pressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static void allocation() {
        printBlankLines();
        System.out.println("Bem-Vindo ao Menu de Alocações.\n\n\n");
        System.out.println("Escolha uma opção:\n\n");
        System.out.println("1. Nova Alocação\n2. Gerenciar uma alocação");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            newAllocation();
        } else {
            manageAllocation();
        }
    }

    private static void manageAllocation() {
        printBlankLines();
        System.out.println("Gerenciar Alocação\n");
        System.out.println("Selecione uma identificação de alocação:");
        for (int i = 0; i < allocations_size; i++) {
            if (fromStringToId(allocation_status[i]) < 3) {
                System.out.println(allocation_id[i] + " - Atividade " + activity_title[findById(allocation_activity_id[i], activity_id, activities_size)] + " - Recurso " + resource_name[findById(allocation_resource_id[i], resource_id, resources_size)]);
            }
        }

        int id = scanner.nextInt();
        if (findById(id, allocation_id, allocations_size) == -1) {
            System.out.println("ID inválido. Verifique sua seleção.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            scanner.nextLine();
        }
        System.out.println("Escolha o novo status da alocação: (1 para \"alocado\", 2 para \"em andamento\" e 3 para \"concluído\")");
        int newstatus = scanner.nextInt();
        int pos = findById(id, allocation_id, allocations_size);
        String status = allocation_status[pos];
        int num = fromStringToId(status);
        if (newstatus != num + 1) {
            System.out.println("Seleção inválida. Você deve escolher um status imediatamente posterior ao atual.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        if ((newstatus == 1 && !checkForBasicInformation(pos)) || (newstatus == 3 && !checkForActivityDescription(pos))) {
            System.out.println("Não foram cumpridos os requisitos para essa alteração de status. Tente novamente depois de cumpri-los.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        allocation_status[pos] = fromIdToString(newstatus);
        System.out.println("Status alterado com sucesso.");
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
                    try {
                        createActivity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
