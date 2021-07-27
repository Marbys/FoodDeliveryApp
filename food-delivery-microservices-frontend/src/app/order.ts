import {DishSummary} from "./dish-summary";


export class Order {
  customerAddress: string;
  orderCreatedAt: Date;
  orderId: number;
  dishSummaries: DishSummary[];

  constructor(customerAddress: string, orderCreatedAt: Date, orderId: number, requestedDishes: DishSummary[]) {
    this.customerAddress = customerAddress;
    this.orderCreatedAt = orderCreatedAt;
    this.orderId = orderId;
    this.dishSummaries = requestedDishes;
  }
}
