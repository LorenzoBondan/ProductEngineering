import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import editIcon from '../../../../../assets/images/edit.svg';
import deleteIcon from '../../../../../assets/images/delete.svg';
import * as maquinaService from '../../../../../services/maquinaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DMaquina } from '../../../../../models/maquina';

type QueryParams = {
    page: number;
    nome: string;
}

export default function MachineList() {

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

    const [maquinas, setMaquinas] = useState<DMaquina[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        nome: ""
    });

    useEffect(() => {
        maquinaService.pesquisarTodos('nome', '=', queryParams.nome, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setMaquinas(maquinas.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/machines/create");
    }

    function handleSearch(searchText: string) {
        setMaquinas([]);
        setQueryParam({ ...queryParams, page: 0, nome: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(machineId: number) {
        navigate(`/machines/${machineId}`);
    }

    function handleDeleteClick(machineId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: machineId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, machineId: number[]) {
        if (answer) {
            maquinaService.remover(machineId)
                .then(() => {
                    setMaquinas([]);
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
                <h2 className="section-title mb20">Cadastro de Máquina</h2>

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
                            <th className="txt-left">Grupo Máquina</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            maquinas.map(maquina => (
                                <tr key={maquina.codigo}>
                                    <td className="tb576">{maquina.codigo}</td>
                                    <td className="txt-left">{maquina.nome}</td>
                                    <td className="txt-left">{maquina.grupoMaquina.nome}</td>
                                    <td><img onClick={() => handleUpdateClick(maquina.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={() => handleDeleteClick(maquina.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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