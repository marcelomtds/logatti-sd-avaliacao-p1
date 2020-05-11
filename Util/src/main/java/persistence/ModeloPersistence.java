package persistence;

import model.Modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ModeloPersistence extends Remote {

    void create(Modelo modelo) throws RemoteException;

    Boolean verifyById(Long id) throws RemoteException;

}
