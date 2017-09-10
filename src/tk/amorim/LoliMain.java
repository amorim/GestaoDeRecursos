package tk.amorim;

import tk.amorim.db.DatabaseProxy;
import tk.amorim.db.UserFiltering;
import tk.amorim.decorator.*;
import tk.amorim.model.Activity;
import tk.amorim.model.Allocation;
import tk.amorim.model.Resource;
import tk.amorim.state.AllocationState;
import tk.amorim.state.PendingAllocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lucas on 01/09/2017.
 */
public class LoliMain {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseProxy db = new DatabaseProxy("admin", "1234");

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
        db.addActivity(activity);
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
        int id = db.getUniqueIdForInstance();
        resource.setId(id);
        resource.setName(name);
        db.addResource(resource);
        System.out.println("\n\nO recurso " + name + " foi cadastrado com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("Pressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static void allocation() throws Exception {
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

    private static void manageAllocation() throws Exception {
        printBlankLines();
        System.out.println("Gerenciar Alocação\n");
        System.out.println("Selecione uma identificação de alocação:");
        db.getAllocations().forEach(a -> System.out.println(a.getId() + " - Atividade " + a.getActivity().getTitle() + " - Recurso " + a.getOwner().getName()));
        int id = scanner.nextInt();
        Allocation alloc = db.getAllocations().stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        db.getAllocations().removeIf(a -> a.getId() == id);
        db.addAllocation((Allocation)alloc.go());
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static void newAllocation() throws Exception {
        printBlankLines();
        System.out.println("Nova Alocação\n\n\n");
        System.out.println("Favor escolher a identificação da atividade a qual o recurso será alocado: \n");
        db.getActivities().getQuerySet().forEach(a -> System.out.println(a.getId() + " - " + a.getTitle()));
        int idActivity = scanner.nextInt();
        System.out.println("Escolher a identificação do recurso a ser alocado: \n");
        db.getResources().forEach(r -> System.out.println(r.getId() + " - " + r.getName()));
        int idResource = scanner.nextInt(), idUser;
        Activity a = db.getActivities().byID(idActivity).getQuerySet().get(0);
        System.out.println("Escolha agora a identificação do usuário responsável por essa alocação (somente são exibidos os usuários que atendem aos requisitos de alocação):\n");
        List<User> filtered = db.getUsers().getQuerySet().stream().filter(u -> u.getPermissionLevel().indexOf(Permission.TEACHER) != -1 || (u.getPermissionLevel().indexOf(Permission.TEACHER) != -1 && a.getType() == 2)).collect(Collectors.toList());
        filtered.forEach(u -> System.out.println(u.getId() + " - " + u.getName()));
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
            final Date auxstart = inicio;
            final Date auxend = fim;
            boolean collide = db.getAllocations().stream().filter(al -> al.getResource().getId() == idResource && !auxstart.after(al.getEnd()) && !auxend.before(al.getStart())).count() > 0;

            if (collide) {
                System.out.println("Esse recurso já está alocado no horário selecionado. Deseja tentar outro horário? (S/N)");
                String go = scanner.nextLine();
                if (go.equals("N")) {
                    return;
                }
            } else {
                break;
            }
        }

        final Date auxstart = inicio;
        final Date auxend = fim;
        boolean collide = db.getAllocations().stream().filter(al -> al.getOwner().getId() == idUser && !auxstart.after(al.getEnd()) && !auxend.before(al.getStart())).count() > 0;
        if (collide) {
            System.out.println("O usuário selecionado está ocupado nesse horário. Não é possível alocá-lo. Favor refazer o processo escolhendo um outro horário.");
            System.out.println("\nPressione ENTER para retornar ao Menu Principal");
            scanner.nextLine();
            return;
        }
        int id = db.getUniqueIdForInstance();
        PendingAllocation pa = new PendingAllocation();
        pa.setId(id);
        pa.setResource(db.getResources().stream().filter(r -> r.getId() == idResource).collect(Collectors.toList()).get(0));
        pa.setActivity(db.getActivities().byID(idActivity).getQuerySet().get(0));
        pa.setStart(inicio);
        pa.setEnd(fim);
        pa.setOwner(db.getUsers().byID(idUser).getQuerySet().get(0));
        db.addAllocation(pa);
        System.out.println("\n\nA alocação foi feita com sucesso.\nFoi gerada a identificação " + id + ".\n");
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    private static void queryUser() throws Exception {
        printBlankLines();
        System.out.println("Consulta Usuário\n");
        db.getUsers().getQuerySet().forEach(u -> System.out.println(u.getId() + " - " + u.getName()));
        System.out.println("\nSelecione uma identificação de usuário:");
        int id = scanner.nextInt();
        User usu = db.getUsers().byID(id).getQuerySet().get(0);
        System.out.println("\nNome do usuário: " + usu.getName());
        System.out.println("E-mail do usuário: " + usu.getEmail());
        System.out.println("\nHistórico:\n");
        System.out.println("Atividades realizadas:\n");
        List<Allocation> allocs = db.getAllocations().stream().filter(a -> a.getOwner().getId() == id).collect(Collectors.toList());
        for (Allocation alloc : allocs) {
            System.out.println("Atividade #" + alloc.getActivity().getId() + " - " + alloc.getActivity().getTitle());
            System.out.println("Descrição: " + alloc.getActivity().getDescription());
            System.out.println("Materiais: " + alloc.getActivity().getMaterial());
            System.out.println("Tipo: " + alloc.getActivity().getTypeDesc());
            System.out.println();
        }
        System.out.println();
        if (allocs.isEmpty()) {
            System.out.println("Nenhuma atividade foi realizada por esse usuário.");
        }
        System.out.println("Recursos Alocados:\n");
        for (Allocation alloc : allocs) {
            System.out.println("Recurso #" + alloc.getResource().getId() + " - " + alloc.getResource().getName());
            System.out.println();
        }
        if (allocs.isEmpty()) {
            System.out.println("Nenhum recurso foi alocado por esse usuário.");
        }
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static void queryResource() throws Exception {
        printBlankLines();
        System.out.println("Consulta Recurso\n");
        db.getResources().forEach(r -> System.out.println(r.getId() + " - " + r.getName()));
        System.out.println("Selecione uma identificação de recurso:");
        int id = scanner.nextInt();
        Resource r = db.getResources().stream().filter(re -> re.getId() == id).collect(Collectors.toList()).get(0);
        System.out.println("Recurso #" + r.getId() + " - " + r.getName());
        System.out.println("\nAlocações que referenciam esse recurso:\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<Allocation> alloc = db.getAllocations().stream().filter(a -> a.getResource().getId() == id).collect(Collectors.toList());
        alloc.forEach(a -> {
            System.out.println("Alocação #" + a.getId());
            System.out.println("Data de início: " + sdf.format(a.getStart()));
            System.out.println("Data de término: " + sdf.format(a.getEnd()));
            System.out.println("Recurso Alocado: " + a.getResource().getName());
            System.out.println("Atividade: " + a.getActivity().getTitle());
            System.out.println("Usuário responsável: " + a.getOwner().getName());
            System.out.println();
        });
        if (alloc.isEmpty()) {
            System.out.println("\nEsse recurso não tem histórico de alocações.");
        }
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static void report() throws Exception {
        printBlankLines();
        System.out.println("Relatório do sistema");
        System.out.println();
        System.out.println("Número de usuários: " + db.getUsers().count());
        System.out.println("Recursos \"Em processo de alocação\": " + db.getAllocations().stream().filter(a -> a.getState() == AllocationState.PENDING).count());
        System.out.println("Recursos \"Alocados\": " + db.getAllocations().stream().filter(a -> a.getState() == AllocationState.ALLOCATED).count());
        System.out.println("Recursos \"Em Andamento\": " + db.getAllocations().stream().filter(a -> a.getState() == AllocationState.IN_PROGRESS).count());
        System.out.println("Recursos \"Concluídos\": " + db.getAllocations().stream().filter(a -> a.getState() == AllocationState.FINISHED).count());
        System.out.println("Total de alocações: " + db.getAllocations().size());
        System.out.println("Atividades - Aulas tradicionais: " + db.getActivities().byType(1).count());
        System.out.println("Atividades - Apresentações: " + db.getActivities().byType(2).count());
        System.out.println("Atividades - Laboratório: " + db.getActivities().byType(3).count());
        System.out.println("\nPressione ENTER para retornar ao Menu Principal");
        scanner.nextLine();
    }

    public static void main(String[] args) {
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
                        ex.printStackTrace();
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
                    try {
                        registerResource();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        allocation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        queryUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        queryResource();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    try {
                        report();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
