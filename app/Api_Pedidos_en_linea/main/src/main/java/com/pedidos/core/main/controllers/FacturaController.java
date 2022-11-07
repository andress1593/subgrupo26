package com.pedidos.core.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.core.main.interfaces.IFactura;
import com.pedidos.core.main.models.Factura;

@RestController
@RequestMapping(value="v1/api")
public class FacturaController {
	
	@Autowired
	IFactura facturaService;
	
	@GetMapping(value="factura")
	public List<Factura> getFacturas() {
		return facturaService.getFacturas();
	}

}
