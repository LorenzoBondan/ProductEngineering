import * as lixeiraService from "../../../services/lixeiraService";
import { useEffect, useState } from 'react';
import { DLixeira } from '../../../models/lixeira';
import SearchBar from '../../../components/SearchBar';
import ButtonNextPage from '../../../components/ButtonNextPage';
import { formatLocalDateTime } from '../../../utils/formatters';
import DialogConfirmation from "../../../components/DialogConfirmation";
import DialogInfo from "../../../components/DialogInfo";
import arrows from "../../../assets/images/arrows.png";

type QueryParams = {
    page: number;
    nometabela: string;
}

export default function Trash(){

    const [isLastPage, setIsLastPage] = useState(false);

    const [lixeiras, setLixeiras] = useState<DLixeira[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        nometabela: ""
    });

    const [dialogInfoData, setDialogInfoData] = useState({
        visible: false,
        message: "Sucesso!"
    });

    const [dialogConfirmationData, setDialogConfirmationData] = useState({
        visible: false,
        id: 0,
        message: "Deseja recuperar os dependentes desse item também?"
    });

    useEffect(() => {
        lixeiraService.pesquisarTodos('', '=', queryParams.nometabela, queryParams.page, 8, "id;a")
            .then(response => {
                const nextPage = response.data.content;
                setLixeiras(lixeiras.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);


    function handleSearch(searchText: string) {
        setLixeiras([]);
        setQueryParam({ ...queryParams, page: 0, nometabela: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleDialogConfirmationAnswer(answer: boolean, lixeiraId: number) {
        lixeiraService.recuperarPorId(lixeiraId, answer)
            .then(() => {
                setLixeiras([]);
                setQueryParam({ ...queryParams, page: 0 });
            })
            .catch(error => {
                setDialogInfoData({
                    visible: true,
                    message: error.response.data.error
                })
            });

        setDialogConfirmationData({ ...dialogConfirmationData, visible: false });
    }

    function handleRecuperarClick(lixeiraId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: lixeiraId, visible: true });
    }

    return(
        <main>
            <section id="listing-section" className="container">
                <h2 className="section-title mb20">Lixeira</h2>

                <SearchBar onSearch={handleSearch} />

                <table className="table mb20 mt20">
                    <thead>
                        <tr>
                            <th className="tb576">Código</th>
                            <th className="txt-left">Tabela</th>
                            <th className="txt-left">Data</th>
                            <th className="txt-left">ID da entidade</th>
                            <th className="txt-left">Usuário</th>
                            <th className="txt-left">Recuperar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            lixeiras
                            .map(lixeira => (
                                <tr key={lixeira.id} className={`situacao-${lixeira.situacao.toLowerCase()}`}>
                                    <td className="tb576">{lixeira.id}</td>
                                    <td className="txt-left">{lixeira.nometabela}</td>
                                    <td className="txt-left">{formatLocalDateTime(lixeira.data.toString())}</td>
                                    <td className="txt-left">{Object.values(lixeira.entidadeid).join(", ")}</td>
                                    <td className="txt-left">{lixeira.usuario}</td>
                                    <td className="txt-left" onClick={() => handleRecuperarClick(lixeira.id)}>
                                        <img src={arrows} alt="" style={{height:"20px", width:"20px", cursor:"pointer"}} />
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>

                {
                    !isLastPage &&
                    <ButtonNextPage onNextPage={handleNextPageClick} />
                }
            </section>

            {
                dialogInfoData.visible &&
                    <DialogInfo
                        message={dialogInfoData.message}
                        onDialogClose={handleDialogInfoClose}
                    />
            }
            
            {
                dialogConfirmationData.visible &&
                    <DialogConfirmation
                        id={dialogConfirmationData.id}
                        message={dialogConfirmationData.message}
                        onDialogAnswer={handleDialogConfirmationAnswer}
                    />
            }
        </main>
    );
}