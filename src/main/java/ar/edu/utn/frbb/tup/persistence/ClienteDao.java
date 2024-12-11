package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;

@Service
public class ClienteDao extends AbstractBaseDao{

    @Autowired
    CuentaDao cuentaDao;

    public Cliente find(long dni, boolean fetchCuentas) {
        try {
            Object entity = getInMemoryDatabase().get(dni);

            if (entity == null) {
                return null;
            }

            Cliente cliente = ((ClienteEntity) entity).toCliente();
            return cliente;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el cliente con DNI: " + dni, e);
        }
    }

    public void save(Cliente cliente) throws DatosIncorrectosException {
        try {
            if (find(cliente.getDni(), true) != null) {
                throw new DatosIncorrectosException("Ya existe un cliente con DNI " + cliente.getDni());
            }

            getInMemoryDatabase().put(cliente.getDni(), new ClienteEntity(cliente));
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el cliente con DNI: " + cliente.getDni(), e);
        }
    }

    @Override
    protected String getEntityName() {
        return "CLIENTE";
    }
}
