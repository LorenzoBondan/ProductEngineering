import { Link } from 'react-router-dom';
import './styles.css';

const HomeItems = () => {
    return(
        <div className='home-items-page-container'>
            <ul>
                <Link to="/fathers">
                    <li>
                        <span>Pais</span>
                    </li>
                </Link>
                <Link to="/mdpsons">
                    <li>
                        <span>Filhos MDP</span>
                    </li>
                </Link>
                <Link to="/mdfsons">
                    <li>
                        <span>Filhos MDF</span>
                    </li>
                </Link>
                <Link to="/aluminiumsons">
                    <li>
                        <span>Filhos Alumínio</span>
                    </li>
                </Link>
                <Link to="/backs">
                    <li>
                        <span>Fundos</span>
                    </li>
                </Link>
                <Link to="/ghosts">
                    <li>
                        <span>Fantasmas</span>
                    </li>
                </Link>
                <Link to="/attachments">
                    <li>
                        <span>Acessórios</span>
                    </li>
                </Link>
                <Link to="/guides">
                    <li>
                        <span>Roteiros</span>
                    </li>
                </Link>
            </ul>
        </div>
    );
}

export default HomeItems;