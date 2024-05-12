import { useParams } from 'react-router-dom';
import { DGuide } from 'models/entities';
import { useCallback, useEffect, useState } from 'react';
import * as GuideService from 'services/Guides/guideService';
import { IoCalendarNumberOutline } from "react-icons/io5";
import { FaRegMoneyBillAlt } from "react-icons/fa";
import { convertDateTime } from 'helpers';
import { IoMdAdd } from 'react-icons/io';
import GuideMachineRow from './GuideMachineRow';
import GuideMachineModal from './GuideMachineModal';

type UrlParams = {
    guideId: string;
}

const GuideDetails = () => {

    const { guideId } = useParams<UrlParams>();
    const [Guide, setGuide] = useState<DGuide>();

    const getGuide = useCallback(() => {
        GuideService.findById(parseInt(guideId))
            .then(response => setGuide(response.data))
            .catch(error => console.error(error));
    }, [guideId]);

    useEffect(() => {
        getGuide();
    }, [getGuide]);

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
                    <h4>{Guide?.description}</h4>
                </div>
                <div className='father-details'>
                    <h5><FaRegMoneyBillAlt/> <strong>Valor:</strong> R$ {Guide?.value}</h5>
                    <h5><IoCalendarNumberOutline/> <strong>Implementação:</strong> {Guide?.implementation && convertDateTime(Guide?.implementation.toString())}</h5>
                    <h5><IoCalendarNumberOutline/> <strong>Data Final:</strong> {Guide?.finalDate && convertDateTime(Guide?.finalDate.toString())}</h5>
                </div>
                <div className="son-material-container">
                    <div className="son-material-top-menu">
                        <h5>Máquinas</h5>
                        <IoMdAdd onClick={openModal} /> 
                    </div>
                    <ul className='father-sons-list'>
                        {Guide?.guideMachines.map(guideMachine => (
                            <>
                                <GuideMachineRow guideMachine={guideMachine} onDeleteOrEdit={getGuide} key={guideMachine.id} />
                                <GuideMachineModal guideMachine={guideMachine} guideId={Guide.id} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getGuide()} />
                            </>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default GuideDetails;