import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {FormSubmission} from "../model/form-submission";

@Injectable()
export class FormSubmissionService {
    private httpService: HttpClient;

    constructor(httpService: HttpClient) {
        this.httpService = httpService;
    }

    public async getForm(name) {
        return await this.httpService.get<FormSubmission>("http://localhost:8080/api/form-submissions/" + name)
            .toPromise();
    }

    public async postForm(formSubmission: FormSubmission) {
        return await this.httpService.post<FormSubmission>("http://localhost:8080/api/form-submissions", formSubmission)
            .toPromise();
    }
}