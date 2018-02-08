import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {SectorService} from "./service/sector.service";
import {FormSubmissionService} from "./service/form-submission.service";
import {FormsModule} from "@angular/forms";
import {AlertModule} from "ngx-bootstrap";


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        AlertModule.forRoot()
    ],
    bootstrap: [AppComponent],
    providers: [
        SectorService,
        FormSubmissionService
    ]
})
export class AppModule {
}
