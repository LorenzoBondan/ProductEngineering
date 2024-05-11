import { DSheet, DUsedBackSheet } from 'models/entities';
import * as sheetService from 'services/MDP/sheetService';
import * as usedBackSheetService from 'services/MDF/usedBackSheetService';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedBackSheetModal from '../UsedBackSheetModal';

type Props = {
    usedBackSheet : DUsedBackSheet;
    onDeleteOrEdit: Function;
}

const UsedBackSheetRow = ({usedBackSheet, onDeleteOrEdit} : Props) => {

    const [sheet, setSheet] = useState<DSheet>();

    const getSheet = useCallback(() => {
        sheetService.findById(usedBackSheet.sheetCode)
            .then(response => {
                setSheet(response.data);
            }).catch(error => console.log(error))
    }, [usedBackSheet]);

    useEffect(() => {
        getSheet();
    }, [getSheet]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedBackSheetService.deleteById(objectId)
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
                <span>{sheet?.code} - {sheet?.description} - {usedBackSheet.netQuantity} {usedBackSheet.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedBackSheet.id)}/>

                <UsedBackSheetModal usedBackSheet={usedBackSheet} backCode={usedBackSheet.backCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedBackSheetRow;