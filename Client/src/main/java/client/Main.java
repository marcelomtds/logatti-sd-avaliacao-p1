package client;

import exception.ResourceNotFoundException;
import model.*;
import persistence.*;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String MARCA_PATH = "rmi://localhost:8282/marca";
    private static final String MODELO_PATH = "rmi://localhost:8282/modelo";
    private static final String AUTOMOVEL_PATH = "rmi://localhost:8282/automovel";
    private static final String CLIENTE_PATH = "rmi://localhost:8282/cliente";
    private static final String LOCACAO_PATH = "rmi://localhost:8282/locacao";

    private static MarcaPersistence marcaPersistence;
    private static ModeloPersistence modeloPersistence;
    private static AutomovelPersistence automovelPersistence;
    private static ClientePersistence clientePersistence;
    private static LocacaoPersistence locacaoPersistence;

    public static void main(String[] args) {
        getInstanceServers();
        readOption();
    }

    private static void readOption() {
        Boolean isContinue = true;
        while (isContinue) {
            Scanner scanner = new Scanner(System.in);
            printMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    createMarca();
                    break;
                case 2:
                    createModelo();
                    break;
                case 3:
                    createAutomovel();
                    break;
                case 4:
                    createCliente();
                    break;
                case 5:
                    createLocacao();
                    break;
                case 6:
                    isContinue = false;
                    System.out.println("Aplicação finalizada com sucesso.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void createMarca() {
        Scanner scanner = new Scanner(System.in);
        try {
            Marca marca = new Marca();
            System.out.print("Informe o nome da marca: ");
            marca.setDescricao(scanner.nextLine());

            marcaPersistence.create(marca);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao ler a entrada informada.");
        }
    }

    private static void createModelo() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Informe o ID da marca: ");
            Marca marca = marcaPersistence.findById(scanner.nextLong());

            if (Objects.isNull(marca)) {
                throw new ResourceNotFoundException("A marca informada não foi encontrada.");
            }

            scanner = new Scanner(System.in);

            Modelo modelo = new Modelo();
            System.out.print("Informe o nome do modelo: ");
            modelo.setDescricao(scanner.nextLine());

            modelo.setMarca(marca);

            modeloPersistence.create(modelo);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao ler a entrada informada.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createAutomovel() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Informe o ID do modelo: ");
            Modelo modelo = modeloPersistence.findById(scanner.nextLong());

            if (Objects.isNull(modelo)) {
                throw new ResourceNotFoundException("O modelo informado não foi encontrado.");
            }

            scanner = new Scanner(System.in);

            Automovel automovel = new Automovel();
            System.out.print("Informe a placa: ");
            automovel.setPlaca(scanner.nextLine());

            scanner = new Scanner(System.in);

            System.out.print("Informe a quantidade de portas: ");
            automovel.setNumeroPortas(scanner.nextInt());

            scanner = new Scanner(System.in);

            System.out.print("Informe o tipo de combustível: ");
            automovel.setTipoCombustivel(scanner.nextLine());

            System.out.print("Informe a quilometragem: ");
            automovel.setQuilometragem(scanner.nextInt());

            scanner = new Scanner(System.in);

            System.out.print("Informe a cor: ");
            automovel.setCor(scanner.nextLine());

            System.out.print("Informe o ano: ");
            automovel.setAno(scanner.nextInt());

            scanner = new Scanner(System.in);

            System.out.print("Informe a numeração do chassi: ");
            automovel.setChassi(scanner.nextLine());

            System.out.print("Informe a o valor da diária: ");
            automovel.setValorDiaria(scanner.nextBigDecimal());

            automovel.setModelo(modelo);

            automovelPersistence.create(automovel);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao ler a entrada informada.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createCliente() {
        Scanner scanner = new Scanner(System.in);
        try {
            Cliente cliente = new Cliente();
            System.out.print("Informe o CPF: ");
            cliente.setCpf(scanner.nextLine());

            System.out.print("Informe o nome: ");
            cliente.setNome(scanner.nextLine());

            System.out.print("Informe a data de nascimento: ");
            cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine()));

            System.out.print("Informe o telefone: ");
            cliente.setTelefone(scanner.nextLine());

            System.out.print("Informe o e-mail: ");
            cliente.setEmail(scanner.nextLine());

            System.out.print("Informe o logradouro do endereço: ");
            cliente.setEnderecoLogradouro(scanner.nextLine());

            System.out.print("Informe o número do endereço: ");
            cliente.setEnderecoNumero(scanner.nextInt());

            scanner = new Scanner(System.in);

            System.out.print("Informe o complemento do endereço: ");
            cliente.setEnderecoComplemento(scanner.nextLine());

            System.out.print("Informe o bairro do endereço: ");
            cliente.setEnderecoBairro(scanner.nextLine());

            System.out.print("Informe o CEP do endereço: ");
            cliente.setEnderecoCep(scanner.nextLine());

            System.out.print("Informe o nome da cidade: ");
            cliente.setEnderecoLocalidade(scanner.nextLine());

            System.out.print("Informe a UF: ");
            cliente.setEnderecoUf(scanner.nextLine());

            clientePersistence.create(cliente);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao ler a entrada informada.");
        }
    }

    private static void createLocacao() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Informe o ID do automóvel: ");
            Automovel automovel = automovelPersistence.findById(scanner.nextLong());

            if (Objects.isNull(automovel)) {
                throw new ResourceNotFoundException("O automóvel informado não foi encontrado.");
            }

            System.out.print("Informe o ID do cliente: ");
            Cliente cliente = clientePersistence.findById(scanner.nextLong());

            if (Objects.isNull(cliente)) {
                throw new ResourceNotFoundException("O cliente informado não foi encontrado.");
            }

            Locacao locacao = new Locacao();

            LocalDate currentDate = LocalDate.now();

            locacao.setDataLocacao(currentDate);

            System.out.print("Informe a quantidade de diárias: ");
            Integer quantidadeDiarias = scanner.nextInt();
            locacao.setDataDevolucao(currentDate.plusDays(quantidadeDiarias));

            locacao.setValor(automovel.getValorDiaria().multiply(BigDecimal.valueOf(quantidadeDiarias)));

            locacao.setAutomovel(automovel);
            locacao.setCliente(cliente);

            locacaoPersistence.create(locacao);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao ler a entrada informada.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------- Menu --------------------");
        sb.append("\n1 - Inserir Marca");
        sb.append("\n2 - Inserir Modelo");
        sb.append("\n3 - Inserir Automóvel");
        sb.append("\n4 - Inserir Cliente");
        sb.append("\n5 - Inserir Locação");
        sb.append("\n6 - Encerrar aplicação");
        sb.append("\nInforme a opção desejada: ");
        System.out.print(sb.toString());
    }

    private static void getInstanceServers() {
        try {
            marcaPersistence = (MarcaPersistence) Naming.lookup(MARCA_PATH);
            modeloPersistence = (ModeloPersistence) Naming.lookup(MODELO_PATH);
            automovelPersistence = (AutomovelPersistence) Naming.lookup(AUTOMOVEL_PATH);
            clientePersistence = (ClientePersistence) Naming.lookup(CLIENTE_PATH);
            locacaoPersistence = (LocacaoPersistence) Naming.lookup(LOCACAO_PATH);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
