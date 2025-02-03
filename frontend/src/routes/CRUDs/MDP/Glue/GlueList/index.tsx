import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import editIcon from '../../../../../assets/images/edit.svg';
import deleteIcon from '../../../../../assets/images/delete.svg';
import * as chapaService from '../../../../../services/chapaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DCola } from '../../../../../models/cola';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function GlueList() {

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

    const [colas, setColas] = useState<DCola[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        chapaService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setColas(colas.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/glues/create");
    }

    function handleSearch(searchText: string) {
        setColas([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(glueId: number) {
        navigate(`/glues/${glueId}`);
    }

    function handleDeleteClick(glueId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: glueId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, glueId: number[]) {
        if (answer) {
            chapaService.remover(glueId)
                .then(() => {
                    setColas([]);
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
                <h2 className="section-title mb20">Cadastro de Colas</h2>

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
                            <th className="txt-left">Gramatura</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            colas.map(cola => (
                                <tr key={cola.codigo}>
                                    <td className="tb576">{cola.codigo}</td>
                                    <td className="txt-left">{cola.descricao}</td>
                                    <td className="txt-left">{cola.gramatura}</td>
                                    <td><img onClick={() => handleUpdateClick(cola.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={() => handleDeleteClick(cola.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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