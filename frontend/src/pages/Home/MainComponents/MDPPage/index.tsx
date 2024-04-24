import { Link } from 'react-router-dom';
import ItemCard from 'Components/ItemCard';
import './styles.css';

const MDPPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/sheets">
                        <ItemCard title='Chapas'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/edgebandings">
                        <ItemCard title='Fitas borda'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/glues">
                        <ItemCard title='Colas'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default MDPPage;