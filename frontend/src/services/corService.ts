import { AxiosRequestConfig } from "axios";
import { requestBackend } from "../utils/requests";
import { DCor } from "../models/cor";

const route = "/api/cor";

export function pesquisarTodos(colunas: string, operacoes: string, valores: string, page?: number, pageSize?: number, sort?: string) {
    sort = sort || "codigo;d";
    
    const config : AxiosRequestConfig = {
        method: "GET",
        url: `${route}`,
        params: {
            colunas,
            operacoes,
            valores,
            sort,
            page,
            pageSize
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function pesquisarPorId(id: number) {
    return requestBackend({ 
        url: `${route}/${id}`,
        withCredentials: true 
    });
}

export function pesquisarHistorico(codigo: number) {
    const config : AxiosRequestConfig = {
        method: "GET",
        url: `${route}/historico`,
        params: {
            codigo
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function criar(obj: DCor) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: `${route}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function atualizar(obj: DCor) {
    const config: AxiosRequestConfig = {
        method: "PUT",
        url: `${route}`,
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}

export function substituirVersao(codigoRegistro: number, codigoVersao: number) {
    const config : AxiosRequestConfig = {
        method: "PUT",
        url: `${route}/substituir`,
        params: {
            codigoRegistro,
            codigoVersao
        },
        withCredentials: true
    }

    return requestBackend(config);
}

export function inativar(id: number[]) {
    const config : AxiosRequestConfig = {
        method: "PATCH",
        url: `${route}/inativar`,
        withCredentials: true,
        params: {
            id
        }
    }

    return requestBackend(config);
}

export function remover(id: number[]) {
    const config : AxiosRequestConfig = {
        method: "DELETE",
        url: `${route}`,
        withCredentials: true,
        params: {
            id
        }
    }

    return requestBackend(config);
}