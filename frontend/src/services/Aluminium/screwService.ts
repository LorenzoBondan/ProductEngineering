import { AxiosRequestConfig } from "axios";
import { DScrew } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/screws",
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
        url: "/screws/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/screws/${id}`, withCredentials: true });
}

export function insert(obj: DScrew) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/screws",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DScrew) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/screws/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/screws/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/screws/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}