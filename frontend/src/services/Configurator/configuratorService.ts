import { AxiosRequestConfig } from "axios";
import { BPConfigurator } from "models/entities";
import { requestBackend } from "util/requests";

export function mdpConfigurator(obj: BPConfigurator, edgeLength: number, edgeWidth: number, ghostSuffix: string, glueCode: number, 
    cornerBracketCode: number, plasticCode: number, nonwovenFabricCode: number, polyethyleneCode: number, upper: boolean, additional: number,
    width: number, quantity: number, oneFace: boolean, implementation: string) {
    const config: AxiosRequestConfig = {
        method: "POST",
        url: "/mdpConfigurator",
        params: {
            edgeLength,
            edgeWidth,
            ghostSuffix,
            glueCode,
            cornerBracketCode,
            plasticCode,
            nonwovenFabricCode,
            polyethyleneCode,
            upper,
            additional,
            width,
            quantity,
            oneFace,
            implementation
        },
        withCredentials: true,
        data: obj
    }

    return requestBackend(config);
}