import {Component} from '@angular/core';
import {SectorService} from "./service/sector.service";
import {Sector} from "./model/sector";
import {DomSanitizer} from "@angular/platform-browser";
import {FormSubmission} from "./model/form-submission";
import {NgModel} from "@angular/forms";
import {FormSubmissionService} from "./service/form-submission.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    private sectorService: SectorService;
    private formSubmissionService: FormSubmissionService;
    private sanitizer: DomSanitizer;
    private sectorList: Sector[];
    public formSubmission :FormSubmission;
    public isReady: boolean;
    public termsChecked: boolean;
    public alerts: string[] = [];

    constructor(sectorService: SectorService, formSubmissionService: FormSubmissionService ,sanitizer: DomSanitizer) {
        this.sectorService = sectorService;
        this.formSubmissionService = formSubmissionService;
        this.sanitizer = sanitizer;
        this.isReady = false;
    }

    async ngOnInit() {
        let sectorRoot = await this.sectorService.getSectors();
        this.sectorList = this.traverseTree(sectorRoot, 0);
        this.sectorList.shift(); // remove root

        let username = localStorage.getItem("username");
        this.formSubmission = await this.formSubmissionService.getForm(username);
        this.isReady = true;

        for (let sector of this.sectorList) {
            if (this.formSubmission.selectedSectorIds.indexOf(sector.id) != -1) {
                console.log("flagging sector "+ sector.id);
                sector.isSelected = true;
            }
        }
        console.log(this.sectorList);
    }

    traverseTree(root :Sector, level :number) : Sector[]{
        root.level = level;
        root.isSelected = false;
        let children = [];

        for (let child of root.children) {
            children.push(...this.traverseTree(child, level + 1));
        }

        return [root, ...children]
    }

    getIndentStyle(level: number) {
        return this.sanitizer.bypassSecurityTrustStyle("margin-left: " + (level - 1) * 2 + "em")
    }

    async submitForm() {
        if (!this.validateFormSubmission(this.formSubmission)) {
            return;
        }

        localStorage.setItem("username", this.formSubmission.name);
        this.isReady = false;

        await this.formSubmissionService.postForm(this.formSubmission);
        this.formSubmission = await this.formSubmissionService.getForm(this.formSubmission.name);

        this.termsChecked = false;
        this.isReady = true;
    }

    validateFormSubmission(formSubmission: FormSubmission): boolean {
        this.alerts = [];
        let result = true;
        if (!this.termsChecked) {
            this.alerts.push("Please accept terms before submitting the form.");
            result = false;
        }

        if (formSubmission.name.length < 1) {
            this.alerts.push("Please enter your name.");
            result = false;
        }

        if (formSubmission.selectedSectorIds.length < 1) {
            this.alerts.push("Please select at least one sector.");
            result = false;
        }

        return result;
    }
}
