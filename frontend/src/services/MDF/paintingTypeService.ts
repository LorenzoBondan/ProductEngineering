import { AxiosRequestConfig } from "axios";
import { DPainting } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/paintingTypes",
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
        url: "/paintingTypes/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/paintingTypes/${id}`, withCredentials: true });
}

export function insert(obj: DPainting) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/paintingTypes",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPainting) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingTypes/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingTypes/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/paintingTypes/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}