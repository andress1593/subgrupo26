package com.pedidos.core.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedidos.core.main.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
