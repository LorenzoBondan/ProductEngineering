import { AxiosRequestConfig } from "axios";
import { DBack } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/backs",
        params: {
            description,
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
        url: "/backs/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/backs/${id}`, withCredentials: true });
}

export function insert(obj: DBack) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/backs",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DBack) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/backs/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/backs/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/backs/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}