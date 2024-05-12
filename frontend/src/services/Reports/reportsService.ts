import { requestBackend } from "util/requests";

export function report(id: number) {
    return requestBackend({
        url: `/fatherReport/${id}`, 
        withCredentials: true,
        responseType: 'blob' 
    });
}