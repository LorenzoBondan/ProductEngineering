import { useParams } from 'react-router-dom';
import { DBack } from 'models/entities';
import { useCallback, useEffect, useState } from 'react';
import * as backService from 'services/MDF/backService';
import { IoCalendarNumberOutline } from "react-icons/io5";
import { FaRegMoneyBillAlt } from "react-icons/fa";
import { convertDateTime } from 'helpers';
import { IoMdAdd } from 'react-icons/io';
import UsedBackSheetRow from './UsedBackSheetRow';
import UsedBackSheetModal from './UsedBackSheetModal';

type UrlParams = {
    backId: string;
}

const BackDetails = () => {

    const { backId } = useParams<UrlParams>();
    const [back, setBack] = useState<DBack>();

    const getBack = useCallback(() => {
        backService.findById(parseInt(backId))
            .then(response => setBack(response.data))
            .catch(error => console.error(error));
    }, [backId]);

    useEffect(() => {
        getBack();
    }, [getBack]);

    // modal functions

    const [modalIsOpen, setIsOpen] = useState(false);

    const openModal = () => {
        setIsOpen(true);
    }
            
    const closeModal = () => {
        setIsOpen(false);
    }

    return(
        <div className='father-details-page'>
            <div className='father-details-container base-card'>
                <div className='father-title crud-modal-title'>
                    <h4>{back?.code} - {back?.description}</h4>
                </div>
                <div className='father-details'>
                    <h5><FaRegMoneyBillAlt/> <strong>Valor:</strong> R$ {back?.value}</h5>
                    <h5><IoCalendarNumberOutline/> <strong>Implementação:</strong> {back?.implementation && convertDateTime(back?.implementation.toString())}</h5>
                </div>
                <div className="son-material-container">
                    <div className="son-material-top-menu">
                        <h5>Chapas de Fundo</h5>
                        <IoMdAdd onClick={openModal} /> 
                    </div>
                    <ul className='father-sons-list'>
                        {back?.usedBackSheets.map(backSheet => (
                            <>
                                <UsedBackSheetRow usedBackSheet={backSheet} onDeleteOrEdit={getBack} key={backSheet.id} />
                                <UsedBackSheetModal usedBackSheet={backSheet} backCode={back.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getBack()} />
                            </>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default BackDetails;