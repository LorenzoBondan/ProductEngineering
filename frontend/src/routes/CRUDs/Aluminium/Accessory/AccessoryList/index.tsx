import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as acessorioService from '../../../../../services/acessorioService';
import ButtonInverse from '../../../../../components/ButtonInverse';
import SearchBar from '../../../../../components/SearchBar';
import ButtonNextPage from '../../../../../components/ButtonNextPage';
import DialogInfo from '../../../../../components/DialogInfo';
import DialogConfirmation from '../../../../../components/DialogConfirmation';
import { DAcessorio } from '../../../../../models/acessorio';
import DropdownMenu from '../../../../../components/DropdownMenu';

type QueryParams = {
    page: number;
    descricao: string;
}

export default function AccessoryList() {

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

    const [acessorios, setAcessorios] = useState<DAcessorio[]>([]);

    const [queryParams, setQueryParam] = useState<QueryParams>({
        page: 0,
        descricao: ""
    });

    useEffect(() => {
        acessorioService.pesquisarTodos('descricao', '=', queryParams.descricao, queryParams.page, 8, "codigo;a")
            .then(response => {
                const nextPage = response.data.content;
                setAcessorios(acessorios.concat(nextPage));
                setIsLastPage(response.data.last);
            });
    }, [queryParams]);

    function handleNewChallengeClick() {
        navigate("/accessories/create");
    }

    function handleSearch(searchText: string) {
        setAcessorios([]);
        setQueryParam({ ...queryParams, page: 0, descricao: searchText });
    }

    function handleNextPageClick() {
        setQueryParam({ ...queryParams, page: queryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogInfoData({ ...dialogInfoData, visible: false });
    }

    function handleUpdateClick(accessoryId: number) {
        navigate(`/accessories/${accessoryId}`);
    }

    function handleDeleteClick(accessoryId: number) {
        setDialogConfirmationData({ ...dialogConfirmationData, id: accessoryId, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean, accessoryId: number[]) {
        if (answer) {
            acessorioService.remover(accessoryId)
                .then(() => {
                    setAcessorios([]);
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
                <h2 className="section-title mb20">Cadastro de Acessórios</h2>

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
                            <th className="txt-left">Altura</th>
                            <th className="txt-left">Largura</th>
                            <th className="txt-left">Espessura</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            acessorios.filter(obj => obj.situacao !== 'LIXEIRA')
                            .map(acessorio => (
                                <tr key={acessorio.codigo} className={`situacao-${acessorio.situacao.toLowerCase()}`}>
                                    <td className="tb576">{acessorio.codigo}</td>
                                    <td className="txt-left">{acessorio.descricao}</td>
                                    {acessorio.cor ? <td className="txt-left">{acessorio.cor.descricao}</td> : <td className="txt-left"></td>}
                                    {acessorio.medidas ? <td className="txt-left">{acessorio.medidas.altura}</td> : <td className="txt-left"></td>}
                                    {acessorio.medidas ? <td className="txt-left">{acessorio.medidas.largura}</td> : <td className="txt-left"></td>}
                                    {acessorio.medidas ? <td className="txt-left">{acessorio.medidas.espessura}</td> : <td className="txt-left"></td>}
                                    <td>
                                        <DropdownMenu
                                            onEdit={() => handleUpdateClick(acessorio.codigo)}
                                            onInactivate={() => console.log()}
                                            onDelete={() => handleDeleteClick(acessorio.codigo)}
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