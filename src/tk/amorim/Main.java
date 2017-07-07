package tk.amorim;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static int MAX = 10000;

    //region Resource
    public static int[] resource_id = new int[MAX];
    public static String[] resource_name = new String[MAX];
    public static int resources_size;
    public static AtomicInteger resource_ids_control = new AtomicInteger(1000);
    //endregion

    //region Allocation
    public static int[] allocation_id = new int[MAX];
    public static String[] allocation_status = new String[MAX];
    public static Date[][] allocation_startDate = new Date[MAX][MAX];
    public static Date[][] allocation_endDate = new Date[MAX][MAX];
    public static int[] allocation_activity_id = new int[MAX];
    public static int[] allocation_resource_id = new int[MAX];
    public static int[] allocation_resource_owner_id = new int[MAX];
    public static int allocations_size;
    public static AtomicInteger allocation_ids_control = new AtomicInteger(1000);
    //endregion

    //region User
    public static int[] user_id = new int[MAX];
    public static String[] user_name = new String[MAX];
    public static int[] user_role = new int[MAX];
    public static int users_size;
    public static AtomicInteger user_ids_control = new AtomicInteger(1000);
    //endregion

    //region Activity
    public static int[] activity_id;
    public static String[] activity_title;
    public static String[] activity_desc;
    public static int activities_size;
    public static AtomicInteger activity_ids_control = new AtomicInteger(1000);
    //endregion

    static int displayMenu() {
        System.out.println("Bem-vindo ao sistema\n\n\nEscolha uma opção:\n\n1. Cadastro de Usuários\n2. Criar uma Atividade\n3. Cadastrar um recurso\n4. Alocações\n5. Sair\n\n");
        return scanner.nextInt();
    }
    static void registerUser() {
        System.out.print("\n\n\nCadastro de Usuário\n\n\nFavor informar o nome do usuário:\n");
        String username = scanner.nextLine();
        System.out.print("Favor informar o tipo de usuário (1 para aluno, 2 para professor, 3 para pesquisador):\n");
        int role = scanner.nextInt();
        int id = user_ids_control.getAndIncrement();
        user_id[users_size] = id;
        user_name[users_size] = username;
        user_role[users_size] = role;
        users_size++;
        System.out.println("\n\nUsuário " + username + " cadastrado com sucesso. \nFoi gerada a identificação " + id + ". \nVocê pode obter essa identificação posteriormente pelo sistema de busca.");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
        System.out.println("\n\n");
    }
    static void createActivity() {
        System.out.println("\n\n\nCriar uma Atividade\n\n\nInformar título da atividade:\n");
        String title = scanner.nextLine();
        System.out.println("Informar breve descrição da atividade:\n");
        String desc = scanner.nextLine();
        int id = activity_ids_control.getAndIncrement();
        activity_id[activities_size] = id;
        activity_title[activities_size] = title;
        activity_desc[activities_size] = desc;
        activities_size++;
        System.out.println("\n\nAtividade \"" + title + "\" cadastrada com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("Você deseja alocar recursos para essa atividade? (S/N)\n");
        if (scanner.nextLine().equals("S")) {
            newAllocation();
        }
    }
    static void registerResource() {
        System.out.println("\n\n\nCadastrar um Recurso\n\n\nInformar o nome do recurso:\n");
        String name = scanner.nextLine();
        int id = resource_ids_control.getAndIncrement();
        resource_id[resources_size] = id;
        resource_name[resources_size] = name;
        resources_size++;
        System.out.println("\n\nO recurso " + name + " foi cadastrado com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        System.out.println("\n\n");
    }
    static void allocation() {
        System.out.println("\n\n\nBem-Vindo ao Menu de Alocações.\n\n\n");
        System.out.println("Escolha uma opção:\n\n");
        System.out.println("1. Nova Alocação\n2. Gerenciar uma alocação\n\n");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            newAllocation();
        }
        else {
            manageAllocation();
        }
    }

    static void newAllocation() {

    }
    public static void main(String[] args) {
        boolean exit = false;
        while (true) {
            int option = displayMenu();
            scanner.nextLine();
            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    createActivity();
                    break;
                case 3:
                    registerResource();
                    break;
                case 4:
                    allocation();
                    break;
                case 5:
                    exit = true;
                    break;
            }
            if (exit)
                break;
        }
    }
}
