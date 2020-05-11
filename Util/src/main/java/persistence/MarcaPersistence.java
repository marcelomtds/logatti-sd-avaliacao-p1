package persistence;

import model.Marca;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MarcaPersistence extends Remote {

    void create(Marca marca) throws RemoteException;

    Boolean verifyById(Long id) throws RemoteException;

}
