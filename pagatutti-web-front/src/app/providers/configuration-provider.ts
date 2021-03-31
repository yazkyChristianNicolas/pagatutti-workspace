import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ConfigurationModel } from '../models/configuration-model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationProvider {

  private configuration: ConfigurationModel = null;
    

  constructor(private http: HttpClient,  @Inject('BASE_API_URL') private baseUrl: string) {
  }

  public getConfiguration(): ConfigurationModel {
      return this.configuration;
  }

  load() {
      return new Promise((resolve, reject) => {
          this.http.get<ConfigurationModel>( this.baseUrl + '/admin/configuration').subscribe(response => {
              console.log("Configuration providers");
              this.configuration = response;
              console.log(this.configuration);
              resolve(true);
          });
      })
  }
}
