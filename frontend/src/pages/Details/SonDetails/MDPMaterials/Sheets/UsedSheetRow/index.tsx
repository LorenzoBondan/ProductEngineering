import { DSheet, DUsedSheet } from 'models/entities';
import * as sheetService from 'services/MDP/sheetService';
import * as usedSheetService from 'services/MDP/usedSheetService';
import './styles.css';
import { useCallback, useEffect, useState } from 'react';
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { toast } from "react-toastify";
import UsedSheetModal from '../UsedSheetModal';

type Props = {
    usedSheet : DUsedSheet;
    onDeleteOrEdit: Function;
}

const UsedSheetRow = ({usedSheet, onDeleteOrEdit} : Props) => {

    const [sheet, setSheet] = useState<DSheet>();

    const getSheet = useCallback(() => {
        sheetService.findById(usedSheet.sheetCode)
            .then(response => {
                setSheet(response.data);
            }).catch(error => console.log(error))
    }, [usedSheet]);

    useEffect(() => {
        getSheet();
    }, [getSheet]);

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        usedSheetService.deleteById(objectId)
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
                <span>{sheet?.code} - {sheet?.description} - {usedSheet.netQuantity} {usedSheet.measurementUnit}</span>
            </div>
            <div className='son-list-item-buttons'>
                <EditSvg onClick={() => openModal()}/>
                <DeleteSvg onClick={() => deleteObject(usedSheet.id)}/>

                <UsedSheetModal usedSheet={usedSheet} mdpSonCode={usedSheet.mdpSonCode} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
            </div>
        </li>
    );
}

export default UsedSheetRow;