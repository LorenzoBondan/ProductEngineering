import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DChapa } from '../../../../../models/chapa';
import * as chapaService from '../../../../../services/chapaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import DropdownMenu from '../../../../../components/DropdownMenu';
import { hasAnyRoles } from '../../../../../services/authService';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function SheetList() {

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

    const [chapas, setChapas] = useState<DChapa[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        chapaService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setChapas(chapas.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/sheets/create");
    }

    function handleSearch(searchText: string) {
        setChapas([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(sheetId: number) {
        navigate(`/sheets/${sheetId}`);
    }

    function handleDeleteClick(sheetId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: sheetId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, sheetId: number[]) {
        if (answer) {
            chapaService.remover(sheetId)
                .then(() => {
                    setChapas([]);
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

    function handleInactivate(id: number[]) {
        chapaService.inativar(id)
        .then(() => {
            setChapas([]);
            setQueryParam({ ...queryParams, page: 0 });
        })
        .catch(error => {
            setDialogInfoData({
                visible: true,
                message: error.response.data.error
            });
        });
    }

    return(
        <main>
            <section id="listing-section" className="container">
                <h2 className="section-title mb20">Cadastro de Chapas</h2>

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
                            <th className="txt-left">Cor</th>
                            <th className="txt-left">Espessura</th>
                            <th className="txt-left">Faces</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            chapas.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(chapa => (
                                <tr key={chapa.codigo} className={`situacao-${chapa.situacao.toLowerCase()}`}>
                                    <td className="tb576">{chapa.codigo}</td>
                                    <td className="txt-left">{chapa.descricao}</td>
                                    {chapa.cor ? <td className="txt-left">{chapa.cor.descricao}</td> : <td className="txt-left"></td>}
                                    <td className="txt-left">{chapa.espessura}</td>
                                    <td className="txt-left">{chapa.faces}</td>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) &&
                                        <td>
                                            <DropdownMenu
                                                onEdit={() => handleUpdateClick(chapa.codigo)}
                                                onInactivate={() => handleInactivate([chapa.codigo])}
                                                onDelete={() => handleDeleteClick(chapa.codigo)}
                                            />
                                        </td>
                                    }
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