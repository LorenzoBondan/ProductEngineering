import DescriptionFilter, { DescriptionFilterData } from "Components/Filters/DescriptionFilter";
import { DGlass } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import Pagination from "Components/Pagination";
import { SpringPage } from "types";
import GlassRow from "../GlassRow";
import * as glassService from 'services/Aluminium/glassService';
import GlassModal from "../GlassModal";

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

    const [page, setPage] = useState<SpringPage<DGlass>>();

    const getGlasss = useCallback(() => {

        glassService.findAll(controlComponentsData.filterData.description, controlComponentsData.activePage, 10, controlComponentsData.filterData.status)
            .then(response => {
                setPage(response.data);
                window.scrollTo(0, 0);
            });
    }, [controlComponentsData])

    useEffect(() => {
        getGlasss();
    }, [getGlasss]);

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
                        Adicionar novo Vidro
                    </button>
                    <GlassModal isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getGlasss()} />
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
                                <th>Medida 1</th>
                                <th>Medida 2</th>
                                <th>Medida 3</th>
                                <th>Unidade de Medida</th>
                                <th>Implementação</th>
                                <th>Cor</th>
                                <th>Editar</th>
                                <th>Inativar</th>
                                <th>Excluir</th>
                            </tr>
                        </thead>
                        <tbody>
                            {page?.content.map((item) => (
                                <GlassRow glass={item} onDeleteOrEdit={getGlasss} key={item.code}/>
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