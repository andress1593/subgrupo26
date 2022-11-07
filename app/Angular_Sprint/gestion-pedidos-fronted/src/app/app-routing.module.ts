import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaPedidosComponent } from './lista-pedidos/lista-pedidos.component';
import { RegistrarPedidoComponent } from './registrar-pedido/registrar-pedido.component';

const routes: Routes = [
  {path : 'productos', component:ListaPedidosComponent},
  {path: '',redirectTo:'productos',pathMatch:'full'},
  {path: 'registrar-pedido',component : RegistrarPedidoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
