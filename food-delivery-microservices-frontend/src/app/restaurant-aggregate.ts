import {ServiceAddresses} from "./service-addresses";
import {Order} from "./order";
import {Menu} from "./menu";

  export class RestaurantAggregate {
    menu: Menu[];
    name: string;
    orders: Order[];
    rating: number;
    restaurantId: number;
    serviceAddresses: ServiceAddresses;


    constructor(menu: Menu[], name: string, orders: Order[], rating: number, restaurantId: number, serviceAddresses: ServiceAddresses) {
      this.menu = menu;
      this.name = name;
      this.orders = orders;
      this.rating = rating;
      this.restaurantId = restaurantId;
      this.serviceAddresses = serviceAddresses;
    }
  }

