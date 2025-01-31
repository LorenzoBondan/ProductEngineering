import { DSituacaoEnum } from "./enums/situacao";

export type DCor = {
    codigo: number;
    descricao: string;
    situacao: keyof typeof DSituacaoEnum;
};