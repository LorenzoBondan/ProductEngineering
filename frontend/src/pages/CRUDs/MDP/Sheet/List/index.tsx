import DescriptionFilter, { DescriptionFilterData } from "Components/Filters/DescriptionFilter";
import { DSheet } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import Pagination from "Components/Pagination";
import { Link } from "react-router-dom";
import { SpringPage } from "types";
import SheetRow from "../SheetRow";
import * as sheetService from 'services/MDP/sheetService';

type ControlComponentsData = {
    activePage: number;
    filterData: DescriptionFilterData;
}

const List = () => {

    const [controlComponentsData, setControlComponentsData] = useState<ControlComponentsData>({activePage:0, filterData: { description: '' }});

    const handlePageChange = (pageNumber : number) => {
        setControlComponentsData({activePage: pageNumber, filterData: controlComponentsData.filterData});
    }

    const handleSubmitFilter = (data : DescriptionFilterData) => {
        setControlComponentsData({activePage: 0, filterData: data});
    }

    const [page, setPage] = useState<SpringPage<DSheet>>();

    const getSheets = useCallback(() => {
        sheetService.findAll(controlComponentsData.filterData.description, controlComponentsData.activePage, 10)
            .then(response => {
                setPage(response.data);
                window.scrollTo(0, 0);
            });
    }, [controlComponentsData])

    useEffect(() => {
        getSheets();
    }, [getSheets]);

    return(
        <div className='crud-container'>
            <div className="crud-content-container">
                <div className="crud-bar-container">
                    <Link to="/sheets/create">
                        <button className="btn btn-primary btn-crud-add" style={{color:"white", marginBottom:"20px"}}>
                            Adicionar nova Chapa
                        </button>
                    </Link>
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
                                <th>Faces</th>
                                <th>Espessura</th>
                                <th>Implementação</th>
                                <th>% Perda</th>
                                <th>Material</th>
                                <th>Cor</th>
                                <th>Editar</th>
                                <th>Excluir</th>
                            </tr>
                        </thead>
                        <tbody>
                            {page?.content
                                .sort( (a,b) => a.description > b.description ? 1 : -1)
                                .map((item) => (
                                    <SheetRow sheet={item} onDeleteOrEdit={getSheets} key={item.code}/>
                                ))
                            }
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