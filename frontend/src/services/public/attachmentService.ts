import { AxiosRequestConfig } from "axios";
import { DAttachment } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/attachments",
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
        url: "/attachments/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/attachments/${id}`, withCredentials: true });
}

export function insert(obj: DAttachment) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/attachments",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DAttachment) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/attachments/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/attachments/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/items/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}