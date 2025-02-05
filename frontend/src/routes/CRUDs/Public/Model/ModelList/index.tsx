import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as modeloService from '../../../../../services/modeloService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DModelo } from '../../../../../models/modelo';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function ModelList() {

    const navigate = useNavigate();

    const [dialogInfoData, setDialogInfoData] = useState({
        visible: false,
        message: "Sucesso!"
    });

    const [dialogConfirmationData, setDialogConfirmationData] = useState({
        visible: false,
        id: 0,
        message: "Você tem certeza?"
    });

    const [isLastPage, setIsLastPage] = useState(false);

    const [modelos, setModelos] = useState<DModelo[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        modeloService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setModelos(modelos.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/models/create");
    }

    function handleSearch(searchText: string) {
        setModelos([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(modelId: number) {
        navigate(`/models/${modelId}`);
    }

    function handleDeleteClick(modelId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: modelId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, modelId: number[]) {
        if (answer) {
            modeloService.remover(modelId)
                .then(() => {
                    setModelos([]);
                    setQueryParam({ ...queryParams, page: 0 });
                })
                .catch(error => {
                    setDialogInfoData({
                        visible: true,
                        message: error.response.data.error
                    })
                });
        }

        setDialogConfirmationData({ ...dialogConfirmationData, visible: false });
    }

    return(
        <main>
            <section id="listing-section" className="container">
                <h2 className="section-title mb20">Cadastro de Modelos</h2>

                <div className="btn-page-container mb20">
                    <div onClick={handleNewChallengeClick}>
                        <ButtonInverse text="Novo" />
                    </div>
                </div>

                <SearchBar onSearch={handleSearch} />

                <table className="table mb20 mt20">
                    <thead>
                        <tr>
                            <th className="tb576">Código</th>
                            <th className="txt-left">Descrição</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            modelos.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(modelo => (
                                <tr key={modelo.codigo} className={`situacao-${modelo.situacao.toLowerCase()}`}>
                                    <td className="tb576">{modelo.codigo}</td>
                                    <td className="txt-left">{modelo.descricao}</td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(modelo.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(modelo.codigo)}
                                        />
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