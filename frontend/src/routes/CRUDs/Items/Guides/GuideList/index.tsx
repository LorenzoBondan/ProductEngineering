import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import eyeIcon from '../../../../../assets/images/eye.svg';
import * as roteiroService from '../../../../../services/roteiroService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DRoteiro } from '../../../../../models/roteiro';
import { formatDate } from '../../../../../utils/formatters';
import { Link } from 'react-router-dom';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function GuideList() {

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

    const [roteiros, setRoteiros] = useState<DRoteiro[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        roteiroService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setRoteiros(roteiros.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/guides/create");
    }

    function handleSearch(searchText: string) {
        setRoteiros([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(guideId: number) {
        navigate(`/guides/${guideId}`);
    }

    function handleDeleteClick(guideId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: guideId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, guideId: number[]) {
        if (answer) {
            roteiroService.remover(guideId)
                .then(() => {
                    setRoteiros([]);
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
                <h2 className="section-title mb20">Cadastro de Roteiro</h2>

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
                            <th className="txt-left">Implantação</th>
                            <th className="txt-left">Data Final</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            roteiros.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(roteiro => (
                                <tr key={roteiro.codigo}>
                                    <td className="tb576">{roteiro.codigo}</td>
                                    <td className="txt-left">{roteiro.descricao}</td>
                                    <td className="txt-left">{formatDate(roteiro.implantacao.toString())}</td>
                                    <td className="txt-left">{formatDate(roteiro.dataFinal.toString())}</td>
                                    <td><Link to={`/guides/details/${roteiro.codigo}`}><img className="visualize-btn" src={eyeIcon} alt="" /></Link></td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(roteiro.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(roteiro.codigo)}
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