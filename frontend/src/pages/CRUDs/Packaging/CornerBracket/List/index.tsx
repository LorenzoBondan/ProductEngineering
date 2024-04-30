import DescriptionFilter, { DescriptionFilterData } from "Components/Filters/DescriptionFilter";
import { DCornerBracket, SpringPage } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import Pagination from "Components/Pagination";
import * as CornerBracketService from 'services/Packaging/cornerBracketService';
import CornerBracketRow from "../CornerBracketRow";
import CornerBracketModal from "../CornerBracketModal";

type ControlComponentsData = {
    activePage: number;
    filterData: DescriptionFilterData;
}

const List = () => {

    // filter 

    const [controlComponentsData, setControlComponentsData] = useState<ControlComponentsData>({activePage:0, filterData: { description: '', status: undefined }});

    const handlePageChange = (pageNumber : number) => {
        setControlComponentsData({activePage: pageNumber, filterData: controlComponentsData.filterData});
    }

    const handleSubmitFilter = (data : DescriptionFilterData) => {
        setControlComponentsData({activePage: 0, filterData: data});
    }

    // findAll

    const [page, setPage] = useState<SpringPage<DCornerBracket>>();

    const getCornerBrackets = useCallback(() => {
        CornerBracketService.findAll(controlComponentsData.filterData.description, controlComponentsData.activePage, 10, controlComponentsData.filterData.status)
            .then(response => {
                setPage(response.data);
                window.scrollTo(0, 0);
            });
    }, [controlComponentsData])

    useEffect(() => {
        getCornerBrackets();
    }, [getCornerBrackets]);

    // modal functions

    const [modalIsOpen, setIsOpen] = useState(false);

    const openModal = () => {
        setIsOpen(true);
    }
    
    const closeModal = () => {
        setIsOpen(false);
    }

    return(
        <div className='crud-container'>
            <div className="crud-content-container">
                <div className="crud-bar-container">
                    <button className="btn btn-primary btn-crud-add" style={{color:"white", marginBottom:"20px"}} onClick={openModal}>
                        Adicionar nova Cantoneira
                    </button>
                    <CornerBracketModal isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getCornerBrackets()} />
                </div>
                <div className='search-bar-container'>
                    <DescriptionFilter onSubmitFilter={handleSubmitFilter} />
                </div>
                <div className='crud-table-container'>
                    <table className='crud-table'>
                        <thead>
                            <tr>
                                <th>Código</th>
                                <th>Descrição</th>
                                <th>Família</th>
                                <th>Implementação</th>
                                <th>% Perda</th>
                                <th>Editar</th>
                                <th>Inativar</th>
                                <th>Excluir</th>
                            </tr>
                        </thead>
                        <tbody>
                            {page?.content.map((item) => (
                                <CornerBracketRow CornerBracket={item} onDeleteOrEdit={getCornerBrackets} key={item.code}/>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <div className='pagination-container'>
                <Pagination 
                    pageCount={(page) ? page.totalPages : 0} 
                    range={2}
                    onChange={handlePageChange}
                    forcePage={page?.number}
                />
            </div>
        </div>
    );
}

export default List;