import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as categoriaComponenteService from '../../../../../services/categoriaComponenteService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import DropdownMenu from '../../../../../components/DropdownMenu';
import { DCategoriaComponente } from '../../../../../models/categoriaComponente';
import { hasAnyRoles } from '../../../../../services/authService';

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

    const [categoriaComponentes, setCategoriaComponentes] = useState<DCategoriaComponente[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        categoriaComponenteService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setCategoriaComponentes(categoriaComponentes.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/componentcategories/create");
    }

    function handleSearch(searchText: string) {
        setCategoriaComponentes([]);
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
                    setCategoriaComponentes([]);
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
        categoriaComponenteService.inativar(id)
        .then(() => {
            setCategoriaComponentes([]);
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
                        </tr>
                    </thead>
                    <tbody>
                        {
                            categoriaComponentes.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(categoriaComponente => (
                                <tr key={categoriaComponente.codigo} className={`situacao-${categoriaComponente.situacao.toLowerCase()}`}>
                                    <td className="tb576">{categoriaComponente.codigo}</td>
                                    <td className="txt-left">{categoriaComponente.descricao}</td>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) &&
                                        <td>
                                            <DropdownMenu
                                                onEdit={() => handleUpdateClick(categoriaComponente.codigo)}
                                                onInactivate={() => handleInactivate([categoriaComponente.codigo])}
                                                onDelete={() => handleDeleteClick(categoriaComponente.codigo)}
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