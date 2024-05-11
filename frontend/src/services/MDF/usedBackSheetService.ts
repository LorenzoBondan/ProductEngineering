import { AxiosRequestConfig } from "axios";
import { DUsedBackSheet } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedBackSheets",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedBackSheets/${id}`, withCredentials: true });
}

export function insert(obj: DUsedBackSheet) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedBackSheets",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedBackSheet) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedBackSheets/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedBackSheets/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}