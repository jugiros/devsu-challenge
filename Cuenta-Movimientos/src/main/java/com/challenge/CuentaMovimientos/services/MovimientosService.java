package com.challenge.CuentaMovimientos.services;
import com.challenge.CuentaMovimientos.exceptions.CuentaNotFoundException;
import com.challenge.CuentaMovimientos.models.Cuenta;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
        Optional<Cuenta> cuentaOptional = cuentaService.getCuentaById(movimientos.getId());
        if (cuentaOptional.isEmpty()) {
            throw new CuentaNotFoundException("La cuenta asociada no existe");
        }

        Cuenta cuenta = cuentaOptional.get();
        movimientos.setCuenta(cuenta);
        return movimientosRepository.save(movimientos);
    }

    public void deleteMovimientosById(Long id) {
        movimientosRepository.deleteById(id);
    }
}