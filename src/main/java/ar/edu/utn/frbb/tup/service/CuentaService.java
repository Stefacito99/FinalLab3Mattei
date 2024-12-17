package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteService clienteService;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if (cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        cuentaDao.save(cuenta);
        return cuenta;
    }

    public Cuenta find(long id) throws CuentaNotFoundException {
        Cuenta cuenta = cuentaDao.find(id);
        if (cuenta == null) {
            throw new CuentaNotFoundException("Cuenta no encontrada.");
        }
        return cuenta;
    }

    public void actualizarCuenta(Cuenta cuenta) throws CuentaNotFoundException {
    if (cuentaDao.find(cuenta.getNumeroCuenta()) == null) {
        throw new CuentaNotFoundException("Cuenta no encontrada.");
    }
    cuentaDao.save(cuenta);
}
}