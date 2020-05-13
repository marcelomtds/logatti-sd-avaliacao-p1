package persistence;

import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.Modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ModeloPersistence extends Remote {

    void create(Modelo modelo) throws RemoteException;

    Modelo findById(Long id) throws RemoteException, ResourceNotFoundException;

    List<Modelo> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException, ResourceNotFoundException, ResourceCannotRemovedException;

    void update(Modelo modelo) throws RemoteException;

    void checkLinkWithMarca(Long id) throws RemoteException, ResourceCannotRemovedException;

}