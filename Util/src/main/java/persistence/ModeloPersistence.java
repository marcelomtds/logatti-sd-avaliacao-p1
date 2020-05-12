package persistence;

import model.Modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ModeloPersistence extends Remote {

    void create(Modelo modelo) throws RemoteException;

    Modelo findById(Long id) throws RemoteException;

    List<Modelo> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException;

    void update(Modelo modelo) throws RemoteException;

}