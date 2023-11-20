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
        Optional<Cuenta> cuentaOptional = cuentaService.getCuentaById(movimientos.getCuenta().getId());
        if (cuentaOptional.isEmpty()) {
            throw new CuentaNotFoundException("La cuenta asociada no existe");
        }

        if (!Objects.equals(movimientos.getTipoMovimiento(), Constants.RETIRO)
                && !Objects.equals(movimientos.getTipoMovimiento(), Constants.DEPOSITO)) {
            throw new TipoMovimientoNotFoundException("El tipo de movimiento no existe");
        }

        Cuenta cuenta = cuentaOptional.get();

        Movimientos lastMovimiento = movimientosRepository.findUltimoMovimiento();
        double lastSaldo = 0;
        double newSaldo = 0;

        if (lastMovimiento != null) {
            lastSaldo = lastMovimiento.getSaldo();
        } else {
            lastSaldo = cuenta.getSaldoInicial();
        }

        if (Objects.equals(movimientos.getTipoMovimiento(), Constants.RETIRO)) {
            newSaldo = lastSaldo - movimientos.getValor();
        } else {
            newSaldo = lastSaldo + movimientos.getValor();
        }

        if (newSaldo < 0) {
            throw new BalanceLessThanZeroException("No se puede realizar el retiro porque el valor supera el saldo de la cuenta");
        }

        Date fecha = new Date();
        Timestamp timestamp = new Timestamp(fecha.getTime());

        movimientos.setCuenta(cuenta);
        movimientos.setFecha(timestamp);
        movimientos.setSaldo(newSaldo);
        return movimientosRepository.save(movimientos);
    }
}