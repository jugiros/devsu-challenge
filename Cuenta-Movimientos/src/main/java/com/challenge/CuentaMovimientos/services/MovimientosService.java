package com.challenge.CuentaMovimientos.services;

import com.challenge.CuentaMovimientos.constants.Constants;
import com.challenge.CuentaMovimientos.exceptions.BalanceLessThanZeroException;
import com.challenge.CuentaMovimientos.exceptions.CuentaNotFoundException;
import com.challenge.CuentaMovimientos.exceptions.TipoMovimientoNotFoundException;
import com.challenge.CuentaMovimientos.models.Cuenta;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovimientosService {

    private final MovimientosRepository movimientosRepository;
    private final CuentaService cuentaService;

    @Autowired
    public MovimientosService(MovimientosRepository movimientosRepository, CuentaService cuentaService) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaService = cuentaService;
    }

    public List<Movimientos> getAllMovimientos() {
        return movimientosRepository.findAll();
    }

    public Optional<Movimientos> getMovimientosById(Long id) {
        return movimientosRepository.findById(id);
    }

    public Movimientos createOrUpdateMovimientos(Movimientos movimientos) {
        validateMovimiento(movimientos);

        Cuenta cuenta = getCuentaOrThrowException(movimientos.getCuenta().getId());
        double lastSaldo = calculateLastSaldo(cuenta);
        double newSaldo = calculateNewSaldo(movimientos, cuenta, lastSaldo);

        if (newSaldo < 0) {
            throw new BalanceLessThanZeroException("No se puede realizar el retiro porque el valor supera el saldo de la cuenta");
        }

        updateMovimientoData(movimientos, cuenta, newSaldo);
        return movimientosRepository.save(movimientos);
    }

    private void validateMovimiento(Movimientos movimientos) {
        Optional<Cuenta> cuentaOptional = cuentaService.getCuentaById(movimientos.getCuenta().getId());
        if (cuentaOptional.isEmpty()) {
            throw new CuentaNotFoundException("La cuenta asociada no existe");
        }

        if (!Objects.equals(movimientos.getTipoMovimiento(), Constants.RETIRO)
                && !Objects.equals(movimientos.getTipoMovimiento(), Constants.DEPOSITO)) {
            throw new TipoMovimientoNotFoundException("El tipo de movimiento no existe");
        }
    }

    private Cuenta getCuentaOrThrowException(Long cuentaId) {
        return cuentaService.getCuentaById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("La cuenta asociada no existe"));
    }

    private double calculateLastSaldo(Cuenta cuenta) {
        Movimientos lastMovimiento = movimientosRepository.findUltimoMovimiento();
        return (lastMovimiento != null) ? lastMovimiento.getSaldo() : cuenta.getSaldoInicial();
    }

    private double calculateNewSaldo(Movimientos movimientos, Cuenta cuenta, double lastSaldo) {
        double valor = movimientos.getValor();
        double newSaldo = (Objects.equals(movimientos.getTipoMovimiento(), Constants.RETIRO)) ?
                lastSaldo - valor : lastSaldo + valor;
        return newSaldo;
    }

    private void updateMovimientoData(Movimientos movimientos, Cuenta cuenta, double newSaldo) {
        Date fecha = new Date();
        movimientos.setCuenta(cuenta);
        movimientos.setFecha(new Timestamp(fecha.getTime()));
        movimientos.setSaldo(newSaldo);
    }

    public List<Map<String, Object>> getMovementClientAccountRecords(Long clienteId, Date fechaDesde, Date fechaHasta) {
        return movimientosRepository.getMovementClientAccountRecords(clienteId, fechaDesde, fechaHasta);
    }

}