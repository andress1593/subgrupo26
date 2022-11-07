package com.pedidos.core.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidos.core.main.interfaces.IFactura;
import com.pedidos.core.main.models.Factura;
import com.pedidos.core.main.repository.FacturaRepository;

@Service
public class FacturaService implements IFactura {
	
	@Autowired
	FacturaRepository facturaRepo;

	@Override
	public List<Factura> getFacturas() {
		return (List<Factura>) facturaRepo.findAll();
	}

}
