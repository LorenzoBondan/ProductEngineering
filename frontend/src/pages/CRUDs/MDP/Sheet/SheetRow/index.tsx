import { DMaterial, DSheet } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useEffect, useState } from "react";
import { requestBackend } from "util/requests";
import * as sheetService from 'services/MDP/sheetService';
import SheetModal from "../SheetModal";

type Props = {
    sheet: DSheet;
    onDeleteOrEdit: Function;
}

const SheetRow = ({sheet, onDeleteOrEdit} : Props) => {

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        sheetService.deleteById(objectId)
            .then(() => {
                onDeleteOrEdit();
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

    // materialId
    const [material, setMaterial] = useState<DMaterial>();

    const getMaterial = (id: number) => {
        requestBackend({url: `/materials/${id}`, withCredentials: true})
            .then(response => {
                setMaterial(response.data)
        })
    }

    useEffect(() => {
        getMaterial(sheet.materialId);
    }, [sheet.materialId, onDeleteOrEdit]);

    return(
        <tr>
            <td>{sheet.code}</td>
            <td>{sheet.description}</td>
            <td>{sheet.family}</td>
            <td>{sheet.faces}</td>
            <td>{sheet.thickness}</td>
            {sheet.implementation ? <td>{sheet.implementation.toString()}</td> : <td>-</td>}
            <td>{sheet.lostPercentage}</td>
            {sheet.materialId ? <td>{material?.name}</td> : <td>-</td>}
            {sheet.color ? <td>{sheet.color.name}</td> : <td>-</td>}
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><DeleteSvg onClick={() => deleteObject(sheet.code)}/></td>

            <SheetModal sheet={sheet} isOpen={modalIsOpen} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default SheetRow;