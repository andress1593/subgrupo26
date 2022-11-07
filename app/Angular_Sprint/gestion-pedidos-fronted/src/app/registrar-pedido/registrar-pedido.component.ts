import { Component, OnInit } from '@angular/core';
import { Pedido } from '../pedido';

@Component({
  selector: 'app-registrar-pedido',
  templateUrl: './registrar-pedido.component.html',
  styleUrls: ['./registrar-pedido.component.css']
})
export class RegistrarPedidoComponent implements OnInit {

  producto : Pedido = new Pedido();
  constructor() { }

  ngOnInit(): void {
    console.log(this.producto)
  }

  onSubmit(){
    console.log(this.producto)
  }

}
