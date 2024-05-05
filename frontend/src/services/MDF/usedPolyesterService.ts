import { AxiosRequestConfig } from "axios";
import { DUsedPolyester } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedPolyesters",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedPolyesters/${id}`, withCredentials: true });
}

export function insert(obj: DUsedPolyester) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedPolyesters",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedPolyester) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedPolyesters/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedPolyesters/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}