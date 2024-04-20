import ModuleCard from "Components/ModuleCard";
import { NavLink } from "react-router-dom";
import mdpImage from 'assets/images/mdp.jpg';
import mdfImage from 'assets/images/mdf.jpg';
import aluminiumImage from 'assets/images/aluminium.jpg';

const MainComponents = () => {
    return(
        <div className='home-container'>
            <div className="row">
                <div className="col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column">
                    <NavLink to="/home/mdp">
                        <ModuleCard title='MDP' imgUrl={mdpImage}/>
                    </NavLink>
                </div>
                <div className="col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column">
                    <NavLink to="/home/mdf">
                        <ModuleCard title='MDF' imgUrl={mdfImage}/>
                    </NavLink>
                </div>
                <div className="col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column">
                    <NavLink to="/home/aluminium">
                        <ModuleCard title='Alumínios' imgUrl={aluminiumImage}/>
                    </NavLink>
                </div>
                <div className="col-sm-6 col-lg-6 col-xl-6 col-xxl-4 modules-column">
                    <NavLink to="/home/packaging">
                        <ModuleCard title='Embalagem' imgUrl={aluminiumImage}/>
                    </NavLink>
                </div>
            </div>
        </div>
    );
}

export default MainComponents;