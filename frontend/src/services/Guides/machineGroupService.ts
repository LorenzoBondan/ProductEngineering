import { AxiosRequestConfig } from "axios";
import { DMachineGroup } from "models/entities";
import { requestBackend } from "util/requests";

export function findAll(name: string, page?: number, size?: number, status?: string, sort = "id") {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: "/machineGroups",
        params: {
            name,
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
        url: "/machineGroups/activeAndCurrentOne",
        params: {
            id
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function findById(id: number) {
    return requestBackend({ url: `/machineGroups/${id}`, withCredentials: true });
}

export function insert(obj: DMachineGroup) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/machineGroups",
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function update(obj: DMachineGroup) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/machineGroups/${obj.id}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function inactivate(id: number) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `/machineGroups/inactivate/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}

export function deleteById(id: number) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `/machineGroups/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}