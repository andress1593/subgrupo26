package com.pedidos.core.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidos.core.main.interfaces.IProducto;
import com.pedidos.core.main.models.Producto;
import com.pedidos.core.main.repository.ProductoRepository;

@Service
public class ProductoService implements IProducto {
	
	@Autowired
	ProductoRepository productoRepo;

	@Override
	public List<Producto> getProductos() {
		return (List<Producto>) productoRepo.findAll();
	}

}
