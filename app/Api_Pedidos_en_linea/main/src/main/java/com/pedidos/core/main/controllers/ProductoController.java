
package com.pedidos.core.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.core.main.interfaces.IProducto;
import com.pedidos.core.main.models.Producto;

@RestController
@RequestMapping(value="v1/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	IProducto productoService;
	
	@GetMapping(value="producto")
	public List<Producto> getProducto() {
		return productoService.getProductos();
	}
}
