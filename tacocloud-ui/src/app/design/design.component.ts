import {Component, Inject, Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaderResponse, HttpHeaders} from '@angular/common/http';
import {NgModel} from '@angular/forms';

@Component({
  selector: 'app-design',
  templateUrl: './design.component.html',
  styleUrls: ['./design.component.css']
})

@Injectable()
export class DesignComponent implements OnInit {

  model: NgModel;
  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
  }

  onSubmit() {
    this.httpClient.post(
      'http://localhost:8080/design',
      this.model, {
        headers: new HttpHeaders().set('Content-type', 'application/json'),
      }
    ).subscribe(taco => this.cart.addToCart(taco));

    this.router.navigate(['/cart']);
  }

}
