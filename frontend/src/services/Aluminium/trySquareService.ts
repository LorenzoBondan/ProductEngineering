import { AxiosRequestConfig } from "axios";
import { DTrySquare } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/trySquares",
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
        url: "/trySquares/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/trySquares/${id}`, withCredentials: true });
}

export function insert(obj: DTrySquare) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/trySquares",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DTrySquare) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/trySquares/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/trySquares/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/trySquares/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}