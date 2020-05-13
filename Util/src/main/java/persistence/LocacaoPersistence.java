package persistence;

import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.Locacao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LocacaoPersistence extends Remote {

    void create(Locacao locacao) throws RemoteException;

    Locacao findById(Long id) throws RemoteException, ResourceNotFoundException;

    List<Locacao> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException, ResourceNotFoundException;

    void update(Locacao locacao) throws RemoteException;

    void checkLinkWithAutomovel(Long id) throws RemoteException, ResourceCannotRemovedException;

    void checkLinkWithCliente(Long id) throws RemoteException, ResourceCannotRemovedException;

}
