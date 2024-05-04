import { AxiosRequestConfig } from "axios";
import { DUsedSheet } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedSheets",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedSheets/${id}`, withCredentials: true });
}

export function insert(obj: DUsedSheet) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedSheets",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedSheet) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedSheets/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedSheets/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}