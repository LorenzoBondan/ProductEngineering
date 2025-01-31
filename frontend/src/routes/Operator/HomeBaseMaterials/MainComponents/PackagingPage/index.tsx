import { Link } from "react-router-dom";
import ItemCard from "../../../../../components/ItemCard";

const PackagingPage = () => {
    return(
        <div className='base-page'>
            <div className='base-container'>
                <div className="base-container-item">
                    <Link to="/cornerBrackets">
                        <ItemCard title='Cantoneiras'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/nonwovenFabrics">
                        <ItemCard title='TNTs'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/plastics">
                        <ItemCard title='Plásticos'/>
                    </Link>
                </div>
                <div className="base-container-item">
                    <Link to="/polyethylenes">
                        <ItemCard title='Polietilenos'/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default PackagingPage;