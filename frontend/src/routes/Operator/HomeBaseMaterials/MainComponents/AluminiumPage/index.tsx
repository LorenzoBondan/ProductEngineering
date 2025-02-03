import { Link } from "react-router-dom";
import ItemCard from "../../../../../components/ItemCard";

const AluminiumPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/accessories">
                        <ItemCard title='Acessórios'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/moldings">
                        <ItemCard title='Baguetes'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default AluminiumPage;