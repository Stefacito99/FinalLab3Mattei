package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, DatosIncorrectosException {
        Cliente cliente = new Cliente(clienteDto);

        if (clienteDao.find(cliente.getDni()) != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.save(cliente);
        return cliente;
    }

    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        Cliente titular = buscarClientePorDni(dniTitular);
        cuenta.setDniTitular(titular.getDni());

        // Verificar si el cliente ya tiene una cuenta del mismo tipo y moneda
        if (titular.tieneCuenta(cuenta.getTipoCuenta(), cuenta.getMoneda())) {
            throw new TipoCuentaAlreadyExistsException("El cliente con dni: " + dniTitular + " ya tiene una cuenta de tipo " + cuenta.getTipoCuenta() + " y moneda " + cuenta.getMoneda());
        }

        titular.addCuenta(cuenta);
        clienteDao.save(titular);
    }

    public Cliente buscarClientePorDni(long dni) throws ClienteNotFoundException {
        Cliente cliente = clienteDao.find(dni);
        if (cliente == null) {
            throw new ClienteNotFoundException("El cliente no existe");
        }
        return cliente;
    }
}