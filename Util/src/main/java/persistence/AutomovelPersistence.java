package persistence;

import model.Automovel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutomovelPersistence extends Remote {

    void create(Automovel marca) throws RemoteException;

}
