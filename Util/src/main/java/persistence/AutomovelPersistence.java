package persistence;

import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.Automovel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AutomovelPersistence extends Remote {

    void create(Automovel automovel) throws RemoteException;

    Automovel findById(Long id) throws RemoteException, ResourceNotFoundException;

    List<Automovel> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException, ResourceNotFoundException, ResourceCannotRemovedException;

    void update(Automovel automovel) throws RemoteException;

    void checkLinkWithModelo(Long id) throws RemoteException, ResourceCannotRemovedException;

}