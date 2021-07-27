import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RestaurantComponent} from "./restaurant/restaurant.component";
import {RestaurantAggregateComponent} from "./restaurant-aggregate/restaurant-aggregate.component";

const routes: Routes = [
  { path: 'restaurant', component: RestaurantAggregateComponent},
  { path: 'restaurant/:restaurantId', component: RestaurantComponent},
  // { path: '/order', component: DashboardComponent},
  //{ path: '**', component: PageNotFoundComponent },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
