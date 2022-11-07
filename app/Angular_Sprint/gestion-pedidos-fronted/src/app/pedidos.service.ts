import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pedido } from './pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidosService {

  private baseUrl = "http://localhost:8084/v1/api/producto";

  constructor(private httpClient : HttpClient) { }

  obtenerListaPedidos():Observable<Pedido[]>{
    return this.httpClient.get<Pedido[]>(`${this.baseUrl} `);
  }
 
}
