import { DEdgeBanding, DUsedEdgeBanding } from 'models/entities';
import * as edgeBandingService from 'services/MDP/edgeBandingService';
import * as usedEdgeBandingService from 'services/MDP/usedEdgeBandingService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedEdgeBandingModal from '../UsedEdgeBandingModal';

type Props = {
    usedEdgeBanding : DUsedEdgeBanding;
    onDeleteOrEdit: Function;
}

const UsedEdgeBandingRow = ({usedEdgeBanding, onDeleteOrEdit} : Props) => {

    const [EdgeBanding, setEdgeBanding] = useState<DEdgeBanding>();

    const getEdgeBanding = useCallback(() => {
        edgeBandingService.findById(usedEdgeBanding.edgeBandingCode)
            .then(response => {
                setEdgeBanding(response.data);
            }).catch(error => console.log(error))
    }, [usedEdgeBanding]);

    useEffect(() => {
        getEdgeBanding();
    }, [getEdgeBanding]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedEdgeBandingService.deleteById(objectId)
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
                <span>{EdgeBanding?.code} - {EdgeBanding?.description} - {usedEdgeBanding.netQuantity} {usedEdgeBanding.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedEdgeBanding.id)}/>

                <UsedEdgeBandingModal usedEdgeBanding={usedEdgeBanding} mdpSonCode={usedEdgeBanding.mdpSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedEdgeBandingRow;