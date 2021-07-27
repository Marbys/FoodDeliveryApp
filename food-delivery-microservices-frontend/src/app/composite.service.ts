import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {RestaurantAggregate} from "./restaurant-aggregate";
import {Order} from "./order";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CompositeService {
  private baseUrl = "http://localhost:8080/composite-restaurant/"

  constructor(private http: HttpClient) { }

  getRestaurantAggregate(restaurantId: number): Observable<RestaurantAggregate>{
    return this.http.get<RestaurantAggregate>(`${this.baseUrl + restaurantId}`);
  }

  getAllRestaurants(): Observable<RestaurantAggregate[]>{
    return this.http.get<RestaurantAggregate[]>(`http://localhost:8080/restaurants`)
  }

  makeOrder(order: Order): void {
    console.log("Post sent!")
    this.http.post(`http://localhost:8080/order`, order, httpOptions ).subscribe();
  }
}
