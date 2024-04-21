import { AxiosRequestConfig } from "axios";
import { DPolyethylene } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/polyethylenes",
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
        url: "/polyethylenes/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/polyethylenes/${id}`, withCredentials: true });
}

export function insert(obj: DPolyethylene) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/polyethylenes",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPolyethylene) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/polyethylenes/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/polyethylenes/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/polyethylenes/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}