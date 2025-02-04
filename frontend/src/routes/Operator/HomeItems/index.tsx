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
                <Link to="/sons">
                    <li>
                        <span>Filhos</span>
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