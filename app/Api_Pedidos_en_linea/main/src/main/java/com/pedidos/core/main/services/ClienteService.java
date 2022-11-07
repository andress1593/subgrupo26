package com.pedidos.core.main.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidos.core.main.interfaces.ICliente;
import com.pedidos.core.main.models.Cliente;
import com.pedidos.core.main.repository.ClienteRepository;

@Service
public class ClienteService implements ICliente {
	
	@Autowired
	ClienteRepository clienteRepo;

	@Override
	public List<Cliente> getCliente() {
		return (List<Cliente>) clienteRepo.findAll();
	}

}
