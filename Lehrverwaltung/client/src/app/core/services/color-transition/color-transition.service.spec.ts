import { TestBed } from '@angular/core/testing';

import { ColorTransitionService } from './color-transition.service';

describe('ColorTransitionService', () => {
  let service: ColorTransitionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColorTransitionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
