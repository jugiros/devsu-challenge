package com.challenge.CuentaMovimientos.services;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientosService {
    private final MovimientosRepository movimientosRepository;

    @Autowired
    public MovimientosService(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    public List<Movimientos> getAllMovimientos() {
        return movimientosRepository.findAll();
    }

    public Optional<Movimientos> getMovimientosById(Long id) {
        return movimientosRepository.findById(id);
    }

    public Movimientos createOrUpdateMovimientos(Movimientos movimientos) {
        return movimientosRepository.save(movimientos);
    }

    public void deleteMovimientosById(Long id) {
        movimientosRepository.deleteById(id);
    }
}