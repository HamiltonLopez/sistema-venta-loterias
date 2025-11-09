import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SorteoCreate } from './sorteo-create';

describe('SorteoCreate', () => {
  let component: SorteoCreate;
  let fixture: ComponentFixture<SorteoCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SorteoCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SorteoCreate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
