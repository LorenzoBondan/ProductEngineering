import { DPaintingBorderBackground, DUsedPaintingBorderBackground } from 'models/entities';
import * as PaintingBorderBackgroundService from 'services/MDF/paintingBorderBackgroundService';
import * as usedPaintingBorderBackgroundService from 'services/MDF/usedPaintingBorderBackgroundService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedPaintingBorderBackgroundModal from '../UsedPaintingBorderBackgroundModal';

type Props = {
    usedPaintingBorderBackground : DUsedPaintingBorderBackground;
    onDeleteOrEdit: Function;
}

const UsedPaintingBorderBackgroundRow = ({usedPaintingBorderBackground, onDeleteOrEdit} : Props) => {

    const [PaintingBorderBackground, setPaintingBorderBackground] = useState<DPaintingBorderBackground>();

    const getPaintingBorderBackground = useCallback(() => {
        PaintingBorderBackgroundService.findById(usedPaintingBorderBackground.paintingBorderBackgroundCode)
            .then(response => {
                setPaintingBorderBackground(response.data);
            }).catch(error => console.log(error))
    }, [usedPaintingBorderBackground]);

    useEffect(() => {
        getPaintingBorderBackground();
    }, [getPaintingBorderBackground]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedPaintingBorderBackgroundService.deleteById(objectId)
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
                <span>{PaintingBorderBackground?.code} - {PaintingBorderBackground?.description} - {usedPaintingBorderBackground.netQuantity} {usedPaintingBorderBackground.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedPaintingBorderBackground.id)}/>

                <UsedPaintingBorderBackgroundModal usedPaintingBorderBackground={usedPaintingBorderBackground} paintingSonCode={usedPaintingBorderBackground.paintingSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedPaintingBorderBackgroundRow;