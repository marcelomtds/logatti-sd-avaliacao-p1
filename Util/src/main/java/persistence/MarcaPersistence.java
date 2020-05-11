package persistence;

import model.Marca;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MarcaPersistence extends Remote {

    void create(Marca marca) throws RemoteException;

    Marca findById(Long id) throws RemoteException;

}
