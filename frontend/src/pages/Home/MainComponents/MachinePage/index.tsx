import { Link } from 'react-router-dom';
import ItemCard from 'Components/ItemCard';

const MachinePage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/machines">
                        <ItemCard title='Máquinas'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default MachinePage;