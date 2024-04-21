import { AxiosRequestConfig } from "axios";
import { DSon } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/sons",
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
        url: "/sons/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/sons/${id}`, withCredentials: true });
}

export function insert(obj: DSon) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/sons",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DSon) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/sons/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/sons/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/sons/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}