import ModuleCard from "Components/ModuleCard";
import { NavLink, useLocation } from "react-router-dom";
import mdpImage from 'assets/images/mdp.jpg';
import mdfImage from 'assets/images/mdf.jpg';
import aluminiumImage from 'assets/images/aluminium.jpg';
import packagingImage from 'assets/images/packaging.jpg';
import machinesImage from 'assets/images/machines.jpg';
import colorsImage from 'assets/images/colors.jpg';

const MainComponents = () => {
    const location = useLocation();
    
    return (
        <div className='home-container'>
            <div className="row">
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/homebasematerials/mdp">
                        <div className={`${location.pathname === "/homebasematerials/mdp" ? "active-card" : ""}`}>
                            <ModuleCard title='MDP' imgUrl={mdpImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/homebasematerials/mdf">
                        <div className={`${location.pathname === "/homebasematerials/mdf" ? "active-card" : ""}`}>
                            <ModuleCard title='MDF' imgUrl={mdfImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <NavLink to="/homebasematerials/aluminium">
                        <div className={`${location.pathname === "/homebasematerials/aluminium" ? "active-card" : ""}`}>
                            <ModuleCard title='Alumínios' imgUrl={aluminiumImage}/>
                        </div>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <div className={`${location.pathname === "/homebasematerials/packaging" ? "active-card" : ""}`}>
                        <NavLink to="/homebasematerials/packaging">
                            <ModuleCard title='Embalagem' imgUrl={packagingImage}/>
                        </NavLink>
                    </div>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <div className={`${location.pathname === "/homebasematerials/machines" ? "active-card" : ""}`}>
                        <NavLink to="/homebasematerials/machines">
                            <ModuleCard title='Máquinas' imgUrl={machinesImage}/>
                        </NavLink>
                    </div>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column'>
                    <div className={`${location.pathname === "/homebasematerials/public" ? "active-card" : ""}`}>
                        <NavLink to="/homebasematerials/public">
                            <ModuleCard title='Cores e Materiais' imgUrl={colorsImage}/>
                        </NavLink>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MainComponents;