package com.challenge.CuentaMovimientos.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "movimientos")
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "saldo")
    private Double saldo;
}
