package com.pedidos.core.main.services;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidos.core.main.dto.FacturaDto;
import com.pedidos.core.main.dto.PedidoDto;
import com.pedidos.core.main.interfaces.IPedido;
import com.pedidos.core.main.models.Factura;
import com.pedidos.core.main.models.Pedido;
import com.pedidos.core.main.repository.FacturaRepository;
import com.pedidos.core.main.repository.PedidoRepository;
import com.pedidos.core.main.repository.ProductoRepository;

@Service
public class PedidoService implements IPedido {

	FacturaDto facturaNueva = new FacturaDto();
	Pedido ped = new Pedido();

	private Double iva;

	private Double domicilio = 0d;

	private Double totalPagar;

	Date fecha = new Date();

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	SimpleDateFormat horaFormat = new SimpleDateFormat("hh:mm");

	@Autowired
	PedidoRepository pedidoRepo;

	@Autowired
	FacturaRepository facturaRepo;

	@Autowired
	ProductoRepository productoRepo;

	@Override
	public List<Pedido> getPedidos() {
		return (List<Pedido>) pedidoRepo.findAll();
	}

	@Override
	public FacturaDto savePedido(PedidoDto pedidoDto) {

		Pedido pedido = new Pedido();
		FacturaDto factura = new FacturaDto();
		Double valor;
		try {
			boolean result = validarPedido(pedidoDto.getNumeroPedido());
			if (pedidoDto.getListaProducto().size() > 0 && result) {
				Iterator<Long> iterator = pedidoDto.getListaProducto().iterator();
				while (iterator.hasNext()) {
					Long idProducto = iterator.next();
					pedidoDto.setIdProducto(idProducto);
					pedido = map(pedidoDto);
					pedido = pedidoRepo.save(pedido);
				}

				List<Double> valores = pedidoRepo.findBynumeroPedido(pedido.getNumeroPedido());
				valor = obtenerValorPedido(valores);

				factura = generarFactura(valor, pedido);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return factura;
	}

	private Pedido map(final PedidoDto pedidoDto) {

		Pedido pedido = new Pedido();

		pedido.setIdCliente(pedidoDto.getIdCliente());
		pedido.setIdProducto(pedidoDto.getIdProducto());
		pedido.setNumeroPedido(pedidoDto.getNumeroPedido());
		pedido.setStatus(1);
		pedido.setFechaCreacion(fecha);

		return pedido;

	}

	private Double obtenerValorPedido(List<Double> precios) {
		Double total = 0D;
		if (precios.size() > 0) {
			for (Double precio : precios) {
				total += precio;
			}
		}
		return total;
	}

	private boolean validarPedido(Long numPedido) {
		try {
			Long result = pedidoRepo.findByPedido(numPedido);
			if (result == 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	private FacturaDto generarFactura(Double valor, Pedido pedido) {
		Factura factura = new Factura();
		if (valor > 70000 && valor <= 100000) {
			System.out.println("el sistema genera la factura con el iva y el valor del\r\n" + "domicilio.");
			iva = (valor * 0.19d);
			domicilio = 10000d;
			totalPagar = valor + iva + domicilio;
			factura = mapFactura(pedido.getId(), iva, domicilio, totalPagar);
			factura = facturaRepo.save(factura);
		} else if (valor > 100000) {
			System.out.println("el sistema genera la factura con el iva y el valor del\r\n" + "domicilio en 0 pesos.");
			iva = (valor * 0.19d);
			domicilio = 0d;
			totalPagar = valor + iva + domicilio;
			factura = mapFactura(pedido.getId(), iva, domicilio, totalPagar);
			factura = facturaRepo.save(factura);
		}

		FacturaDto facturaDto = mapFac(factura);
		if (factura.getId() != null) {
			facturaDto.setId(factura.getId());
			facturaDto.setResultado("OK");
			facturaDto.setMensaje("¡Factura generada satisfactoriamente!");
		} else {
			facturaDto.setResultado("ERROR");
			facturaDto.setMensaje("¡No se pudo generar el pedido!");
		}
		return facturaDto;
	}

	private Factura mapFactura(Long idPedido, Double iva, Double domicilio, Double total) {

		Factura factura = new Factura();

		factura.setIdPedido(idPedido);
		factura.setIva(iva);
		factura.setDomicilio(domicilio);
		factura.setTotal(total);
		factura.setStatus(1);
		factura.setFechaCreacion(fecha);

		return factura;
	}

	private FacturaDto mapFac(Factura factura) {

		FacturaDto facturaDto = new FacturaDto();

		facturaDto.setIdPedido(factura.getId());
		facturaDto.setIva(factura.getIva());
		facturaDto.setDomicilio(factura.getDomicilio());
		facturaDto.setTotal(factura.getTotal());
		facturaDto.setStatus(factura.getStatus());
		facturaDto.setFechaCreacion(factura.getFechaCreacion());

		return facturaDto;
	}

	@Override
	public FacturaDto editPedido(PedidoDto pedido) {

		try {

			Date fechaActual = new Date();
			Factura factura = facturaRepo.getByIdPedido(pedido.getNumeroPedido());
			Date fechaRegistrada = factura.getFechaCreacion();

			String fechaActualCadena = dateFormat.format(fechaActual);
			String fechaRegistradaCadena = dateFormat.format(fechaRegistrada);

			String horaActual = horaFormat.format(fechaActual);
			LocalTime actual = LocalTime.parse(horaActual);

			String horaReg = horaFormat.format(fechaRegistrada);
			LocalTime registro = LocalTime.parse(horaReg);

			int minutos = (int) ChronoUnit.MINUTES.between(registro, actual);

			// Validamos que el pedido se hizo el mismo día.
			if (fechaActualCadena.equals(fechaRegistradaCadena)) {

				// Validamos la cantidad de horas.
				if (minutos < (60 * 5)) {

					if (pedido.getProductosNuevos().size() > 0) {

						Double valorMax = pedidoRepo.findValorMax(pedido.getNumeroPedido());

						List<Double> valoresNuevos = productoRepo.getValores(pedido.getProductosNuevos());

						int resp = validarValoresNuevos(valorMax, valoresNuevos);

						if (resp != -1) {
							Iterator<Long> iterator = pedido.getProductosNuevos().iterator();

							while (iterator.hasNext()) {

								Long idProducto = iterator.next();
								pedido.setIdProducto(idProducto);

								ped = map(pedido);
								ped = pedidoRepo.save(ped);

							}

							facturaRepo.delete(factura);
							List<Double> valores = pedidoRepo.findBynumeroPedido(pedido.getNumeroPedido());
							Double valor = obtenerValorPedido(valores);

							facturaNueva = generarFactura(valor, ped);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return facturaNueva;
	}

	private int validarValoresNuevos(Double valorMax, List<Double> valoresNuevos) {

		List<Double> valorIncorrecto = new ArrayList<Double>();
		if (valoresNuevos.size() > 0) {
			for (int i = 0; i < valoresNuevos.size(); i++) {
				if (valoresNuevos.get(i) < valorMax) {
					valorIncorrecto.add(valoresNuevos.get(i));
				}
			}
		}

		if (valorIncorrecto.size() > 0) {
			return -1;
		}

		return 0;
	}

	@Override
	public FacturaDto deletePedido(PedidoDto pedido) {

		try {

			Date fechaActual = new Date();

			Factura factura = facturaRepo.getByIdPedido(pedido.getNumeroPedido());

			Date fechaRegistrada = factura.getFechaCreacion();

			String fechaActualCadena = dateFormat.format(fechaActual);
			String fechaRegistradaCadena = dateFormat.format(fechaRegistrada);

			String horaActual = horaFormat.format(fechaActual);
			LocalTime actual = LocalTime.parse(horaActual);

			String horaReg = horaFormat.format(fechaRegistrada);
			LocalTime registro = LocalTime.parse(horaReg);

			int minutos = (int) ChronoUnit.MINUTES.between(registro, actual);

			// Validamos que el pedido se hizo el mismo día.
			if (fechaActualCadena.equals(fechaRegistradaCadena)) {

				// Validamos la cantidad de horas.
				if (minutos < (60 * 12)) {

					facturaRepo.delete(factura);
					Integer result = pedidoRepo.deletePedido(pedido.getNumeroPedido());

					if (result > 0) {
						facturaNueva = new FacturaDto();
						facturaNueva.setResultado("OK");
						facturaNueva.setMensaje("¡Pedido eliminado correctamente!");
					}

				} else {

					List<Double> valores = pedidoRepo.findBynumeroPedido(pedido.getNumeroPedido());
					Double valor = obtenerValorPedido(valores);

					facturaNueva = facturaCancelada(valor);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return facturaNueva;
	}
	
	private FacturaDto facturaCancelada(Double valor) {
		
		Factura factura = new Factura();

		totalPagar = valor * 0.10d;
		factura = mapFactura(0L, 0D, 0D, totalPagar);
		factura = facturaRepo.save(factura);
		
		FacturaDto facturaDto = mapFac(factura);
		
		if (factura.getId() != null) {
			facturaDto.setId(factura.getId());
			facturaDto.setResultado("OK");
			facturaDto.setMensaje("¡Factura generada satisfactoriamente!");
		} else {
			facturaDto.setResultado("ERROR");
			facturaDto.setMensaje("¡No se pudo generar la factura!");
		}
		return facturaDto;
	}

	@Override
	public FacturaDto deleteProductInPedido(PedidoDto pedido) {
		try {
			
			Date fechaActual = new Date();

			Factura factura = facturaRepo.getByIdPedido(pedido.getNumeroPedido());

			Date fechaRegistrada = factura.getFechaCreacion();

			String fechaActualCadena = dateFormat.format(fechaActual);
			String fechaRegistradaCadena = dateFormat.format(fechaRegistrada);

			String horaActual = horaFormat.format(fechaActual);
			LocalTime actual = LocalTime.parse(horaActual);

			String horaReg = horaFormat.format(fechaRegistrada);
			LocalTime registro = LocalTime.parse(horaReg);

			int minutos = (int) ChronoUnit.MINUTES.between(registro, actual);

			// Validamos que el pedido se hizo el mismo día.
			if (fechaActualCadena.equals(fechaRegistradaCadena)) {

				// Validamos la cantidad de horas.
				if (minutos < (60 * 12)) {

					facturaRepo.delete(factura);
					Integer result = pedidoRepo.deleteProductInPedido(pedido.getIdProducto(),pedido.getNumeroPedido());

					if (result > 0) {
						facturaNueva = new FacturaDto();
						facturaNueva.setResultado("OK");
						facturaNueva.setMensaje("¡Producto eliminado correctamente!");
						facturaRepo.delete(factura);
						List<Double> valores = pedidoRepo.findBynumeroPedido(pedido.getNumeroPedido());
						Double valor = obtenerValorPedido(valores);
						ped = map(pedido);
						facturaNueva = generarFactura(valor, ped);
					}

				} else {

					List<Double> valores = pedidoRepo.findBynumeroPedido(pedido.getNumeroPedido());
					Double valor = obtenerValorPedido(valores);

					facturaNueva = facturaCancelada(valor);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return facturaNueva;
	}

}
