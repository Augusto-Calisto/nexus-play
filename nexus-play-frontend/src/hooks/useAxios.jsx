import { useCallback } from "react";

import axios from "axios";

const BASE_URL = import.meta.env.VITE_SERVICE_URL;

let api;

export function useAxios() {
    if(!api) {
        api = axios.create({
            baseURL: BASE_URL,
            headers: { "Content-Type": "application/json" },
            timeout: 10000,
            withCredentials: true
        });

        api.interceptors.request.use((config) => {
            console.log("Enviando requisição para: ", config.url);
            
            return config;

        }, (exception) => {
            return Promise.reject(exception);
        });

        // Interceptor de Resposta (Erros globais)
        api.interceptors.response.use((response) => {            
            return response;

        }, (exception) => {
            if (Object.hasOwn(exception, "response")) {
                return Promise.reject(exception.response);
            }
            
            return Promise.reject(exception);
        });
    }

    // O useCallback garante que a referência dessas funções permanece a mesma
    const get = useCallback(async (uri, options) => {
        return await api.get(uri, options);
    }, [api]);

    const post = useCallback(async (uri, body, options) => {
        return await api.post(uri, body, options);
    }, [api]);

    const put = useCallback(async (uri, body, options) => {
        return await api.put(uri, body, options);
    }, [api]);

    const del = useCallback(async (uri, options) => {
        return await api.delete(uri, options);
    }, [api]);

    return {
        get,
        post,
        put,
        del,
    };
}