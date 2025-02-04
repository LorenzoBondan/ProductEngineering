import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import editIcon from '../../../../../assets/images/edit.svg';
import deleteIcon from '../../../../../assets/images/delete.svg';
import * as categoriaComponenteService from '../../../../../services/categoriaComponenteService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DModelo } from '../../../../../models/modelo';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function ComponentCategoryList() {

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
        categoriaComponenteService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setModelos(modelos.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/componentcategories/create");
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

    function handleUpdateClick(componentCategoryId: number) {
        navigate(`/componentcategories/${componentCategoryId}`);
    }

    function handleDeleteClick(componentCategoryId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: componentCategoryId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, componentCategoryId: number[]) {
        if (answer) {
            categoriaComponenteService.remover(componentCategoryId)
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
                <h2 className="section-title mb20">Cadastro de Categorias de Componentes</h2>

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
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            modelos.map(modelo => (
                                <tr key={modelo.codigo}>
                                    <td className="tb576">{modelo.codigo}</td>
                                    <td className="txt-left">{modelo.descricao}</td>
                                    <td><img onClick={() => handleUpdateClick(modelo.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={() => handleDeleteClick(modelo.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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