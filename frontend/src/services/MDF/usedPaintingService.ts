import { AxiosRequestConfig } from "axios";
import { DUsedPainting } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedPaintings",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedPaintings/${id}`, withCredentials: true });
}

export function insert(obj: DUsedPainting) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedPaintings",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedPainting) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedPaintings/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedPaintings/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}