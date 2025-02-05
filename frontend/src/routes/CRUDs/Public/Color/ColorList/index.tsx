import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as corService from '../../../../../services/corService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DCor } from '../../../../../models/cor';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function ColorList() {

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

    const [cores, setCores] = useState<DCor[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        corService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setCores(cores.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/colors/create");
    }

    function handleSearch(searchText: string) {
        setCores([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(colorId: number) {
        navigate(`/colors/${colorId}`);
    }

    function handleDeleteClick(colorId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: colorId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, colorId: number[]) {
        if (answer) {
            corService.remover(colorId)
                .then(() => {
                    setCores([]);
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
                <h2 className="section-title mb20">Cadastro de Cores</h2>

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
                            <th className="txt-left">Hexa</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            cores.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(cor => (
                                <tr key={cor.codigo} className={`situacao-${cor.situacao.toLowerCase()}`}>
                                    <td className="tb576">{cor.codigo}</td>
                                    <td className="txt-left">{cor.descricao}</td>
                                    <td className="txt-left">
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                                            {cor.hexa && (
                                                <span 
                                                    style={{
                                                        width: '16px',
                                                        height: '16px',
                                                        borderRadius: '50%',
                                                        backgroundColor: `#${cor.hexa}`,
                                                        border: '1px solid #ccc'
                                                    }}
                                                />
                                            )}
                                        </div>
                                    </td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(cor.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(cor.codigo)}
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