import { NavLink } from 'react-router-dom';
import './styles.css';
import mdpImage from '../../../assets/images/mdp.jpg';
import mdfImage from '../../../assets/images/mdf.jpg';
import aluminiumImage from '../../../assets/images/aluminium.jpg';
import modulationImage from '../../../assets/images/modulation.jpg';
import ModuleCard from '../../../components/ModuleCard';

const HomeStruct = () => {

    return(
        <div className='home-struct-container'>
            <h1 className='home-title'>Estruturas</h1>
            <div className="components-catalog-grid mt20 mb20 container">
                <NavLink to="/mdpstruct">
                    <ModuleCard title='MDP' imgUrl={mdpImage}/>
                </NavLink>
                <NavLink to="/mdfstruct">
                    <ModuleCard title='MDF' imgUrl={mdfImage}/>
                </NavLink>
                <NavLink to="/modulationstruct">
                    <ModuleCard title='Modulação' imgUrl={modulationImage}/>
                </NavLink>
                <NavLink to="/aluminiumstruct">
                    <ModuleCard title='Alumínios' imgUrl={aluminiumImage}/>
                </NavLink>
            </div>
        </div>
    );
}

export default HomeStruct;