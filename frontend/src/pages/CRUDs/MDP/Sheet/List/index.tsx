import SheetFilter, { SheetFilterData } from "Components/Filters/SheetFilter";
import { AxiosRequestConfig } from "axios";
import { SheetDTO } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import Pagination from "Components/Pagination";
import { Link } from "react-router-dom";
import { SpringPage } from "types";
import { requestBackend } from "util/requests";
import SheetRow from "../SheetRow";

type ControlComponentsData = {
    activePage: number;
    filterData: SheetFilterData;
}

const List = () => {

    const [controlComponentsData, setControlComponentsData] = useState<ControlComponentsData>({activePage:0, filterData: { description: '' }});

    const handlePageChange = (pageNumber : number) => {
        setControlComponentsData({activePage: pageNumber, filterData: controlComponentsData.filterData});
    }

    const [page, setPage] = useState<SpringPage<SheetDTO>>();

    const getSheets = useCallback(() => {
        const params : AxiosRequestConfig = {
            method:"GET",
            url: "/sheets",
            params: {
            page: controlComponentsData.activePage,
            size: 12,
            description: controlComponentsData.filterData.description
            },
            withCredentials: true
        }
      
        requestBackend(params) 
            .then(response => {
            setPage(response.data);
            window.scrollTo(0, 0);
        })
    }, [controlComponentsData])

    useEffect(() => {
        getSheets();
    }, [getSheets]);

    const handleSubmitFilter = (data : SheetFilterData) => {
        setControlComponentsData({activePage: 0, filterData: data});
    }

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
                    <SheetFilter onSubmitFilter={handleSubmitFilter} />
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
                                    <SheetRow sheet={item} key={item.code}/>
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