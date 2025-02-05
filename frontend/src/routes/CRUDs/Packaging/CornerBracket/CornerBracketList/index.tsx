import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as cantoneiraService from '../../../../../services/cantoneiraService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DCantoneira } from '../../../../../models/cantoneira';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function CornerBracketList() {

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

    const [cantoneiras, setCantoneiras] = useState<DCantoneira[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        cantoneiraService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setCantoneiras(cantoneiras.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/cornerbrackets/create");
    }

    function handleSearch(searchText: string) {
        setCantoneiras([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(cornerBracketId: number) {
        navigate(`/cornerbrackets/${cornerBracketId}`);
    }

    function handleDeleteClick(cornerBracketId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: cornerBracketId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, cornerBracketId: number[]) {
        if (answer) {
            cantoneiraService.remover(cornerBracketId)
                .then(() => {
                    setCantoneiras([]);
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
                <h2 className="section-title mb20">Cadastro de Cantoneiras</h2>

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
                            cantoneiras.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(cantoneira => (
                                <tr key={cantoneira.codigo} className={`situacao-${cantoneira.situacao.toLowerCase()}`}>
                                    <td className="tb576">{cantoneira.codigo}</td>
                                    <td className="txt-left">{cantoneira.descricao}</td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(cantoneira.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(cantoneira.codigo)}
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