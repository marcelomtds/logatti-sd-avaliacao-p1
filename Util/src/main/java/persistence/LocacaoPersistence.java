package persistence;

import model.Locacao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LocacaoPersistence extends Remote {

    void create(Locacao locacao) throws RemoteException;

    Locacao findById(Long id) throws RemoteException;

    List<Locacao> listAll() throws RemoteException;

    void delete(Long id) throws RemoteException;

    void update(Locacao locacao) throws RemoteException;

}
