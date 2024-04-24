import ItemCard from "Components/ItemCard";
import { Link } from "react-router-dom";

const PublicPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/colors">
                        <ItemCard title='Cores'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/materials">
                        <ItemCard title='Materiais'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default PublicPage;