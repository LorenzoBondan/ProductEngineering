import { DGuide } from 'models/entities';
import * as guideService from 'services/Guides/guideService';
import { useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import GuideModal from '../GuideModal';

type Props = {
    guide : DGuide;
    onDeleteOrEdit: Function;
}

const UsedGuideRow = ({guide, onDeleteOrEdit} : Props) => {

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        guideService.deleteById(objectId)
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
                <span>{guide?.description}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(guide.id)}/>

                <GuideModal guide={guide} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedGuideRow;