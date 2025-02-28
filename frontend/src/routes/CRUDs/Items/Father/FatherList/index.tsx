import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import eyeIcon from '../../../../../assets/images/eye.svg';
import * as paiService from '../../../../../services/paiService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DPai } from '../../../../../models/pai';
import { Link } from 'react-router-dom';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function FatherList() {

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

    const [pais, setPais] = useState<DPai[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        paiService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setPais(pais.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/fathers/create");
    }

    function handleSearch(searchText: string) {
        setPais([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(fatherId: number) {
        navigate(`/fathers/${fatherId}`);
    }

    function handleDeleteClick(fatherId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: fatherId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, fatherId: number[]) {
        if (answer) {
            paiService.remover(fatherId)
                .then(() => {
                    setPais([]);
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
        paiService.inativar(id)
        .then(() => {
            setPais([]);
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
                <h2 className="section-title mb20">Cadastro de Pais</h2>

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
                            <th className="txt-left">Categoria</th>
                            <th className="txt-left">Modelo</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            pais.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(pai => (
                                <tr key={pai.codigo} className={`situacao-${pai.situacao.toLowerCase()}`}>
                                    <td className="tb576">{pai.codigo}</td>
                                    <td className="txt-left">{pai.descricao}</td>
                                    <td className="txt-left">{pai.categoriaComponente.descricao}</td>
                                    <td className="txt-left">{pai.modelo.descricao}</td>
                                    <td><Link to={`/fathers/details/${pai.codigo}`}><img className="visualize-btn" src={eyeIcon} alt="" /></Link></td>
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(pai.codigo)}
                                            onInactivate={() => handleInactivate([pai.codigo])}
                                            onDelete={() => handleDeleteClick(pai.codigo)}
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