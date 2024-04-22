import ModuleCard from "Components/ModuleCard";
import { NavLink, useLocation } from "react-router-dom";
import mdpImage from 'assets/images/mdp.jpg';
import mdfImage from 'assets/images/mdf.jpg';
import aluminiumImage from 'assets/images/aluminium.jpg';
import packagingImage from 'assets/images/packaging.jpg';

const MainComponents = () => {
    const location = useLocation();
    
    return (
        <div className='home-container'>
            <div className="row">
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/home/mdp">
                        <div className={`${location.pathname === "/home/mdp" ? "active-card" : ""}`}>
                            <ModuleCard title='MDP' imgUrl={mdpImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/home/mdf">
                        <div className={`${location.pathname === "/home/mdf" ? "active-card" : ""}`}>
                            <ModuleCard title='MDF' imgUrl={mdfImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/home/aluminium">
                        <div className={`${location.pathname === "/home/aluminium" ? "active-card" : ""}`}>
                            <ModuleCard title='Alumínios' imgUrl={aluminiumImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <div className={`${location.pathname === "/home/packaging" ? "active-card" : ""}`}>
                        <NavLink to="/home/packaging">
                            <ModuleCard title='Embalagem' imgUrl={packagingImage}/>
                        </NavLink>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MainComponents;