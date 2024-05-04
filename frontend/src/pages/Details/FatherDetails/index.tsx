import { Link, useParams } from 'react-router-dom';
import './styles.css';
import { DFather } from 'models/entities';
import { useCallback, useEffect, useState } from 'react';
import * as fatherService from 'services/public/fatherService';
import { IoCalendarNumberOutline } from "react-icons/io5";
import { FaRegMoneyBillAlt } from "react-icons/fa";
import { convertDateTime } from 'helpers';

type UrlParams = {
    fatherId: string;
}

const FatherDetails = () => {

    const { fatherId } = useParams<UrlParams>();
    const [father, setFather] = useState<DFather>();

    const getFather = useCallback(() => {
        fatherService.findById(parseInt(fatherId))
            .then(response => setFather(response.data))
            .catch(error => console.error(error));
    }, [fatherId]);

    useEffect(() => {
        getFather();
    }, [getFather]);

    return(
        <div className='father-details-page'>
            <div className='father-details-container base-card'>
                <div className='father-title crud-modal-title'>
                    <h4>{father?.code} - {father?.description}</h4>
                </div>
                <div className='father-details'>
                    <h5><FaRegMoneyBillAlt/> <strong>Valor:</strong> R$ {father?.value}</h5>
                    <h5><IoCalendarNumberOutline/> <strong>Implementação:</strong> {father && convertDateTime(father?.implementation.toString())}</h5>
                </div>
                {father && father?.sons.length > 0 && 
                    <ul className='father-sons-list'>
                        {father?.sons.map(son => (
                            <li key={son.code} className='son-list-item'>
                                <Link to={`/sons/${son.code}`}>
                                    {son.code} - {son.description}
                                </Link>
                            </li>
                        ))}
                    </ul>
                }
                {father && father?.ghost && 
                    <ul className='father-sons-list'>
                        <Link to={`/ghosts/${father?.ghost.code}`}>
                            <li className='son-list-item'>
                                {father?.ghost.code} - {father?.ghost.description}
                            </li>
                        </Link>
                    </ul>
                }
                {father && father?.attachments.length > 0 && 
                    <ul className='father-sons-list'>
                        {father?.attachments.map(attachment => (
                            <Link to={`/attachments/${attachment.code}`}>
                                <li className='son-list-item'>
                                    {attachment.code} - {attachment.description}
                                </li>
                            </Link>
                        ))}
                    </ul>
                }
            </div>
        </div>
    );
}

export default FatherDetails;