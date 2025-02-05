import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import editIcon from '../../../../../assets/images/edit.svg';
import deleteIcon from '../../../../../assets/images/delete.svg';
import * as fitaBordaService from '../../../../../services/fitaBordaService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DFitaBorda } from '../../../../../models/fitaBorda';

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

    function handleUpdateClick(sheetId: number) {
        navigate(`/edgebandings/${sheetId}`);
    }

    function handleDeleteClick(sheetId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: sheetId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, sheetId: number[]) {
        if (answer) {
            fitaBordaService.remover(sheetId)
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
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            fitasBorda.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(fitaBorda => (
                                <tr key={fitaBorda.codigo}>
                                    <td className="tb576">{fitaBorda.codigo}</td>
                                    <td className="txt-left">{fitaBorda.descricao}</td>
                                    {fitaBorda.cor ? <td className="txt-left">{fitaBorda.cor.descricao}</td> : <td className="txt-left"></td>}
                                    <td className="txt-left">{fitaBorda.espessura}</td>
                                    <td className="txt-left">{fitaBorda.altura}</td>
                                    <td><img onClick={() => handleUpdateClick(fitaBorda.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={() => handleDeleteClick(fitaBorda.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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