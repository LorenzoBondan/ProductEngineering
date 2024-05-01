import { Link } from 'react-router-dom';
import {ReactComponent as Seta} from 'assets/images/Seta.svg';
import './styles.css';

const Operations = () => {
    return(
        <div className='operations-container'>
            <div className='operations-struct-container operation-section'>
                <div className='operation-section-text'>
                    <p>Gere automaticamente cadastros, estruturas e roteiros dos itens de MDP, MDF, Modulação, Alumínios e Embalagem</p>
                </div>
                <div className='operation-section-button-container'>
                    <Link to={"/homestructs"}>
                        <button className='btn btn-primary text-white'>ENTRAR<Seta/></button>
                    </Link>
                </div>
            </div>
            <div className='operations-items-container operation-section operation-section-middle'>
                <div className='operation-section-text operation-section-text-middle'>
                    <p>Crie e altere manualmente itens e materiais utilizados nas estruturas de MDP, MDF, Modulação, Alumínios e Embalagem</p>
                </div>
                <div className='operation-section-button-container'>
                    <Link to={"/mdpStruct"}>
                        <button className='btn btn-primary text-white'>ENTRAR<Seta/></button>
                    </Link>
                </div>
            </div>
            <div className='operations-base-materials-container operation-section'>
                <div className='operation-section-text'>
                    <p>Busque e altere os materiais base de MDP, MDF, Modulação, Alumínios e Embalagem, além de Máquinas e Cores</p>
                </div>
                <div className='operation-section-button-container'>
                    <Link to={"/homebasematerials"}>
                        <button className='btn btn-primary text-white'>ENTRAR<Seta/></button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Operations;