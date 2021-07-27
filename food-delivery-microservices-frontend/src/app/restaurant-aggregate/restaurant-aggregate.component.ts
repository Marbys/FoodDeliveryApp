import { Component, OnInit } from '@angular/core';
import {CompositeService} from "../composite.service";
import {RestaurantAggregate} from "../restaurant-aggregate";
import {Restaurant} from "../restaurant";

@Component({
  selector: 'app-restaurant-aggregate',
  templateUrl: './restaurant-aggregate.component.html',
  styleUrls: ['./restaurant-aggregate.component.css']
})
export class RestaurantAggregateComponent implements OnInit {

  restaurants!: Restaurant[];
  image: string[] = ["hamburger","pizza", "spaghetti", "sushi"];

  constructor(private compositeService: CompositeService) { }

  ngOnInit(): void {
    this.compositeService.getAllRestaurants().subscribe((data: Restaurant[]) => {
      this.restaurants = data;
    })
  }

}
