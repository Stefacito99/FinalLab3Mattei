package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.persistence.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteDao extends AbstractBaseDao {

    @Autowired
    CuentaDao cuentaDao;

    public Cliente find(long dni) {
        try {
            Object entity = getInMemoryDatabase().get(dni);

            if (entity == null) {
                return null;
            }

            ClienteEntity clienteEntity = (ClienteEntity) entity;
            Set<Cuenta> cuentasSet = clienteEntity.getCuentas().stream()
                    .map(cuentaDao::find)
                    .collect(Collectors.toSet());

            return clienteEntity.toCliente(cuentasSet);

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el cliente con DNI: " + dni, e);
        }
    }

    public void save(Cliente cliente) throws DatosIncorrectosException {
        ClienteEntity entity = new ClienteEntity(cliente);
        getInMemoryDatabase().put(cliente.getDni(), entity);
        for (Cuenta cuenta : cliente.getCuentas()) {
            cuentaDao.save(cuenta);
        }
    }

    @Override
    protected String getEntityName() {
        return "CLIENTE";
    }
}