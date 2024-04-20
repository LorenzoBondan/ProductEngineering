import { Link } from 'react-router-dom';
import ItemCard from 'Components/ItemCard';

const MDFPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/paintings">
                        <ItemCard title='Pinturas'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/paintingborderbackgrounds">
                        <ItemCard title='Pinturas de Borda de Fundo'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/polyesters">
                        <ItemCard title='Poliésters'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default MDFPage;