import { AxiosRequestConfig } from "axios";
import { DMaterial } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/materials",
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
        url: "/materials/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/materials/${id}`, withCredentials: true });
}

export function insert(obj: DMaterial) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/materials",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DMaterial) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/materials/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/materials/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/materials/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}