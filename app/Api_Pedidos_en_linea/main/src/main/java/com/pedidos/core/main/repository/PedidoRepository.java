package com.pedidos.core.main.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pedidos.core.main.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Query(value = "SELECT PR.valor FROM pedido PE "
			+ "INNER JOIN producto PR "
			+ "ON PE.id_producto = PR.id_producto "
			+ "WHERE PE.numero_pedido = ?1", nativeQuery = true)
	public List<Double> findBynumeroPedido(Long numPedido);
	
	@Query(value = "SELECT MAX(PR.valor) FROM pedido PE "
			+ "INNER JOIN producto PR "
			+ "ON PE.id_producto = PR.id_producto "
			+ "WHERE PE.numero_pedido = ?1", nativeQuery = true)
	public Double findValorMax(Long numPedido);
	
	@Query(value = "SELECT COUNT(*) FROM pedido WHERE numero_pedido = ?1", nativeQuery = true)
	public Long findByPedido(Long numPedido);
	
	@Modifying
	@Query(value = "DELETE FROM pedido WHERE numero_pedido = ?1", nativeQuery = true)
	@Transactional
	public Integer deletePedido(Long numPedido);
	
	@Modifying
	@Query(value = "DELETE FROM pedido WHERE id_producto = ?1 AND numero_pedido = ?2", nativeQuery = true)
	@Transactional
	public Integer deleteProductInPedido(Long idProduct, Long numPedido);

}
