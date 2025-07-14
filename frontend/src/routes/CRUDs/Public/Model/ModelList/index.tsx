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
import { hasAnyRoles } from '../../../../../services/authService';

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

    const [dialogConfirmationData, setDialogConfirmationData] = useState<{
        visible: boolean;
        id: number | number[];
        message: string;
    }>({
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

    const [selectedIds, setSelectedIds] = useState<number[]>([]);

    useEffect(() => {
        modeloService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setModelos(modelos.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleSelect(id: number, checked: boolean) {
        if (checked) {
            setSelectedIds(prev => [...prev, id]);
        } else {
            setSelectedIds(prev => prev.filter(selectedId => selectedId !== id));
        }
    }

    function handleSelectAll(checked: boolean) {
        if (checked) {
            const allIds = modelos
                .filter(m => m.situacao !== 'LIXEIRA')
                .map(m => m.codigo);
            setSelectedIds(allIds);
        } else {
            setSelectedIds([]);
        }
    }

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

    function handleDeleteClick(modelId: number | number[]) {
        const ids = Array.isArray(modelId) ? modelId : [modelId];
        const message = `Você tem certeza que deseja excluir ${ids.length} registro${ids.length > 1 ? 's' : ''}?`;

        setDialogConfirmationData({
            visible: true,
            id: ids,
            message
        });
    }

    function handleDialogConfirmationAnswer(answer: boolean, modelId: number | number[]) {
        if (answer) {
            const ids = Array.isArray(modelId) ? modelId : [modelId];

            modeloService.remover(ids)
                .then(() => {
                    setModelos([]);
                    setSelectedIds([]);
                    setQueryParam({ ...queryParams, page: 0 });
                })
                .catch(error => {
                    setDialogInfoData({
                        visible: true,
                        message: error.response?.data?.error || 'Erro ao excluir'
                    })
                });
        }

        setDialogConfirmationData({ ...dialogConfirmationData, visible: false });
    }

    function handleInactivate(ids: number[]) {
        modeloService.inativar(ids)
            .then(() => {
                setModelos([]);
                setSelectedIds([]);
                setQueryParam({ ...queryParams, page: 0 });
            })
            .catch(error => {
                setDialogInfoData({
                    visible: true,
                    message: error.response?.data?.error || 'Erro ao inativar'
                });
            });
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

                {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) && selectedIds.length > 0 && (
                    <div className="mb10 mt20" style={{ display: 'flex', gap: '10px' }}>
                        <ButtonInverse
                            text={`Inativar selecionados (${selectedIds.length})`}
                            onClick={() => handleInactivate(selectedIds)}
                        />
                        <ButtonInverse
                            text={`Excluir selecionados (${selectedIds.length})`}
                            onClick={() =>
                                setDialogConfirmationData({
                                    visible: true,
                                    id: selectedIds,
                                    message: `Você tem certeza que deseja excluir ${selectedIds.length} registro${selectedIds.length > 1 ? 's' : ''}?`
                                })
                            }
                        />
                    </div>
                )}

                <table className="table mb20 mt20">
                    <thead>
                        <tr>
                            {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) && (
                                <th style={{ width: '40px', textAlign: 'center' }}>
                                    <input
                                        type="checkbox"
                                        onChange={(e) => handleSelectAll(e.target.checked)}
                                        checked={selectedIds.length === modelos.filter(m => m.situacao !== 'LIXEIRA').length}
                                    />
                                </th>
                            )}
                            <th className="tb576">Código</th>
                            <th className="txt-left">Descrição</th>
                            <th></th>
                        </tr>
                    </thead>
                        <tbody>
                            {modelos.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(modelo => (
                                <tr key={modelo.codigo} className={`situacao-${modelo.situacao.toLowerCase()}`}>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) && (
                                        <td style={{ textAlign: 'center' }}>
                                            <input
                                                type="checkbox"
                                                checked={selectedIds.includes(modelo.codigo)}
                                                onChange={(e) => handleSelect(modelo.codigo, e.target.checked)}
                                            />
                                        </td>
                                    )}
                                    <td className="tb576">{modelo.codigo}</td>
                                    <td className="txt-left">{modelo.descricao}</td>
                                    {hasAnyRoles(['ROLE_ADMIN', 'ROLE_ANALYST']) &&
                                        <td>
                                            <DropdownMenu
                                                onEdit={() => handleUpdateClick(modelo.codigo)}
                                                onInactivate={() => handleInactivate([modelo.codigo])}
                                                onDelete={() => handleDeleteClick(modelo.codigo)}
                                            />
                                        </td>
                                    }
                                </tr>
                            ))}
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