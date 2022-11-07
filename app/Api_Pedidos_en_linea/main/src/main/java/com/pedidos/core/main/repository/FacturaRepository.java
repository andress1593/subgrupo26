package com.pedidos.core.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pedidos.core.main.models.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
	
	@Modifying
	@Query(value = "INSERT INTO factura(id_pedido, iva, domicilio, total, status, fecha_creacion)"
			+ "VALUES(?1, ?2, ?3, ?4, 1, NOW())", nativeQuery = true)
	@Transactional
	public void generarFactura(Long id_pedido, Double iva, Double domicilio, Double total);
	
	@Query(value = "SELECT * FROM factura f "
			+ "INNER JOIN pedido p ON f.id_pedido = p.id_pedido "
			+ "WHERE p.numero_pedido = ?1", nativeQuery = true)
	public Factura getByIdPedido(Long idPedido);

}
