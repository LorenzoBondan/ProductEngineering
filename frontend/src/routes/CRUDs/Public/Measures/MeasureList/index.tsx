import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import editIcon from '../../../../../assets/images/edit.svg';
import deleteIcon from '../../../../../assets/images/delete.svg';
import * as medidasService from '../../../../../services/medidasService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DMedidas } from '../../../../../models/medidas';

type QueryParams = {
    page: number;
    altura: string;
}

export default function MeasureList() {

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

    const [medidas, setMedidas] = useState<DMedidas[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        altura: ""
    });

    useEffect(() => {
        medidasService.pesquisarTodos('altura', '=', queryParams.altura, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setMedidas(medidas.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/measures/create");
    }

    function handleSearch(searchText: string) {
        setMedidas([]);
        setQueryParam({ ...queryParams, page: 0, altura: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(measureId: number) {
        navigate(`/measures/${measureId}`);
    }

    function handleDeleteClick(measureId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: measureId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, measureId: number[]) {
        if (answer) {
            medidasService.remover(measureId)
                .then(() => {
                    setMedidas([]);
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
                <h2 className="section-title mb20">Cadastro de Medidas</h2>

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
                            <th className="txt-left">Altura</th>
                            <th className="txt-left">Largura</th>
                            <th className="txt-left">Espessura</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            medidas.map(medida => (
                                <tr key={medida.codigo}>
                                    <td className="tb576">{medida.codigo}</td>
                                    <td className="txt-left">{medida.altura}</td>
                                    <td className="txt-left">{medida.largura}</td>
                                    <td className="txt-left">{medida.espessura}</td>
                                    <td><img onClick={() => handleUpdateClick(medida.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={() => handleDeleteClick(medida.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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