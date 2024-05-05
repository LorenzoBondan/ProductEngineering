import { DPainting, DUsedPainting } from 'models/entities';
import * as PaintingService from 'services/MDF/paintingService';
import * as usedPaintingService from 'services/MDF/usedPaintingService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedPaintingModal from '../UsedPaintingModal';

type Props = {
    usedPainting : DUsedPainting;
    onDeleteOrEdit: Function;
}

const UsedPaintingRow = ({usedPainting, onDeleteOrEdit} : Props) => {

    const [Painting, setPainting] = useState<DPainting>();

    const getPainting = useCallback(() => {
        PaintingService.findById(usedPainting.paintingCode)
            .then(response => {
                setPainting(response.data);
            }).catch(error => console.log(error))
    }, [usedPainting]);

    useEffect(() => {
        getPainting();
    }, [getPainting]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedPaintingService.deleteById(objectId)
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
                <span>{Painting?.code} - {Painting?.description} - {usedPainting.netQuantity} {usedPainting.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedPainting.id)}/>

                <UsedPaintingModal usedPainting={usedPainting} paintingSonCode={usedPainting.paintingSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedPaintingRow;