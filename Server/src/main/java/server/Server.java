package server;

import persistence.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public Server() {
        try {
            LocateRegistry.createRegistry(8282);
            Naming.rebind("rmi://localhost:8282/marca", new MarcaPersistenceImpl());
            System.out.println("Serviço 'Marca' iniciado com sucesso.");

            Naming.rebind("rmi://localhost:8282/modelo", new ModeloPersistenceImpl());
            System.out.println("Serviço 'Modelo' iniciado com sucesso.");

            Naming.rebind("rmi://localhost:8282/automovel", new AutomovelPersistenceImpl());
            System.out.println("Serviço 'Automovel' iniciado com sucesso.");

            Naming.rebind("rmi://localhost:8282/cliente", new ClientePersistenceImpl());
            System.out.println("Serviço 'Cliente' iniciado com sucesso.");

            Naming.rebind("rmi://localhost:8282/locacao", new LocacaoPersistenceImpl());
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
