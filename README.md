# **Proyecto Final - Laboratorio 3**  
**Autor:** Stefano Mattei  

Este es un sistema de gestión de préstamos desarrollado en Java. Permite realizar las siguientes acciones:  
- Crear clientes y cuentas bancarias.  
- Solicitar préstamos asociados a las cuentas.  
- Registrar pagos de cuotas.  

## **Estructura del Proyecto**  

El proyecto está organizado en las siguientes capas:  
- **Constructor**: Se encarga de construir y configurar las instancias necesarias para el funcionamiento del sistema.  
- **Model**: Contiene las clases que representan las entidades principales, como `Cliente`, `Cuenta`, y `Prestamo`.  
- **Persistence**: Maneja la simulación de persistencia de datos, permitiendo guardar, actualizar y recuperar información.  
- **Service**: Contiene la lógica de negocio, como la gestión de préstamos, creación de clientes y cuentas, y operaciones relacionadas con pagos y saldos.  

## **Ejemplos de Uso**  

### **Cliente**  

#### Crear un cliente  
**POST** `http://localhost:8080/cliente`  
```json
{
    "nombre": "Stefano",
    "apellido": "Mattei",
    "dni": 12345678,
    "fechaNacimiento": "2003-08-24",
    "tipoPersona": "F",
    "banco": "Revolut"
}
```  

#### Obtener un cliente por DNI  
**GET** `http://localhost:8080/cliente/12345678`  

---  

### **Cuenta**  

#### Crear una cuenta  
**POST** `http://localhost:8080/cuenta`  
```json
{
    "tipoCuenta": "CC",
    "tipoMoneda": "P",
    "balance": 100,
    "dniTitular": 12345678
}
```  

#### Obtener una cuenta por ID  
**GET** `http://localhost:8080/cuenta/1`  

---  

### **Préstamo**  

#### Solicitar un préstamo  
**POST** `http://localhost:8080/prestamo`  
```json
{
    "monto": 15000,
    "plazoMeses": 10,
    "dniTitular": 12345678,
    "moneda": "P"
}
```  

#### Obtener un préstamo por ID  
**GET** `http://localhost:8080/prestamo/1`  

#### Obtener todos los préstamos de un cliente por DNI  
**GET** `http://localhost:8080/prestamo/cliente/12345678`  

#### Registrar un pago de préstamo  
**POST** `http://localhost:8080/prestamo/pago/1?monto=1630`  

---

