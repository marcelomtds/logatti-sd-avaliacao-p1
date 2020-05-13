package client;

import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.*;
import persistence.*;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

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

    private static Scanner scanner;

    public static void main(String[] args) {
        getInstanceServers();
        readOption();
    }

    private static void readOption() {
        Boolean isContinue = true;
        while (isContinue) {
            try {
                printMenu();
                int option = getIntValue();
                switch (option) {
                    case 1:
                        createMarca();
                        break;
                    case 2:
                        updateMarca();
                        break;
                    case 3:
                        listAllMarcas();
                        break;
                    case 4:
                        deleteMarca();
                        break;
                    case 5:
                        createModelo();
                        break;
                    case 6:
                        updateModelo();
                        break;
                    case 7:
                        listAllModelos();
                        break;
                    case 8:
                        deleteModelo();
                        break;
                    case 9:
                        createAutomovel();
                        break;
                    case 10:
                        updateAutomovel();
                        break;
                    case 11:
                        listAllAutomoveis();
                        break;
                    case 12:
                        deleteAutomovel();
                        break;
                    case 13:
                        createCliente();
                        break;
                    case 14:
                        updateCliente();
                        break;
                    case 15:
                        listAllClientes();
                        break;
                    case 16:
                        deleteCliente();
                        break;
                    case 17:
                        createLocacao();
                        break;
                    case 18:
                        updateLocacao();
                        break;
                    case 19:
                        listAllLocacoes();
                        break;
                    case 20:
                        deleteLocacao();
                        break;
                    case 21:
                        isContinue = false;
                        System.out.println("Aplicação finalizada com sucesso.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ocorreu um ao ler a entrada informada.");
            }
        }
    }

    private static void createMarca() {
        try {
            Marca marca = new Marca();
            System.out.print("Informe o nome da marca: ");
            marca.setDescricao(getStringValue());
            marcaPersistence.create(marca);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void updateMarca() {
        try {
            System.out.print("Informe o ID da marca: ");
            Marca marca = marcaPersistence.findById(getLongValue());
            System.out.print("Informe o nome da marca: ");
            marca.setDescricao(getStringValue());
            marcaPersistence.update(marca);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllMarcas() {
        try {
            for (Marca marca : marcaPersistence.listAll()) {
                System.out.println(marca.toString());
            }
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void deleteMarca() {
        try {
            System.out.print("Informe o ID da marca: ");
            marcaPersistence.delete(getLongValue());
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException | ResourceCannotRemovedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createModelo() {
        try {
            Modelo modelo = new Modelo();
            System.out.print("Informe o ID da marca: ");
            modelo.setMarca(marcaPersistence.findById(getLongValue()));
            System.out.print("Informe o nome do modelo: ");
            modelo.setDescricao(getStringValue());
            modeloPersistence.create(modelo);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateModelo() {
        try {
            System.out.print("Informe o ID do modelo: ");
            Modelo modelo = modeloPersistence.findById(getLongValue());
            System.out.print("Informe o ID da marca: ");
            modelo.setMarca(marcaPersistence.findById(getLongValue()));
            System.out.print("Informe o nome do modelo: ");
            modelo.setDescricao(getStringValue());
            modeloPersistence.update(modelo);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllModelos() {
        try {
            for (Modelo modelo : modeloPersistence.listAll()) {
                System.out.println(String.format("%s - Marca = %s", modelo.toString(), modelo.getMarca().toString()));
            }
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void deleteModelo() {
        try {
            System.out.print("Informe o ID do modelo: ");
            modeloPersistence.delete(getLongValue());
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException | ResourceCannotRemovedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createAutomovel() {
        try {
            Automovel automovel = new Automovel();
            System.out.print("Informe o ID do modelo: ");
            automovel.setModelo(modeloPersistence.findById(getLongValue()));
            System.out.print("Informe a placa: ");
            automovel.setPlaca(getStringValue());
            System.out.print("Informe a quantidade de portas: ");
            automovel.setNumeroPortas(getIntValue());
            System.out.print("Informe o tipo de combustível: ");
            automovel.setTipoCombustivel(getStringValue());
            System.out.print("Informe a cor: ");
            automovel.setCor(getStringValue());
            System.out.print("Informe o ano: ");
            automovel.setAno(getIntValue());
            System.out.print("Informe a numeração do chassi: ");
            automovel.setChassi(getStringValue());
            System.out.print("Informe a o valor da diária: ");
            automovel.setValorDiaria(getBigDecimalValue());
            automovelPersistence.create(automovel);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateAutomovel() {
        try {
            System.out.print("Informe o ID do automóvel: ");
            Automovel automovel = automovelPersistence.findById(getLongValue());
            System.out.print("Informe o ID do modelo: ");
            automovel.setModelo(modeloPersistence.findById(getLongValue()));
            System.out.print("Informe a placa: ");
            automovel.setPlaca(getStringValue());
            System.out.print("Informe a quantidade de portas: ");
            automovel.setNumeroPortas(getIntValue());
            System.out.print("Informe o tipo de combustível: ");
            automovel.setTipoCombustivel(getStringValue());
            System.out.print("Informe a cor: ");
            automovel.setCor(getStringValue());
            System.out.print("Informe o ano: ");
            automovel.setAno(getIntValue());
            System.out.print("Informe a numeração do chassi: ");
            automovel.setChassi(getStringValue());
            System.out.print("Informe a o valor da diária: ");
            automovel.setValorDiaria(getBigDecimalValue());
            automovelPersistence.update(automovel);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllAutomoveis() {
        try {
            for (Automovel automovel : automovelPersistence.listAll()) {
                System.out.println(String.format("%s - Modelo = %s - Marca = %s",
                        automovel.toString(), automovel.getModelo().toString(), automovel.getModelo().getMarca().toString()));
            }
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void deleteAutomovel() {
        try {
            System.out.print("Informe o ID do automóvel: ");
            automovelPersistence.delete(getLongValue());
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException | ResourceCannotRemovedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createCliente() {
        try {
            Cliente cliente = new Cliente();
            System.out.print("Informe o CPF: ");
            cliente.setCpf(getStringValue());
            System.out.print("Informe o nome: ");
            cliente.setNome(getStringValue());
            System.out.print("Informe a data de nascimento: ");
            cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(getStringValue()));
            System.out.print("Informe o telefone: ");
            cliente.setTelefone(getStringValue());
            System.out.print("Informe o e-mail: ");
            cliente.setEmail(getStringValue());
            System.out.print("Informe o logradouro do endereço: ");
            cliente.setEnderecoLogradouro(getStringValue());
            System.out.print("Informe o número do endereço: ");
            cliente.setEnderecoNumero(getIntValue());
            System.out.print("Informe o complemento do endereço: ");
            cliente.setEnderecoComplemento(getStringValue());
            System.out.print("Informe o bairro do endereço: ");
            cliente.setEnderecoBairro(getStringValue());
            System.out.print("Informe o CEP do endereço: ");
            cliente.setEnderecoCep(getStringValue());
            System.out.print("Informe o nome da cidade: ");
            cliente.setEnderecoCidade(getStringValue());
            System.out.print("Informe a UF: ");
            cliente.setEnderecoUf(getStringValue());
            clientePersistence.create(cliente);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ParseException e) {
            System.out.println("A data de nascimento informada é inválida.");
        }
    }

    private static void updateCliente() {
        try {
            System.out.print("Informe o ID do cliente: ");
            Cliente cliente = clientePersistence.findById(getLongValue());
            System.out.print("Informe o CPF: ");
            cliente.setCpf(getStringValue());
            System.out.print("Informe o nome: ");
            cliente.setNome(getStringValue());
            System.out.print("Informe a data de nascimento: ");
            cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(getStringValue()));
            System.out.print("Informe o telefone: ");
            cliente.setTelefone(getStringValue());
            System.out.print("Informe o e-mail: ");
            cliente.setEmail(getStringValue());
            System.out.print("Informe o logradouro do endereço: ");
            cliente.setEnderecoLogradouro(getStringValue());
            System.out.print("Informe o número do endereço: ");
            cliente.setEnderecoNumero(getIntValue());
            System.out.print("Informe o complemento do endereço: ");
            cliente.setEnderecoComplemento(getStringValue());
            System.out.print("Informe o bairro do endereço: ");
            cliente.setEnderecoBairro(getStringValue());
            System.out.print("Informe o CEP do endereço: ");
            cliente.setEnderecoCep(getStringValue());
            System.out.print("Informe o nome da cidade: ");
            cliente.setEnderecoCidade(getStringValue());
            System.out.print("Informe a UF: ");
            cliente.setEnderecoUf(getStringValue());
            clientePersistence.update(cliente);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ParseException e) {
            System.out.println("A data de nascimento informada é inválida.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllClientes() {
        try {
            for (Cliente cliente : clientePersistence.listAll()) {
                System.out.println(cliente.toString());
            }
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void deleteCliente() {
        try {
            System.out.print("Informe o ID do cliente: ");
            clientePersistence.delete(getLongValue());
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException | ResourceCannotRemovedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createLocacao() {
        try {
            Locacao locacao = new Locacao();
            System.out.print("Informe o ID do automóvel: ");
            Automovel automovel = automovelPersistence.findById(getLongValue());
            locacao.setAutomovel(automovel);
            System.out.print("Informe o ID do cliente: ");
            locacao.setCliente(clientePersistence.findById(getLongValue()));
            LocalDate currentDate = LocalDate.now();
            locacao.setDataLocacao(Date.valueOf(currentDate));
            System.out.print("Informe a quantidade de diárias: ");
            Integer quantidadeDiarias = getIntValue();
            locacao.setDataDevolucao(Date.valueOf(currentDate.plusDays(quantidadeDiarias)));
            locacao.setValor(automovel.getValorDiaria().multiply(BigDecimal.valueOf(quantidadeDiarias)));
            locacaoPersistence.create(locacao);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateLocacao() {
        try {
            System.out.print("Informe o ID da locação: ");
            Locacao locacao = locacaoPersistence.findById(getLongValue());
            System.out.print("Informe o ID do automóvel: ");
            Automovel automovel = automovelPersistence.findById(getLongValue());
            locacao.setAutomovel(automovel);
            System.out.print("Informe o ID do cliente: ");
            locacao.setCliente(clientePersistence.findById(getLongValue()));
            LocalDate currentDate = LocalDate.now();
            locacao.setDataLocacao(Date.valueOf(currentDate));
            System.out.print("Informe a quantidade de diárias: ");
            Integer quantidadeDiarias = getIntValue();
            locacao.setDataDevolucao(Date.valueOf(currentDate.plusDays(quantidadeDiarias)));
            locacao.setValor(automovel.getValorDiaria().multiply(BigDecimal.valueOf(quantidadeDiarias)));
            locacaoPersistence.update(locacao);
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllLocacoes() {
        try {
            for (Locacao locacao : locacaoPersistence.listAll()) {
                System.out.println(String.format("%s - Cliente = %s - Automóvel = %s - Modelo = %s - Marca = %s",
                        locacao.toString(), locacao.getCliente(), locacao.getAutomovel(), locacao.getAutomovel().getModelo(), locacao.getAutomovel().getModelo().getMarca()));
            }
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }

    private static void deleteLocacao() {
        try {
            System.out.print("Informe o ID da locação: ");
            locacaoPersistence.delete(getLongValue());
        } catch (RemoteException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Integer getIntValue() {
        scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static String getStringValue() {
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static Long getLongValue() {
        scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    private static BigDecimal getBigDecimalValue() {
        scanner = new Scanner(System.in);
        return scanner.nextBigDecimal();
    }

    private static void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------ Menu - Marca --------------");
        sb.append("\n1 - Inserir");
        sb.append("\n2 - Alterar");
        sb.append("\n3 - Listar");
        sb.append("\n4 - Deletar");
        sb.append("\n------------ Menu - Moldelo ------------");
        sb.append("\n5 - Inserir");
        sb.append("\n6 - Alterar");
        sb.append("\n7 - Listar");
        sb.append("\n8 - Deletar");
        sb.append("\n------------ Menu - Automóvel ----------");
        sb.append("\n9 - Inserir");
        sb.append("\n10 - Alterar");
        sb.append("\n11 - Listar");
        sb.append("\n12 - Deletar");
        sb.append("\n------------ Menu - Cliente ------------");
        sb.append("\n13 - Inserir");
        sb.append("\n14 - Alterar");
        sb.append("\n15 - Listar");
        sb.append("\n16 - Deletar");
        sb.append("\n------------ Menu - Locação ------------");
        sb.append("\n17 - Inserir");
        sb.append("\n18 - Alterar");
        sb.append("\n19 - Listar");
        sb.append("\n20 - Deletar");
        sb.append("\n------------ Menu - Aplicação ----------");
        sb.append("\n21 - Sair");
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
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            System.out.println("Ocorreu um outo de comunicação com o serviço.");
        }
    }
}