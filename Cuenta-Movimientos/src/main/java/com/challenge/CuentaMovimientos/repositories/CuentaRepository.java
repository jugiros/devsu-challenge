package com.challenge.CuentaMovimientos.repositories;
import com.challenge.CuentaMovimientos.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
