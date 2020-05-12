package persistence;

import model.Automovel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AutomovelPersistence extends Remote {

    void create(Automovel automovel) throws RemoteException;

    Automovel findById(Long id) throws RemoteException;

    List<Automovel> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException;

    void update(Automovel automovel) throws RemoteException;

}