package com.challenge.CuentaMovimientos.repositories;
import com.challenge.CuentaMovimientos.models.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    @Query("SELECT m FROM Movimientos m ORDER BY m.fecha DESC")
    Movimientos findUltimoMovimiento();

    @Query(value = "SELECT movimientos.fecha AS Fecha, persona.nombre AS Cliente, " +
            "cuenta.numero_cuenta AS 'Numero Cuenta', cuenta.tipo_cuenta AS Tipo, " +
            "cuenta.saldo_inicial AS 'Saldo Inicial', cuenta.estado AS Estado, " +
            "CASE WHEN movimientos.tipo_movimiento = 'Retiro' THEN -1 * movimientos.valor ELSE movimientos.valor END AS Movimiento, " +
            "movimientos.saldo AS 'Saldo Disponible' " +
            "FROM movimientos " +
            "INNER JOIN cuenta ON movimientos.cuenta_id = cuenta.id " +
            "INNER JOIN cliente ON cuenta.cliente_id = cliente.cliente_id " +
            "INNER JOIN persona ON cliente.persona_id = persona.id " +
            "WHERE cliente.cliente_id = :clienteId " +
            "AND (DATE_FORMAT(movimientos.fecha, '%Y-%m-%d') BETWEEN DATE_FORMAT(:fechaDesde, '%Y-%m-%d') AND DATE_FORMAT(:fechaHasta, '%Y-%m-%d')) " +
            "ORDER BY movimientos.fecha DESC", nativeQuery = true)
    List<Map<String, Object>> getMovementClientAccountRecords(Long clienteId, Date fechaDesde, Date fechaHasta);

}
