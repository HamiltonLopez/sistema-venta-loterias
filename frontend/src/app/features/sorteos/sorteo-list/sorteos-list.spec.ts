import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SorteosList } from './sorteos-list';

describe('SorteosList', () => {
  let component: SorteosList;
  let fixture: ComponentFixture<SorteosList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SorteosList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SorteosList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
