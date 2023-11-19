package com.challenge.ClientePersona.controllers;
import com.challenge.ClientePersona.dto.PersonaClienteDTO;
import com.challenge.ClientePersona.models.Cliente;
import com.challenge.ClientePersona.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id)
                .map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deletePersonaAndClienteByClienteId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<Cliente> createClienteFromDTO(@RequestBody PersonaClienteDTO personaClienteDTO) {
        Cliente cliente = clienteService.createClienteFromDTO(personaClienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @PutMapping("/update-persona-cliente/{id}")
    public ResponseEntity<Cliente> updatePersonaAndClienteFromClienteId(@PathVariable Long id, @RequestBody PersonaClienteDTO personaClienteDTO) {
        Cliente cliente = clienteService.updatePersonaAndClienteFromClienteId(id, personaClienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
