package com.pedidos.core.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pedidos.core.main.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query(value = "SELECT valor FROM producto WHERE id_producto IN(?1)", nativeQuery = true)
	public List<Double> getValores(List<Long> idProducto);
}
