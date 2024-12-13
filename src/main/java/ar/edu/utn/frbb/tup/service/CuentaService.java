package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteService clienteService;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, DatosIncorrectosException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if (cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        // Chequear cuentas soportadas por el banco CA$ CC$ CAU$S
        // if (!tipoCuentaEstaSoportada(cuenta)) {...}

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        cuentaDao.save(cuenta);
        return cuenta;
    }

    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }

    public void actualizarCuenta(Cuenta cuenta) {
        cuentaDao.save(cuenta);
    }
}