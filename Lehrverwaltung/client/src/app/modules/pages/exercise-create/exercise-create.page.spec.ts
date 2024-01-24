import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {ExerciseCreatePage} from './exercise-create.page';

describe('ExerciseCreatePage', () => {
  let component: ExerciseCreatePage;
  let fixture: ComponentFixture<ExerciseCreatePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciseCreatePage],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ExerciseCreatePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
