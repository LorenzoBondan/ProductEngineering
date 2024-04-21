import { AxiosRequestConfig } from "axios";
import { DItem } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/items",
        params: {
            name,
            page,
            size,
            sort,
            status
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findAllActiveAndCurrentOne(id: number) {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/items/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/items/${id}`, withCredentials: true });
}

export function insert(obj: DItem) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/items",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DItem) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/items/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/items/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/items/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}