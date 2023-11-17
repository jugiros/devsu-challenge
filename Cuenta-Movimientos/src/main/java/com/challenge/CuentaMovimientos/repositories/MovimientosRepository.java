package com.challenge.CuentaMovimientos.repositories;
import com.challenge.CuentaMovimientos.models.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

}
