package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CuentaDao extends AbstractBaseDao {
    private Map<Long, CuentaEntity> database = new HashMap<>();

    @Override
    protected String getEntityName() {
        return "CUENTA";
    }

    public Cuenta find(long id) {
        CuentaEntity entity = database.get(id);
        return entity != null ? entity.toCuenta() : null;
    }

    public void save(Cuenta cuenta) {
        CuentaEntity entity = new CuentaEntity(cuenta);
        database.put(cuenta.getNumeroCuenta(), entity);
    }
}