import { TestBed } from '@angular/core/testing';

import { ChipExerciseService } from './chip-exercise.service';

describe('ChipExerciseService', () => {
  let service: ChipExerciseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChipExerciseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
