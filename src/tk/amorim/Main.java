package tk.amorim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    public static Date[] allocation_startDate = new Date[MAX];
    public static Date[] allocation_endDate = new Date[MAX];
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
    public static int[] activity_id = new int[MAX];
    public static String[] activity_title = new String[MAX];
    public static String[] activity_desc = new String[MAX];
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
            //manageAllocation();
        }
    }
    static int findById(int id, int[] array, int tam) {
        for (int i = 0; i < tam; i++) {
            if (array[i] == id)
                return i;
        }
        return -1;
    }
    static boolean checkForPrivilegedUsers() {
        for (int role : user_role) {
            if (role > 1)
                return true;
        }
        return false;
    }
    static boolean checkIfDateCollides(Date startA, Date endA, int resourceID) {
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_resource_id[i] == resourceID) {
                Date startB = allocation_startDate[i];
                Date endB = allocation_endDate[i];
                if ((startA.before(endB) || startA.equals(endB)) && ((endA.after(startB)) || endA.equals(startB))) {
                    return true;
                }
            }
        }
        return false;
    }
    static void newAllocation() {
        System.out.println("\n\n\nNova Alocação\n\n\n");
        if (activities_size == 0) {
            System.out.println("Ocorreu um erro: não há atividades cadastradas, impossível alocar.");
            return;
        }
        if (resources_size == 0) {
            System.out.println("Ocorreu um erro: não há recursos cadastrados, impossível alocar.");
            return;
        }
        if (users_size == 0 || !checkForPrivilegedUsers()) {
            System.out.println("Ocorreu um erro: não há usuários aptos para alocar.");
            return;
        }
        System.out.println("Favor escolher a identificação da atividade a qual o recurso será alocado: \n");
        for (int i = 0; i < activities_size; i++) {
            System.out.println(activity_id[i] + " - " + activity_title[i]);
        }
        System.out.println("\n\n");
        int idActivity = scanner.nextInt();
        int locActivity = findById(idActivity, activity_id, activities_size);
        System.out.println("Escolher a identificação do recurso a ser alocado: \n");
        for (int i = 0; i < resources_size; i++) {
            System.out.println(resource_id[i] + " - " + resource_name[i]);
        }
        System.out.println("\n\n");
        int idResource = scanner.nextInt();
        int locResource = findById(idResource, resource_id, resources_size);
        System.out.println("Escolha agora a identificação do usuário responsável por essa alocação (somente são exibidos professores e pesquisadores):\n");
        for (int i = 0; i < resources_size; i++) {
            if (user_role[i] > 1) {
                System.out.println(user_id[i] + " - " + user_name[i]);
            }
        }
        System.out.println("\n\n");
        int idUser = scanner.nextInt();
        scanner.nextLine();
        int locUser = findById(idUser, user_id, users_size);
        Date inicio = null, fim = null;
        while (true) {
            System.out.println("Agora informe a data de início da alocação (DD/MM/AAAA HH:MM):\n");
            String dataInicio = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                inicio = sdf.parse(dataInicio);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("A data de fim da alocação (DD/MM/AAAA HH:MM):\n");
            String dataFim = scanner.nextLine();
            try {
                fim = sdf.parse(dataFim);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            if (checkIfDateCollides(inicio, fim, idResource)) {
                System.out.println("Esse recurso já está alocado no horário selecionado. Deseja tentar outro horário? (S/N)");
                String go = scanner.nextLine();
                if (go.equals("N"))
                    return;
            }
            else
                break;
        }
        int id = allocation_ids_control.getAndIncrement();
        allocation_id[allocations_size] = id;
        allocation_resource_id[allocations_size] = idResource;
        allocation_activity_id[allocations_size] = idActivity;
        allocation_startDate[allocations_size] = inicio;
        allocation_endDate[allocations_size] = fim;
        allocation_status[allocations_size] = "teste";
        allocations_size++;
        System.out.println("\n\nA alocação foi feita com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        System.out.println("\n\n");
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
