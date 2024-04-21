import { AxiosRequestConfig } from "axios";
import { DPainting } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/paintings",
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
        url: "/paintings/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/paintings/${id}`, withCredentials: true });
}

export function insert(obj: DPainting) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/paintings",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPainting) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintings/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintings/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/paintings/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}