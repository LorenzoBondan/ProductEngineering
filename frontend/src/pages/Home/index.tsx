import './styles.css';
import { Link } from 'react-router-dom';
import ModuleCard from './ModuleCard';
import mdpImage from 'assets/images/mdp.jpg';
import mdfImage from 'assets/images/mdf.jpg';
import modulationImage from 'assets/images/modulation.jpg';
import aluminiumImage from 'assets/images/aluminium.jpg';
import { SheetDTO, SpringPage } from 'models/entities';
import { useCallback, useEffect, useState } from 'react';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';

const Home = () => {

    const [page, setPage] = useState<SpringPage<SheetDTO>>();

    const getStadiums = useCallback(() => {
        const params : AxiosRequestConfig = {
          method:"GET",
          url: "/sheets?description=",
          withCredentials: true
        }
    
        requestBackend(params)
          .then(response => {
            setPage(response.data);
            window.scrollTo(0, 0);
          })
      }, [])
  
      useEffect(() => {
        getStadiums();
      }, [getStadiums]);


    return(
        <div className='home-page'>
            <div className='home-container'>
                <div className="row">
                    <div className="col-sm-6 col-lg-4 col-xl-3 modules-column">
                        <Link to="/mdp">
                            <ModuleCard title='MDP' imgUrl={mdpImage}/>
                        </Link>
                    </div>
                    <div className="col-sm-6 col-lg-4 col-xl-3 modules-column">
                        <Link to="/mdf">
                            <ModuleCard title='MDF' imgUrl={mdfImage}/>
                        </Link>
                    </div>
                    <div className="col-sm-6 col-lg-4 col-xl-3 modules-column">
                        <Link to="/modulation">
                            <ModuleCard title='Modulation' imgUrl={modulationImage}/>
                        </Link>
                    </div>
                    <div className="col-sm-6 col-lg-4 col-xl-3 modules-column">
                        <Link to="/aluminium">
                            <ModuleCard title='Aluminium' imgUrl={aluminiumImage}/>
                        </Link>
                    </div>
                </div>
                {page?.content
                  .sort((a,b) => a.description < b.description ? 1 : -1)
                  .map(stadium => (
                    <div className="col-sm-6 col-lg-6 col-xl-6 stadiums-column" key={stadium.code}>
                        <p>{stadium.description}</p>
                    </div>
                    )
                  )
                }
            </div>
        </div>
    );
}

export default Home;