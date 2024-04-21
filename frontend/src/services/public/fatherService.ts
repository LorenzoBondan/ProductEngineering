import { AxiosRequestConfig } from "axios";
import { DFather } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/fathers",
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
        url: "/fathers/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/fathers/${id}`, withCredentials: true });
}

export function insert(obj: DFather) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/fathers",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DFather) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/fathers/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/fathers/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/fathers/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}