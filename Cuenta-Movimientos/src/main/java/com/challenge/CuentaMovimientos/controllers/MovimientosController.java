package com.challenge.CuentaMovimientos.controllers;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.services.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
    private final MovimientosService movimientosService;

    @Autowired
    public MovimientosController(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    @GetMapping
    public ResponseEntity<List<Movimientos>> getAllMovimientos() {
        List<Movimientos> movimientos = movimientosService.getAllMovimientos();
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientosById(@PathVariable Long id) {
        return movimientosService.getMovimientosById(id)
                .map(movimiento -> new ResponseEntity<>(movimiento, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Movimientos> createMovimientos(@RequestBody Movimientos movimientos) {
        Movimientos createdMovimientos = movimientosService.createOrUpdateMovimientos(movimientos);
        return new ResponseEntity<>(createdMovimientos, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimientos(@PathVariable Long id, @RequestBody Movimientos movimientos) {
        movimientos.setId(id);
        Movimientos updatedMovimientos = movimientosService.createOrUpdateMovimientos(movimientos);
        return new ResponseEntity<>(updatedMovimientos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimientos(@PathVariable Long id) {
        movimientosService.deleteMovimientosById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
