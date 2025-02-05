import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as grupoMaquinaService from '../../../../../services/grupoMaquinaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DGrupoMaquina } from '../../../../../models/grupoMaquina';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    nome: string;
}

export default function MachineGroupList() {

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

    const [gruposMaquinas, setGruposMaquinas] = useState<DGrupoMaquina[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        nome: ""
    });

    useEffect(() => {
        grupoMaquinaService.pesquisarTodos('nome', '=', queryParams.nome, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setGruposMaquinas(gruposMaquinas.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/machinegroups/create");
    }

    function handleSearch(searchText: string) {
        setGruposMaquinas([]);
        setQueryParam({ ...queryParams, page: 0, nome: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(machineGroupId: number) {
        navigate(`/machinegroups/${machineGroupId}`);
    }

    function handleDeleteClick(machineGroupId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: machineGroupId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, machineGroupId: number[]) {
        if (answer) {
            grupoMaquinaService.remover(machineGroupId)
                .then(() => {
                    setGruposMaquinas([]);
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
                <h2 className="section-title mb20">Cadastro de Grupos de Máquinas</h2>

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
                            <th className="txt-left">Nome</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            gruposMaquinas.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(grupoMaquina => (
                                <tr key={grupoMaquina.codigo} className={`situacao-${grupoMaquina.situacao.toLowerCase()}`}>
                                    <td className="tb576">{grupoMaquina.codigo}</td>
                                    <td className="txt-left">{grupoMaquina.nome}</td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(grupoMaquina.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(grupoMaquina.codigo)}
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