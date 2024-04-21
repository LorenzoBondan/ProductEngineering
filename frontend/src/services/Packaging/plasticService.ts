import { AxiosRequestConfig } from "axios";
import { DPlastic } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/plastics",
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
        url: "/plastics/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/plastics/${id}`, withCredentials: true });
}

export function insert(obj: DPlastic) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/plastics",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPlastic) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/plastics/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/plastics/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/plastics/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}