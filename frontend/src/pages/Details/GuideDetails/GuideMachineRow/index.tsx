import { DMachine, DGuideMachine } from 'models/entities';
import * as machineService from 'services/Guides/machineService';
import * as guideMachineService from 'services/Guides/guideMachineService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import GuideMachineModal from '../GuideMachineModal';

type Props = {
    guideMachine : DGuideMachine;
    onDeleteOrEdit: Function;
}

const GuideMachineRow = ({guideMachine, onDeleteOrEdit} : Props) => {

    const [machine, setMachine] = useState<DMachine>();

    const getMachine = useCallback(() => {
        machineService.findById(guideMachine.machineId)
            .then(response => {
                setMachine(response.data);
            }).catch(error => console.log(error))
    }, [guideMachine]);

    useEffect(() => {
        getMachine();
    }, [getMachine]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        guideMachineService.deleteById(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Excluído com sucesso!");
            });
    }

    // modal functions

    const [modalIsOpen, setIsOpen] = useState(false);

    const openModal = () => {
        setIsOpen(true);
    }
    
    const closeModal = () => {
        setIsOpen(false);
    }

    return(
        <li className='son-list-item'>
            <div className='son-list-item-info'>
                <span>{machine?.name} - {guideMachine.machineTime} MIN</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(guideMachine.id)}/>

                <GuideMachineModal guideMachine={guideMachine} guideId={guideMachine.guideId} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default GuideMachineRow;