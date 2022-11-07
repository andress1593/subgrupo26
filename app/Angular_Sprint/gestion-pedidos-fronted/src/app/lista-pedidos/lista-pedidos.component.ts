import { Component, OnInit } from '@angular/core';
import { Pedido } from '../pedido';
import { PedidosService } from '../pedidos.service';

@Component({
  selector: 'app-lista-pedidos',
  templateUrl: './lista-pedidos.component.html',
  styleUrls: ['./lista-pedidos.component.css']
})
export class ListaPedidosComponent implements OnInit {

  pedidos:Pedido[]
  constructor(private pedidoServicio:PedidosService) { }

  ngOnInit(): void {
    this.obtenerPedidos();
  }

  private obtenerPedidos(){
    this.pedidoServicio.obtenerListaPedidos().subscribe(dato => {
      this.pedidos = dato;
    });
  }

}
