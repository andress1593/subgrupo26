package com.pedidos.core.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.core.main.dto.FacturaDto;
import com.pedidos.core.main.dto.PedidoDto;
import com.pedidos.core.main.interfaces.IPedido;
import com.pedidos.core.main.models.Pedido;

@RestController
@RequestMapping(value = "v1/api")
public class PedidoController {

	@Autowired
	IPedido pedidoService;

	@GetMapping(value = "pedido")
	public List<Pedido> getPedidos() {
		return pedidoService.getPedidos();
	}

	@PostMapping(value = "savePedido")
	public FacturaDto savePedido(@RequestBody PedidoDto pedidoDto) {
		return pedidoService.savePedido(pedidoDto);
	}
	
	@PutMapping(path = { "updatePedido/{numeroPedido}" })
	public FacturaDto updatePedido(@RequestBody PedidoDto pedidoDto) {
		return pedidoService.editPedido(pedidoDto);
	}
	
	@DeleteMapping(path = { "deletePedido/{numeroPedido}" })
	public FacturaDto deletePedido(@RequestBody PedidoDto pedidoDto) {
		return pedidoService.deletePedido(pedidoDto);
	}
	
	@DeleteMapping(path = { "deleteProductPedido" })
	public FacturaDto deleteProductPedido(@RequestBody PedidoDto pedidoDto) {
		return pedidoService.deleteProductInPedido(pedidoDto);
	}

}
