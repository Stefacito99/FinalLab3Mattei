package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CuentaDao extends AbstractBaseDao {
    @Override
    protected String getEntityName() {
        return "CUENTA";
    }

    public void save(Cuenta cuenta) {
        CuentaEntity entity = new CuentaEntity(cuenta);
        getInMemoryDatabase().put(entity.getId(), entity);
    }

    public Cuenta find(long id) {
        if (getInMemoryDatabase().get(id) == null) {
            return null;
        }
        return ((CuentaEntity) getInMemoryDatabase().get(id)).toCuenta();
    }

    public List<Cuenta> getCuentasByCliente(long dni) {
        List<Cuenta> cuentasDelCliente = new ArrayList<>();
        for (Object object : getInMemoryDatabase().values()) {
            CuentaEntity cuenta = ((CuentaEntity) object);
            if (cuenta.getDniTitular() == dni) {
                cuentasDelCliente.add(cuenta.toCuenta());
            }
        }
        return cuentasDelCliente;
    }

    public List<Cuenta> getAllCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        for (Object object : getInMemoryDatabase().values()) {
            CuentaEntity cuenta = ((CuentaEntity) object);
            cuentas.add(cuenta.toCuenta());
            System.out.println("Cuenta: " + cuenta.getNumeroCuenta() + ", Moneda: " + cuenta.getTipoMoneda().getDescripcion());
        }
        return cuentas;
    }
}