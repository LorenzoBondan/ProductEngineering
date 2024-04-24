import NameFilter, { NameFilterData } from "Components/Filters/NameFilter";
import { DMaterial } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import Pagination from "Components/Pagination";
import { SpringPage } from "types";
import * as MaterialService from 'services/public/materialService';
import MaterialRow from "../MaterialRow";
import MaterialModal from "../MaterialModal";

type ControlComponentsData = {
    activePage: number;
    filterData: NameFilterData;
}

const List = () => {

    // filter 

    const [controlComponentsData, setControlComponentsData] = useState<ControlComponentsData>({activePage:0, filterData: { name: '', status: undefined }});

    const handlePageChange = (pageNumber : number) => {
        setControlComponentsData({activePage: pageNumber, filterData: controlComponentsData.filterData});
    }

    const handleSubmitFilter = (data : NameFilterData) => {
        setControlComponentsData({activePage: 0, filterData: data});
    }

    // findAll

    const [page, setPage] = useState<SpringPage<DMaterial>>();

    const getMaterials = useCallback(() => {
        MaterialService.findAll(controlComponentsData.filterData.name, controlComponentsData.activePage, 10, controlComponentsData.filterData.status)
            .then(response => {
                setPage(response.data);
                window.scrollTo(0, 0);
            });
    }, [controlComponentsData])

    useEffect(() => {
        getMaterials();
    }, [getMaterials]);

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
                        Adicionar novo Material
                    </button>
                    <MaterialModal isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getMaterials()} />
                </div>
                <div className='search-bar-container'>
                    <NameFilter onSubmitFilter={handleSubmitFilter} />
                </div>
                <div className='crud-table-container'>
                    <table className='crud-table'>
                        <thead>
                            <tr>
                                <th>Código</th>
                                <th>Nome</th>
                                <th>Editar</th>
                                <th>Inativar</th>
                                <th>Excluir</th>
                            </tr>
                        </thead>
                        <tbody>
                            {page?.content.map((item) => (
                                <MaterialRow Material={item} onDeleteOrEdit={getMaterials} key={item.id}/>
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