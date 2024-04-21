import { AxiosRequestConfig } from "axios";
import { DColor } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/colors",
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
        url: "/colors/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/colors/${id}`, withCredentials: true });
}

export function insert(obj: DColor) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/colors",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DColor) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/colors/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/colors/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/colors/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}