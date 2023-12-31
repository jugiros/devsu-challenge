package com.challenge.CuentaMovimientos.controllers;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.services.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/create")
    public ResponseEntity<Movimientos> createMovimientos(@RequestBody Movimientos movimientos) {
        Movimientos createdMovimientos = movimientosService.createOrUpdateMovimientos(movimientos);
        return new ResponseEntity<>(createdMovimientos, HttpStatus.CREATED);
    }

    @GetMapping("/reporte")
    public List<Map<String, Object>> obtenerMovimientosClienteCuenta(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta
    ) {
        return movimientosService.getMovementClientAccountRecords(clienteId, fechaDesde, fechaHasta);
    }
}
