import {Menu} from "./menu";
import {Order} from "./order";
import {ServiceAddresses} from "./service-addresses";

export class Restaurant {
  restaurantId: number;
  name: string;
  rating: number;
  serviceAddresses: ServiceAddresses;


  constructor(restaurantId: number, name: string, rating: number, serviceAddresses: ServiceAddresses) {
    this.restaurantId = restaurantId;
    this.name = name;
    this.rating = rating;
    this.serviceAddresses = serviceAddresses;
  }
}
