import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSliderModule} from '@angular/material/slider';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { DigitOnlyModule } from '@uiowa/digit-only';
import {MatDialogModule} from '@angular/material/dialog';
import { environment } from 'src/environments/environment';
import { ConfigurationProvider } from './providers/configuration-provider';
import { APP_INITIALIZER } from '@angular/core';
import { TermsAndConditionsComponent } from './terms-and-conditions/terms-and-conditions.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { FocusNextInputDirective } from './directives/focus-next-input.directive';
import { MessageDialog } from './modals/message-dialog/message-dialog.component';
import { MailValidationComponent } from './mail-validation/mail-validation.component';
import {MatStepperModule} from '@angular/material/stepper';
import {WebcamModule} from 'ngx-webcam';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';


export function configurationProviderFactory(provider: ConfigurationProvider) {
  return () => provider.load();
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PageNotFoundComponent,
    TermsAndConditionsComponent,
    FocusNextInputDirective,
    MessageDialog,
    MailValidationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule,
    MatSliderModule,
    FormsModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    HttpClientModule,
    DigitOnlyModule,
    ReactiveFormsModule,
    MatProgressBarModule,
    MatDialogModule,
    WebcamModule,
    MatStepperModule,
    MatToolbarModule,
    MatTooltipModule
  ],
  providers: [
    { provide: "BASE_API_URL", useValue: environment.apiUrl },
    ConfigurationProvider, 
    { provide: APP_INITIALIZER, useFactory: configurationProviderFactory, deps: [ConfigurationProvider], multi: true } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
