package persistence;

import model.Marca;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MarcaPersistence extends Remote {

    void create(Marca marca) throws RemoteException;

    Marca findById(Long id) throws RemoteException;

    List<Marca> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException;

    void update(Marca marca) throws RemoteException;

}
