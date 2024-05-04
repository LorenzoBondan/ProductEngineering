import { AxiosRequestConfig } from "axios";
import { DPaintingSon } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/paintingSons",
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
        url: "/paintingSons/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/paintingSons/${id}`, withCredentials: true });
}

export function insert(obj: DPaintingSon) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/paintingSons",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPaintingSon) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingSons/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingSons/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/paintingSons/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}