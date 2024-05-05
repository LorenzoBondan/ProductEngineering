import { AxiosRequestConfig } from "axios";
import { DUsedEdgeBanding } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/usedEdgeBandings",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/usedEdgeBandings/${id}`, withCredentials: true });
}

export function insert(obj: DUsedEdgeBanding) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/usedEdgeBandings",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DUsedEdgeBanding) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/usedEdgeBandings/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/usedEdgeBandings/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}