import { AxiosRequestConfig } from "axios";
import { DPolyester } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/polyesters",
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
        url: "/polyesters/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/polyesters/${id}`, withCredentials: true });
}

export function insert(obj: DPolyester) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/polyesters",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPolyester) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/polyesters/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/polyesters/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/polyesters/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}