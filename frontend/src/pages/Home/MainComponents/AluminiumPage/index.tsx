import ItemCard from "Components/ItemCard";
import { Link } from "react-router-dom";

const AluminiumPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/drawerpulls">
                        <ItemCard title='Puxador'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/trysquares">
                        <ItemCard title='Esquadretas'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/screws">
                        <ItemCard title='Parafusos'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/moldings">
                        <ItemCard title='Perfis'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/glasses">
                        <ItemCard title='Vidros'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default AluminiumPage;

/*

const AluminiumPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/drawerpulls">
                        <ItemCard title='Puxador'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/trysquares">
                        <ItemCard title='Esquadretas'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/screws">
                        <ItemCard title='Parafusos'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/moldings">
                        <ItemCard title='Perfis'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/glasses">
                        <ItemCard title='Vidros'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default AluminiumPage;

*/