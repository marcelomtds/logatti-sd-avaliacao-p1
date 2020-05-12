package persistence;

import model.Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientePersistence extends Remote {

    void create(Cliente cliente) throws RemoteException;

    Cliente findById(Long id) throws RemoteException;

    List<Cliente> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException;

    void update(Cliente cliente) throws RemoteException;

    ;

}