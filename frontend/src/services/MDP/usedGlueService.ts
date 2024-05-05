import { AxiosRequestConfig } from "axios";
import { DUsedGlue } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedGlues",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedGlues/${id}`, withCredentials: true });
}

export function insert(obj: DUsedGlue) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedGlues",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedGlue) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedGlues/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedGlues/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}