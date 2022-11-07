package com.pedidos.core.main.interfaces;

import java.util.List;

import com.pedidos.core.main.dto.FacturaDto;
import com.pedidos.core.main.dto.PedidoDto;
import com.pedidos.core.main.models.Pedido;

public interface IPedido {

	public List<Pedido> getPedidos();
	
	public FacturaDto savePedido(PedidoDto pedido);
	
	public FacturaDto editPedido(PedidoDto pedido);
	
	public FacturaDto deletePedido(PedidoDto pedido);
	
	public FacturaDto deleteProductInPedido(PedidoDto pedido);
}
