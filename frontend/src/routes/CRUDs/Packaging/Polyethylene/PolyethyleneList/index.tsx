import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as polietilenoService from '../../../../../services/polietilenoService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DPolietileno } from '../../../../../models/polietileno';
import DropdownMenu from '../../../../../components/DropdownMenu';
import { hasAnyRoles } from '../../../../../services/authService';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function PolyethyleneList() {

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

    const [polietilenos, setPolietilenos] = useState<DPolietileno[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        polietilenoService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setPolietilenos(polietilenos.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/polyethylenes/create");
    }

    function handleSearch(searchText: string) {
        setPolietilenos([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(polyethyleneId: number) {
        navigate(`/polyethylenes/${polyethyleneId}`);
    }

    function handleDeleteClick(polyethyleneId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: polyethyleneId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, polyethyleneId: number[]) {
        if (answer) {
            polietilenoService.remover(polyethyleneId)
                .then(() => {
                    setPolietilenos([]);
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
        polietilenoService.inativar(id)
        .then(() => {
            setPolietilenos([]);
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
                <h2 className="section-title mb20">Cadastro de Polietilenos</h2>

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
                            polietilenos.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(polietileno => (
                                <tr key={polietileno.codigo} className={`situacao-${polietileno.situacao.toLowerCase()}`}>
                                    <td className="tb576">{polietileno.codigo}</td>
                                    <td className="txt-left">{polietileno.descricao}</td>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) &&
                                        <td>
                                            <DropdownMenu
                                                onEdit={() => handleUpdateClick(polietileno.codigo)}
                                                onInactivate={() => handleInactivate([polietileno.codigo])}
                                                onDelete={() => handleDeleteClick(polietileno.codigo)}
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