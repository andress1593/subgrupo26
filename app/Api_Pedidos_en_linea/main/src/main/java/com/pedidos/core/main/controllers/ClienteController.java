package com.pedidos.core.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.core.main.interfaces.ICliente;
import com.pedidos.core.main.models.Cliente;

@RestController
@RequestMapping(value="v1/api")
public class ClienteController {
	
	@Autowired
	private ICliente clienteService;

	@GetMapping(value="cliente")
	public List<Cliente> getCliente() {
		return clienteService.getCliente();
	}

}
