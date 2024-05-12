import { AxiosRequestConfig } from "axios";
import { DGuide } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description?: string, page?: number, size?: number, status?: string, sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/guides",
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
        url: "/guides/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/guides/${id}`, withCredentials: true });
}

export function insert(obj: DGuide) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/guides",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DGuide) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/guides/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/guides/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/guides/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}