/**
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *//* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional }                      from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent }                           from '@angular/common/http';
import { CustomHttpUrlEncodingCodec }                        from '../encoder';

import { Observable }                                        from 'rxjs';

import { PageAdditionRequest } from '../model/pageAdditionRequest';
import { PageInfo } from '../model/pageInfo';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class PageAPIService {

    protected basePath = 'http://localhost:8443/';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds a product to a page
     * Adds a product to a given page at the coordinates (x,y)
     * @param body The request for adding the product to the page
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public addItemToPage(body?: PageAdditionRequest, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public addItemToPage(body?: PageAdditionRequest, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public addItemToPage(body?: PageAdditionRequest, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public addItemToPage(body?: PageAdditionRequest, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {


        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            '*/*'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<boolean>('post',`${this.basePath}/page/addItemToPage`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Removes all items from a Page
     * Removes the products on a given page
     * @param body The Page to have the products removed from
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public clearPage(body?: PageInfo, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public clearPage(body?: PageInfo, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public clearPage(body?: PageInfo, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public clearPage(body?: PageInfo, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {


        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            '*/*'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<boolean>('post',`${this.basePath}/page/clearPage`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Creates a page
     * This method creates a page to be used within the system.
     * @param body The page to be created
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public createPage(body?: PageInfo, observe?: 'body', reportProgress?: boolean): Observable<PageInfo>;
    public createPage(body?: PageInfo, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PageInfo>>;
    public createPage(body?: PageInfo, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PageInfo>>;
    public createPage(body?: PageInfo, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {


        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            '*/*'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<PageInfo>('post',`${this.basePath}/page/createPage`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Deletes a page
     * Deletes a page that is associated to a given id
     * @param id The id of the page to delete
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deletePage(id: number, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public deletePage(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public deletePage(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public deletePage(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling deletePage.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<boolean>('delete',`${this.basePath}/page/deletePage/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Gets a page
     * Gets a page from a given ID.
     * @param id The ID of the page
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getPage(id: number, observe?: 'body', reportProgress?: boolean): Observable<PageInfo>;
    public getPage(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PageInfo>>;
    public getPage(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<PageInfo>>;
    public getPage(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling getPage.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<PageInfo>('get',`${this.basePath}/page/getPage/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Gets a list of all pages
     * Gets all pages from db.
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getPages(observe?: 'body', reportProgress?: boolean): Observable<Array<PageInfo>>;
    public getPages(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<PageInfo>>>;
    public getPages(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<PageInfo>>>;
    public getPages(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<PageInfo>>('get',`${this.basePath}/page/getPages`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Removes an item from a Page
     * Removes the product on a given page at the coordinates (x,y)
     * @param x X coordinate of the Page
     * @param y Y coordinate of the Page
     * @param body The Page to have the product removed from
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public removeItemFromPage(x: number, y: number, body?: PageInfo, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public removeItemFromPage(x: number, y: number, body?: PageInfo, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public removeItemFromPage(x: number, y: number, body?: PageInfo, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public removeItemFromPage(x: number, y: number, body?: PageInfo, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (x === null || x === undefined) {
            throw new Error('Required parameter x was null or undefined when calling removeItemFromPage.');
        }

        if (y === null || y === undefined) {
            throw new Error('Required parameter y was null or undefined when calling removeItemFromPage.');
        }


        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            '*/*'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<boolean>('post',`${this.basePath}/page/removeItemFromPage/${encodeURIComponent(String(x))}/${encodeURIComponent(String(y))}`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Updates a page
     * Updates a page, uses the id the found within the object to update that record within the database
     * @param body The page to be updated
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updatePage(body?: PageInfo, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public updatePage(body?: PageInfo, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public updatePage(body?: PageInfo, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public updatePage(body?: PageInfo, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {


        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            '*/*'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<boolean>('post',`${this.basePath}/page/updatePage`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
