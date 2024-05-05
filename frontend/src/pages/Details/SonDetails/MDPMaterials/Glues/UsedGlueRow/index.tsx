import { DGlue, DUsedGlue } from 'models/entities';
import * as GlueService from 'services/MDP/glueService';
import * as usedGlueService from 'services/MDP/usedGlueService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedGlueModal from '../UsedGlueModal';

type Props = {
    usedGlue : DUsedGlue;
    onDeleteOrEdit: Function;
}

const UsedGlueRow = ({usedGlue, onDeleteOrEdit} : Props) => {

    const [Glue, setGlue] = useState<DGlue>();

    const getGlue = useCallback(() => {
        GlueService.findById(usedGlue.glueCode)
            .then(response => {
                setGlue(response.data);
            }).catch(error => console.log(error))
    }, [usedGlue]);

    useEffect(() => {
        getGlue();
    }, [getGlue]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedGlueService.deleteById(objectId)
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
                <span>{Glue?.code} - {Glue?.description} - {usedGlue.netQuantity} {usedGlue.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedGlue.id)}/>

                <UsedGlueModal usedGlue={usedGlue} mdpSonCode={usedGlue.mdpSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedGlueRow;