package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.persistence.entity.PrestamoEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PrestamoDao extends AbstractBaseDao {
    private Map<Long, PrestamoEntity> database = new HashMap<>();

    @Override
    protected String getEntityName() {
        return "PRESTAMO";
    }

    public Prestamo find(long id) {
        PrestamoEntity entity = database.get(id);
        return entity != null ? entity.toPrestamo() : null;
    }

    public void save(Prestamo prestamo) {
        PrestamoEntity entity = new PrestamoEntity(prestamo);
        database.put(prestamo.getNumeroPrestamo(), entity);
    }

    public List<Prestamo> findAll() {
        return database.values().stream()
                .map(PrestamoEntity::toPrestamo)
                .collect(Collectors.toList());
    }

    public void actualizar(Prestamo prestamo) {
        PrestamoEntity entity = new PrestamoEntity(prestamo);
        database.put(prestamo.getNumeroPrestamo(), entity);
    }
}