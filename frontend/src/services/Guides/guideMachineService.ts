import { AxiosRequestConfig } from "axios";
import { DGuideMachine } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/guideMachines",
        params: {
            sort,
        },
        withCredentials: true

    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/guideMachines/${id}`, withCredentials: true });
}

export function insert(obj: DGuideMachine) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/guideMachines",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DGuideMachine) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/guideMachines/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/guideMachines/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}