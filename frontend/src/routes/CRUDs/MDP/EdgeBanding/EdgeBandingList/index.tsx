import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as fitaBordaService from '../../../../../services/fitaBordaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DFitaBorda } from '../../../../../models/fitaBorda';
import DropdownMenu from '../../../../../components/DropdownMenu';
import { hasAnyRoles } from '../../../../../services/authService';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function EdgeBandingList() {

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

    const [fitasBorda, setFitasBorda] = useState<DFitaBorda[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        fitaBordaService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setFitasBorda(fitasBorda.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/edgebandings/create");
    }

    function handleSearch(searchText: string) {
        setFitasBorda([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(edgeBandingId: number) {
        navigate(`/edgebandings/${edgeBandingId}`);
    }

    function handleDeleteClick(edgeBandingId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: edgeBandingId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, edgeBandingId: number[]) {
        if (answer) {
            fitaBordaService.remover(edgeBandingId)
                .then(() => {
                    setFitasBorda([]);
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
        fitaBordaService.inativar(id)
        .then(() => {
            setFitasBorda([]);
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
                <h2 className="section-title mb20">Cadastro de Fita Borda</h2>

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
                            <th className="txt-left">Altura</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            fitasBorda.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(fitaBorda => (
                                <tr key={fitaBorda.codigo} className={`situacao-${fitaBorda.situacao.toLowerCase()}`}>
                                    <td className="tb576">{fitaBorda.codigo}</td>
                                    <td className="txt-left">{fitaBorda.descricao}</td>
                                    {fitaBorda.cor ? <td className="txt-left">{fitaBorda.cor.descricao}</td> : <td className="txt-left"></td>}
                                    <td className="txt-left">{fitaBorda.espessura}</td>
                                    <td className="txt-left">{fitaBorda.altura}</td>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) &&
                                        <td>
                                            <DropdownMenu
                                                onEdit={() => handleUpdateClick(fitaBorda.codigo)}
                                                onInactivate={() => handleInactivate([fitaBorda.codigo])}
                                                onDelete={() => handleDeleteClick(fitaBorda.codigo)}
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