package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.persistence.entity.PrestamoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PrestamoDao extends AbstractBaseDao {

    public PrestamoDao() {
        super();
    }

    public Prestamo find(long id) {
        if (getInMemoryDatabase().get(id) == null) {
            return null;
        }
        return ((PrestamoEntity) getInMemoryDatabase().get(id)).toPrestamo();
    }

    public void save(Prestamo prestamo) {
        PrestamoEntity prestamoEntity = new PrestamoEntity(prestamo);
        getInMemoryDatabase().put(prestamoEntity.getId(), prestamoEntity);
    }

    public List<Prestamo> findAll() {
        return getInMemoryDatabase().values().stream()
                .map(entity -> ((PrestamoEntity) entity).toPrestamo())
                .collect(Collectors.toList());
    }

    @Override
    protected String getEntityName() {
        return "PRESTAMO";
    }
}