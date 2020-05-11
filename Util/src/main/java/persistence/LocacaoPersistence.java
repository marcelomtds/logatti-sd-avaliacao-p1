package persistence;

import model.Locacao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LocacaoPersistence extends Remote {

    void create(Locacao locacao) throws RemoteException;

}
