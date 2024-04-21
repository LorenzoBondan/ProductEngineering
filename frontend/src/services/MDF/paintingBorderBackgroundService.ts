import { AxiosRequestConfig } from "axios";
import { DPaintingBorderBackground } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(description: string, page?: number, size?: number, status?: string, sort = "code") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/paintingBorderBackgrounds",
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
        url: "/paintings/paintingBorderBackgrounds",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/paintingBorderBackgrounds/${id}`, withCredentials: true });
}

export function insert(obj: DPaintingBorderBackground) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/paintingBorderBackgrounds",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DPaintingBorderBackground) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingBorderBackgrounds/${obj.code}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/paintingBorderBackgrounds/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/paintingBorderBackgrounds/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}