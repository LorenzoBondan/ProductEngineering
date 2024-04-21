import { AxiosRequestConfig } from "axios";
import { DMolding } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/moldings",
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
        url: "/moldings/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/moldings/${id}`, withCredentials: true });
}

export function insert(obj: DMolding) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/moldings",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DMolding) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/moldings/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/moldings/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/moldings/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}