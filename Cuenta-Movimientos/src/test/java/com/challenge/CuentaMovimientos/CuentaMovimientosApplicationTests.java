package com.challenge.CuentaMovimientos;

import com.challenge.CuentaMovimientos.controllers.CuentaController;
import com.challenge.CuentaMovimientos.controllers.MovimientosController;
import com.challenge.CuentaMovimientos.models.Cuenta;
import com.challenge.CuentaMovimientos.models.Movimientos;
import com.challenge.CuentaMovimientos.services.CuentaService;
import com.challenge.CuentaMovimientos.services.MovimientosService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CuentaMovimientosApplicationTests {

	@Mock
	private MovimientosService movimientosService;

	@InjectMocks
	private MovimientosController movimientosController;

	@Mock
	private CuentaService cuentaService;

	@InjectMocks
	private CuentaController cuentaController;

	@Test
	void createMovimientos_ReturnsCreatedStatus() {
		Movimientos movimientos = new Movimientos();
		when(movimientosService.createOrUpdateMovimientos(any(Movimientos.class)))
				.thenReturn(movimientos);

		ResponseEntity<Movimientos> response = movimientosController.createMovimientos(movimientos);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void createCuenta_ReturnsCreatedStatus() {
		Cuenta cuenta = new Cuenta();
		when(cuentaService.createOrUpdateCuenta(any(Cuenta.class)))
				.thenReturn(cuenta);

		ResponseEntity<Cuenta> response = cuentaController.createCuenta(cuenta);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
