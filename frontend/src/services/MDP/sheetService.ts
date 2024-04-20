import { AxiosRequestConfig } from "axios";
import { DSheet } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page: number, size: number, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/sheets",
        params: {
            description,
            page,
            size,
            sort 
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/sheets/${id}`, withCredentials: true });
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/sheets/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function update(obj: DSheet) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/sheets/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function insert(obj: DSheet) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/sheets",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}