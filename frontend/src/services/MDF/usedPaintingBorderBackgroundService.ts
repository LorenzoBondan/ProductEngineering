import { AxiosRequestConfig } from "axios";
import { DUsedPaintingBorderBackground } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedPaintingBorderBackgrounds",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedPaintingBorderBackgrounds/${id}`, withCredentials: true });
}

export function insert(obj: DUsedPaintingBorderBackground) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedPaintingBorderBackgrounds",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedPaintingBorderBackground) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedPaintingBorderBackgrounds/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedPaintingBorderBackgrounds/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}