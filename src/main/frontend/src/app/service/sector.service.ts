import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Sector} from "../model/sector";

@Injectable()
export class SectorService {
    private httpService: HttpClient;
    constructor(httpService: HttpClient) {
        this.httpService = httpService;
    }

    public async getSectors() {
        return await this.httpService.get<Sector>("http://localhost:8080/api/sectors")
            .toPromise();
    }
}