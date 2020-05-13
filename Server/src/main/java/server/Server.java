package server;

import persistence.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static final String BASE = "rmi://localhost:8282";

    public Server() {
        try {
            LocateRegistry.createRegistry(8282);
            Naming.rebind(String.format("%s/marca", BASE), new MarcaPersistenceImpl());
            System.out.println("Serviço 'Marca' iniciado com sucesso.");

            Naming.rebind(String.format("%s/modelo", BASE), new ModeloPersistenceImpl());
            System.out.println("Serviço 'Modelo' iniciado com sucesso.");

            Naming.rebind(String.format("%s/automovel", BASE), new AutomovelPersistenceImpl());
            System.out.println("Serviço 'Automovel' iniciado com sucesso.");

            Naming.rebind(String.format("%s/cliente", BASE), new ClientePersistenceImpl());
            System.out.println("Serviço 'Cliente' iniciado com sucesso.");

            Naming.rebind(String.format("%s/locacao", BASE), new LocacaoPersistenceImpl());
            System.out.println("Serviço 'Locacao' iniciado com sucesso.");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}
