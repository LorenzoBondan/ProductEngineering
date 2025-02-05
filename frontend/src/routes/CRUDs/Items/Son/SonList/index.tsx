import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import eyeIcon from '../../../../../assets/images/eye.svg';
import * as filhoService from '../../../../../services/filhoService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DFilho } from '../../../../../models/filho';
import { Link } from 'react-router-dom';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function SonList() {

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

    const [filhos, setFilhos] = useState<DFilho[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        filhoService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setFilhos(filhos.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/sons/create");
    }

    function handleSearch(searchText: string) {
        setFilhos([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(sonId: number) {
        navigate(`/sons/${sonId}`);
    }

    function handleDeleteClick(sonId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: sonId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, sonId: number[]) {
        if (answer) {
            filhoService.remover(sonId)
                .then(() => {
                    setFilhos([]);
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
                <h2 className="section-title mb20">Cadastro de Filhos</h2>

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
                            <th className="txt-left">Pai</th>
                            <th className="txt-left">Cor</th>
                            <th className="txt-left">Medidas</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            filhos.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(filho => (
                                <tr key={filho.codigo}>
                                    <td className="tb576">{filho.codigo}</td>
                                    <td className="txt-left">{filho.descricao}</td>
                                    <td className="txt-left">{filho.pai.descricao}</td>
                                    <td className="txt-left">{filho.cor.descricao}</td>
                                    <td className="txt-left">{filho.medidas.altura}X{filho.medidas.largura}X{filho.medidas.espessura}</td>
                                    <td><Link to={`/sons/details/${filho.codigo}`}><img className="visualize-btn" src={eyeIcon} alt="" /></Link></td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(filho.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(filho.codigo)}
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