import { AxiosRequestConfig } from "axios";
import { DGhost } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/ghosts",
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
        url: "/ghosts/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/ghosts/${id}`, withCredentials: true });
}

export function insert(obj: DGhost) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/ghosts",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DGhost) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/ghosts/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/ghosts/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/ghosts/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}