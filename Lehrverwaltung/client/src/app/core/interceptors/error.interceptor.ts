import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private  router: Router, private authService: AuthService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const clonedRequest =
            request.clone(
                {withCredentials: true}
            );
        return next.handle(clonedRequest)
            .pipe(
                map((event: HttpEvent<any>) => {
                    return event;
                }),
                catchError(error => {
                    if (error.status === 401) {
                        this.authService.logout();
                    }
                    return throwError(error);
                }));
    }
}
