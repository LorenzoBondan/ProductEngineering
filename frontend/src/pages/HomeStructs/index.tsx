import { NavLink } from 'react-router-dom';
import './styles.css';
import ModuleCard from 'Components/ModuleCard';
import mdpImage from 'assets/images/mdp.jpg';
import mdfImage from 'assets/images/mdf.jpg';
import aluminiumImage from 'assets/images/aluminium.jpg';
import modulationImage from 'assets/images/modulation.jpg';

const HomeStruct = () => {

    return(
        <div className='home-struct-container'>
            <h1 className='home-title'>Estruturas</h1>
            <div className="row">
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-3 modules-column'>
                    <NavLink to="/mdpstruct">
                        <ModuleCard title='MDP' imgUrl={mdpImage}/>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-3 modules-column'>
                    <NavLink to="/mdfstruct">
                        <ModuleCard title='MDF' imgUrl={mdfImage}/>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-3 modules-column'>
                    <NavLink to="/modulationstruct">
                        <ModuleCard title='Modulação' imgUrl={modulationImage}/>
                    </NavLink>
                </div>
                <div className='col-sm-6 col-lg-6 col-xl-6 col-xxl-3 modules-column'>
                    <NavLink to="/aluminiumstruct">
                        <ModuleCard title='Alumínios' imgUrl={aluminiumImage}/>
                    </NavLink>
                </div>
            </div>
        </div>
    );
}

export default HomeStruct;