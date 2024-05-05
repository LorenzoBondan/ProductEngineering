import { DPolyester, DUsedPolyester } from 'models/entities';
import * as PolyesterService from 'services/MDF/polyesterService';
import * as usedPolyesterService from 'services/MDF/usedPolyesterService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedPolyesterModal from '../UsedPolyesterModal';

type Props = {
    usedPolyester : DUsedPolyester;
    onDeleteOrEdit: Function;
}

const UsedPolyesterRow = ({usedPolyester, onDeleteOrEdit} : Props) => {

    const [Polyester, setPolyester] = useState<DPolyester>();

    const getPolyester = useCallback(() => {
        PolyesterService.findById(usedPolyester.polyesterCode)
            .then(response => {
                setPolyester(response.data);
            }).catch(error => console.log(error))
    }, [usedPolyester]);

    useEffect(() => {
        getPolyester();
    }, [getPolyester]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedPolyesterService.deleteById(objectId)
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
                <span>{Polyester?.code} - {Polyester?.description} - {usedPolyester.netQuantity} {usedPolyester.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedPolyester.id)}/>

                <UsedPolyesterModal usedPolyester={usedPolyester} paintingSonCode={usedPolyester.paintingSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedPolyesterRow;