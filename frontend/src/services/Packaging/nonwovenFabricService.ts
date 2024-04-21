import { AxiosRequestConfig } from "axios";
import { DNonwovenFabric } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/nonwovenFabrics",
        params: {
            description,
            page,
            size,
            sort,
            status: status
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findAllActiveAndCurrentOne(id: number) {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/nonwovenFabrics/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/nonwovenFabrics/${id}`, withCredentials: true });
}

export function insert(obj: DNonwovenFabric) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/nonwovenFabrics",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DNonwovenFabric) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/nonwovenFabrics/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/nonwovenFabrics/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/nonwovenFabrics/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}