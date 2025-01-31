import { DSituacaoEnum } from "./enums/situacao";

export type DRoteiro = {
    codigo: number;
    descricao: string;
    implantacao: Date;
    dataFinal: Date;
    valor: number;
    situacao: keyof typeof DSituacaoEnum;
};