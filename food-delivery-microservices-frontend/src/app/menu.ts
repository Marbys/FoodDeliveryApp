export class Menu {
  description: string;
  dishId: number;
  name: string;
  price: number;
  restaurantId: number;


  constructor(description: string, dishId: number, name: string, price: number, restaurantId: number) {
    this.description = description;
    this.dishId = dishId;
    this.name = name;
    this.price = price;
    this.restaurantId = restaurantId;
  }
}
