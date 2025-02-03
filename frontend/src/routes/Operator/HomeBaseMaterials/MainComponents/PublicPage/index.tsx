import { Link } from "react-router-dom";
import ItemCard from "../../../../../components/ItemCard";

const PublicPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/colors">
                        <ItemCard title='Cores'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default PublicPage;