package tk.amorim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static Scanner scanner;

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
    public static String[] user_email = new String[MAX];
    public static String[] user_cpf = new String[MAX];
    public static int[] user_role = new int[MAX];
    public static int users_size;
    public static AtomicInteger user_ids_control = new AtomicInteger(1000);
    //endregion

    //region Activity
    public static int[] activity_id = new int[MAX];
    public static String[] activity_title = new String[MAX];
    public static String[] activity_desc = new String[MAX];
    public static int[][] activity_users = new int[MAX][MAX];
    public static int[] activity_type = new int[MAX];
    public static String[] activity_material = new String[MAX];
    public static int[] activity_users_size = new int[MAX];
    public static int activities_size;
    public static AtomicInteger activity_ids_control = new AtomicInteger(1000);
    //endregion
    private static void loadData() {
        user_name[0] = "Lucas";
        user_cpf[0] = "33364372888";
        user_email[0] = "lucas@email.com";
        user_id[0] = user_ids_control.getAndIncrement();
        user_role[0] = 2;
        users_size = 1;

        resource_name[0] = "Projetor";
        resource_id[0] = resource_ids_control.getAndIncrement();
        resources_size = 1;

        activity_title[0] = "Aula 1";
        activity_desc[0] = "Aula de apresentação do curso";
        activity_material[0] = "Lápis e Borracha";
        activity_type[0] = 1;
        activity_id[0] = activity_ids_control.getAndIncrement();
        activity_users[0][0] = user_id[0];
        activity_users_size[0] = 1;
        activities_size = 1;

        allocation_activity_id[0] = activity_id[0];
        allocation_resource_owner_id[0] = user_id[0];
        try {
            allocation_startDate[0] = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("20/07/2017 14:00");
            allocation_endDate[0] = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("20/07/2017 15:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        allocation_status[0] = "Em processo de alocação";
        allocation_id[0] = allocation_ids_control.getAndIncrement();
        allocation_resource_id[0] = resource_id[0];
        allocations_size = 1;

    }
    private static void printBlankLines() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    private static int displayMenu() {
        printBlankLines();
        System.out.println("Bem-vindo ao sistema\n\n\nEscolha uma opção:\n\n1. Cadastro de Usuários\n2. Criar uma Atividade\n3. Cadastrar um recurso\n4. Alocações\n5. Consulta Usuário\n6. Consulta Recurso\n7. Emitir Relatório\n8. Sair");
        return scanner.nextInt();
    }

    private static boolean findCPF(String cpf) {
        for (int i = 0; i < users_size; i++) {
            if (user_cpf[i].equalsIgnoreCase(cpf))
                return true;
        }
        return false;
    }

    private static void registerUser() {
        printBlankLines();
        System.out.print("Cadastro de Usuário\n\n\nFavor informar o nome do usuário:\n");
        String username = scanner.nextLine();
        System.out.println("Informe o e-mail do usuário:");
        String email = scanner.nextLine();
        String cpf;
        System.out.println("Informe o CPF do Usuário: ");
        cpf = scanner.nextLine();
        if (findCPF(cpf)) {
            System.out.println("Este CPF já está cadastrado. Verifique.");
            System.out.println("Pressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.print("Favor informar o tipo de usuário (1 para aluno, 2 para professor, 3 para pesquisador):\n");
        int role = scanner.nextInt();
        int id = user_ids_control.getAndIncrement();
        user_id[users_size] = id;
        user_name[users_size] = username;
        user_role[users_size] = role;
        user_email[users_size] = email;
        user_cpf[users_size] = cpf;
        users_size++;
        System.out.println("\n\nUsuário " + username + " cadastrado com sucesso. \nFoi gerada a identificação " + id + ". \nVocê pode obter essa identificação posteriormente pelo sistema de busca.");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static boolean checkIfUserIsAlreadyAdded(int id) {
        for (int i = 0; i < activity_users_size[activities_size]; i++) {
            if (activity_users[activities_size][i] == id)
                return true;
        }
        return false;
    }

    private static void createActivity() {
        printBlankLines();
        if (users_size == 0) {
            System.out.println("Ocorreu um erro: não há usuários cadastrados no sistema, não é possível cadastrar uma atividade.");
            System.out.println("Pressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.println("Criar uma Atividade\n\n\nInformar título da atividade:");
        String title = scanner.nextLine();
        System.out.println("Informar breve descrição da atividade:");
        String desc = scanner.nextLine();
        System.out.println("Informe os materiais que serão usados nessa atividade:");
        String stuff = scanner.nextLine();
        System.out.println("Qual o tipo da atividade? (1 para aula tradicional, 2 para apresentações e 3 para laboratório)");
        int type = scanner.nextInt();
        System.out.println("Selecione agora os usuários que participarão dessa atividade:\n");
        while (true) {
            System.out.println();
            for (int i = 0; i < users_size; i++) {
                System.out.println(user_id[i] + " - " + user_name[i]);
            }
            System.out.println("\nInsira o ID desejado:");
            int id = scanner.nextInt();
            if (findById(id, user_id, users_size) == -1) {
                System.out.println("ID inválido. Favor selecionar uma das identificações listadas.");
                continue;
            }
            if (checkIfUserIsAlreadyAdded(id)) {
                System.out.println("Este usuário já está adicionado nessa atividade.");
            } else {
                activity_users[activities_size][activity_users_size[activities_size]++] = id;
            }
            scanner.nextLine();
            System.out.println("Adicionar mais usuários? (S/N)\n");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }

        int id = activity_ids_control.getAndIncrement();
        activity_id[activities_size] = id;
        activity_title[activities_size] = title;
        activity_desc[activities_size] = desc;
        activity_type[activities_size] = type;
        activity_material[activities_size] = stuff;
        activities_size++;
        System.out.println("\n\nAtividade \"" + title + "\" cadastrada com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("Você deseja alocar recursos para essa atividade? (S/N)\n");
        if (scanner.nextLine().equals("S")) {
            newAllocation();
            return;
        }
        System.out.println("Pressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static void registerResource() {
        printBlankLines();
        System.out.println("Cadastrar um Recurso\n\n\nInformar o nome do recurso:");
        String name = scanner.nextLine();
        int id = resource_ids_control.getAndIncrement();
        resource_id[resources_size] = id;
        resource_name[resources_size] = name;
        resources_size++;
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

    private static boolean checkForBasicInformation(int pos) {
        return allocation_startDate[pos] != null && allocation_endDate[pos] != null && allocation_resource_id[pos] != 0 && allocation_resource_owner_id[pos] != 0 && allocation_activity_id[pos] != 0;
    }

    private static boolean checkForActivityDescription(int pos) {
        return !activity_desc[findById(allocation_activity_id[pos], activity_id, activities_size)].isEmpty();
    }

    private static int fromStringToId(String string) {
        if (string.startsWith("Em proc")) {
            return 0;
        }
        if (string.startsWith("Aloc")) {
            return 1;
        }
        if (string.startsWith("Em A")) {
            return 2;
        }
        return 3;
    }

    private static String fromIdToString(int id) {
        switch (id) {
            case 0:
                return "Em processo de alocação";
            case 1:
                return "Alocado";
            case 2:
                return "Em Andamento";
            case 3:
                return "Concluído";
        }
        return "Erro";
    }

    private static int findById(int id, int[] array, int tam) {
        for (int i = 0; i < tam; i++) {
            if (array[i] == id)
                return i;
        }
        return -1;
    }

    private static boolean checkForPrivilegedUsers() {
        for (int role : user_role) {
            if (role > 1)
                return true;
        }
        return false;
    }

    private static boolean checkIfDateCollides(Date startA, Date endA, int id, int array[]) {
        for (int i = 0; i < allocations_size; i++) {
            if (array[i] == id) {
                Date startB = allocation_startDate[i];
                Date endB = allocation_endDate[i];
                if (!startA.after(endB) && !endA.before(startB)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkUserAvailability(int id) {
        int idx = activity_type[findById(id, activity_id, activities_size)];
        for (int i = 0; i < users_size; i++) {
            if (user_role[i] == 2) {
                return true;
            }
            if (user_role[i] == 3 && idx == 2) {
                return true;
            }
        }
        return false;
    }

    private static void newAllocation() {
        printBlankLines();
        System.out.println("Nova Alocação\n\n\n");
        if (activities_size == 0) {
            System.out.println("Ocorreu um erro: não há atividades cadastradas, impossível alocar.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        if (resources_size == 0) {
            System.out.println("Ocorreu um erro: não há recursos cadastrados, impossível alocar.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        if (users_size == 0 || !checkForPrivilegedUsers()) {
            System.out.println("Ocorreu um erro: não há usuários aptos para alocar.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.println("Favor escolher a identificação da atividade a qual o recurso será alocado: \n");
        for (int i = 0; i < activities_size; i++) {
            System.out.println(activity_id[i] + " - " + activity_title[i]);
        }
        int idActivity = scanner.nextInt();

        if (!checkUserAvailability(idActivity)) {
            System.out.println("Ocorreu um erro: Não há usuário apto a ser associado a essa atividade.");
            return;
        }
        System.out.println("Escolher a identificação do recurso a ser alocado: \n");
        for (int i = 0; i < resources_size; i++) {
            System.out.println(resource_id[i] + " - " + resource_name[i]);
        }
        int idResource = scanner.nextInt(), idUser;
        System.out.println("Escolha agora a identificação do usuário responsável por essa alocação (somente são exibidos os usuários que atendem aos requisitos de alocação):\n");
        for (int i = 0; i < users_size; i++) {
            if (user_role[i] > 1) {
                if (user_role[i] != 3 || (user_role[i] == 3 && activity_type[findById(idActivity, activity_id, activities_size)] == 2)) {
                    System.out.println(user_id[i] + " - " + user_name[i]);
                }
            }
        }
        idUser = scanner.nextInt();
        scanner.nextLine();
        Date inicio, fim;
        while (true) {
            System.out.println("Agora informe a data de início da alocação (DD/MM/AAAA HH:MM):\n");
            String dataInicio = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                inicio = sdf.parse(dataInicio);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
            System.out.println("A data de fim da alocação (DD/MM/AAAA HH:MM):\n");
            String dataFim = scanner.nextLine();
            try {
                fim = sdf.parse(dataFim);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
            if (!inicio.before(fim)) {
                System.out.println("Datas inválidas. Favor selecionar corretamente.");
                continue;
            }
            if (checkIfDateCollides(inicio, fim, idResource, allocation_resource_id)) {
                System.out.println("Esse recurso já está alocado no horário selecionado. Deseja tentar outro horário? (S/N)");
                String go = scanner.nextLine();
                if (go.equals("N")) {
                    return;
                }
            } else {
                break;
            }
        }
        if (checkIfDateCollides(inicio, fim, idUser, allocation_resource_owner_id)) {
            System.out.println("O usuário selecionado está ocupado nesse horário. Não é possível alocá-lo. Favor refazer o processo escolhendo um outro horário.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        int id = allocation_ids_control.getAndIncrement();
        allocation_id[allocations_size] = id;
        allocation_resource_id[allocations_size] = idResource;
        allocation_activity_id[allocations_size] = idActivity;
        allocation_startDate[allocations_size] = inicio;
        allocation_endDate[allocations_size] = fim;
        allocation_status[allocations_size] = "Em processo de alocação";
        allocation_resource_owner_id[allocations_size] = idUser;
        allocations_size++;
        System.out.println("\n\nA alocação foi feita com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static String getActivityTypeDescriptionById(int id) {
        switch (id) {
            case 1:
                return "Aula Tradicional";
            case 2:
                return "Laboratório";
            case 3:
                return "Apresentação";
            default:
                return "Sem Tipo";
        }
    }

    private static void queryUser() {
        printBlankLines();
        if (users_size == 0) {
            System.out.println("Ocorreu um erro: não há usuários cadastrados no sistema, não é possível consultar usuários.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.println("Consulta Usuário\n");
        for (int i = 0; i < users_size; i++) {
            System.out.println(user_id[i] + " - " + user_name[i]);
        }
        System.out.println("\nSelecione uma identificação de usuário:");
        int id = scanner.nextInt();
        int pos = findById(id, user_id, users_size);
        System.out.println("\nNome do usuário: " + user_name[pos]);
        System.out.println("E-mail do usuário: " + user_email[pos]);
        System.out.println("\nHistórico:\n");
        System.out.println("Atividades realizadas:\n");
        boolean entrou = false;
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_resource_owner_id[i] == id) {
                entrou = true;
                int loc = findById(allocation_activity_id[i], activity_id, activities_size);
                System.out.println("Atividade #" + activity_id[loc] + " - " + activity_title[loc]);
                System.out.println("Descrição: " + activity_desc[loc]);
                System.out.println("Materiais: " + activity_material[loc]);
                System.out.println("Tipo: " + getActivityTypeDescriptionById(activity_type[loc]));
                System.out.println();
            }
        }
        System.out.println();
        if (!entrou) {
            System.out.println("Nenhuma atividade foi realizada por esse usuário.");
        }
        entrou = false;
        System.out.println("Recursos Alocados:\n");
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_resource_owner_id[i] == id) {
                entrou = true;
                int loc = findById(allocation_resource_id[i], resource_id, resources_size);
                System.out.println("Recurso #" + resource_id[loc] + " - " + resource_name[loc]);
                System.out.println();
            }
        }
        if (!entrou) {
            System.out.println("Nenhum recurso foi alocado por esse usuário.");
        }
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static void queryResource() {
        printBlankLines();
        if (resources_size == 0) {
            System.out.println("\n\nOcorreu um erro: não há recursos cadastrados no sistema, não é possível consultar recursos.\n\n");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        System.out.println("Consulta Recurso\n");
        for (int i = 0; i < resources_size; i++) {
            System.out.println(resource_id[i] + " - " + resource_name[i]);
        }
        System.out.println("Selecione uma identificação de recurso:");
        int id = scanner.nextInt();
        int pos = findById(id, resource_id, resources_size);
        System.out.println("Recurso #" + resource_id[pos] + " - " + resource_name[pos]);
        System.out.println("\nAlocações que referenciam esse recurso:\n");
        boolean entrou = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_resource_id[i] == id) {
                entrou = true;
                System.out.println("Alocação #" + allocation_id[i]);
                System.out.println("Data de início: " + sdf.format(allocation_startDate[i]));
                System.out.println("Data de término: " + sdf.format(allocation_endDate[i]));
                System.out.println("Recurso Alocado: " + resource_name[findById(allocation_resource_id[i], resource_id, resources_size)]);
                System.out.println("Atividade: " + activity_title[findById(allocation_activity_id[i], activity_id, activities_size)]);
                System.out.println("Usuário responsável: " + user_name[findById(allocation_resource_owner_id[i], user_id, users_size)]);
                System.out.println();
            }
        }
        if (!entrou) {
            System.out.println("\nEsse recurso não tem histórico de alocações.");
        }
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static boolean notPresent(int idToFind, int[] array, int tam) {
        for (int i = 0; i < tam; i++) {
            if (array[i] == idToFind) {
                return false;
            }
        }
        return true;
    }

    private static void report() {
        printBlankLines();
        System.out.println("Relatório do sistema");
        System.out.println();
        System.out.println("Número de usuários: " + users_size);
        int[] temp = new int[MAX];
        int idx = 0;
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_status[i].equalsIgnoreCase("em processo de alocação")) {
                if (notPresent(allocation_resource_id[i], temp, idx)) {
                    temp[idx++] = allocation_resource_id[i];
                }
            }
        }
        System.out.println("Recursos \"Em processo de alocação\": " + idx);
        idx = 0;
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_status[i].equalsIgnoreCase("alocado")) {
                if (notPresent(allocation_resource_id[i], temp, idx)) {
                    temp[idx++] = allocation_resource_id[i];
                }
            }
        }
        System.out.println("Recursos \"Alocados\": " + idx);
        idx = 0;
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_status[i].equalsIgnoreCase("em andamento")) {
                if (notPresent(allocation_resource_id[i], temp, idx)) {
                    temp[idx++] = allocation_resource_id[i];
                }
            }
        }
        System.out.println("Recursos \"Em Andamento\": " + idx);
        idx = 0;
        for (int i = 0; i < allocations_size; i++) {
            if (allocation_status[i].equalsIgnoreCase("concluído")) {
                if (notPresent(allocation_resource_id[i], temp, idx)) {
                    temp[idx++] = allocation_resource_id[i];
                }
            }
        }
        System.out.println("Recursos \"Concluídos\": " + idx);
        System.out.println("Total de alocações: " + allocations_size);
        System.out.println("Atividades - Aulas tradicionais: " + Arrays.stream(activity_type).filter(type -> type == 1).count());
        System.out.println("Atividades - Apresentações: " + Arrays.stream(activity_type).filter(type -> type == 2).count());
        System.out.println("Atividades - Laboratório: " + Arrays.stream(activity_type).filter(type -> type == 3).count());
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        loadData();
        scanner = new Scanner(System.in);
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
                    queryUser();
                    break;
                case 6:
                    queryResource();
                    break;
                case 7:
                    report();
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
