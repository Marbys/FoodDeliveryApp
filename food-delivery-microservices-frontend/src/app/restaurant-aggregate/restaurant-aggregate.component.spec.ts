import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantAggregateComponent } from './restaurant-aggregate.component';

describe('RestaurantAggregateComponent', () => {
  let component: RestaurantAggregateComponent;
  let fixture: ComponentFixture<RestaurantAggregateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantAggregateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestaurantAggregateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
