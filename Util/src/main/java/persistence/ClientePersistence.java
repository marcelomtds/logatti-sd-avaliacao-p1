package persistence;

import model.Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientePersistence extends Remote {

    void create(Cliente cliente) throws RemoteException;

    Cliente findById(Long id) throws RemoteException;

}