import { AxiosRequestConfig } from "axios";
import { DMachine } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/machines",
        params: {
            name,
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
        url: "/machines/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/machines/${id}`, withCredentials: true });
}

export function insert(obj: DMachine) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/machines",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DMachine) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/machines/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/machines/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/machines/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}