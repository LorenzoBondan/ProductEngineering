import { NavLink } from 'react-router-dom';
import './styles.css';
import mdpImage from '../../../assets/images/mdp.jpg';
import modulationImage from '../../../assets/images/modulation.jpg';
import ModuleCard from '../../../components/ModuleCard';

const HomeStruct = () => {

    return(
        <div className='home-struct-container'>
            <h1 className='home-title'>Estruturas</h1>
            <div className="components-catalog-grid mt20 mb20 container">
                <NavLink to="/singlestruct">
                    <ModuleCard title='Estrutura MDP/MDF' imgUrl={mdpImage}/>
                </NavLink>
                <NavLink to="/modulationstruct">
                    <ModuleCard title='Estrutura Modulação/Alumínios' imgUrl={modulationImage}/>
                </NavLink>
            </div>
        </div>
    );
}

export default HomeStruct;