import {Component, Input, OnInit} from '@angular/core';
import {Router, ActivatedRoute, ParamMap} from "@angular/router";
import {RestaurantAggregate} from "../restaurant-aggregate";
import {CompositeService} from "../composite.service";
import {DishSummary} from "../dish-summary";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from '@angular/material/list';
import {newArray} from "@angular/compiler/src/util";
import {Order} from "../order";


@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {
  restaurant!: RestaurantAggregate;
  restaurantId!: number;
  cart: DishSummary[] = [];
  d: Date = new Date();

  constructor(private route: ActivatedRoute, private service: CompositeService) {
  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.restaurantId = Number(routeParams.get('restaurantId'));
    this.service.getRestaurantAggregate(this.restaurantId).subscribe(params => {
      this.restaurant = params;
    });
  }

  addDish(menu: DishSummary) {
    this.cart[this.cart.length] = menu;
  }

  makeOrder(cart: DishSummary[]) {
    this.service.makeOrder(new Order("Address", this.d, 1, cart));
    window.alert("Zamówienie złożone.");
    cart.splice(0,cart.length);
    this.cart = cart;
  }

  removeFromCart(cart: DishSummary[], index: number) {
    cart.splice(index, 1)
    this.cart = cart;
  }
}
