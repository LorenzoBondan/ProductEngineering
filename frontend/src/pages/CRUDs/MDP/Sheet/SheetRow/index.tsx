import { DMaterial, DSheet } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useEffect, useState } from "react";
import * as sheetService from 'services/MDP/sheetService';
import * as materialService from 'services/public/materialService';
import SheetModal from "../SheetModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    sheet: DSheet;
    onDeleteOrEdit: Function;
}

const SheetRow = ({sheet, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja inativar esse item?")){
          return;
        }

        sheetService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Inativado com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        sheetService.deleteById(objectId)
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

    // materialId

    const [material, setMaterial] = useState<DMaterial>();

    const getMaterial = (id: number) => {
        materialService.findById(id)
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
            {sheet.implementation ? <td>{sheet.implementation.toString()}</td> : <td>-</td>}
            <td>{sheet.lostPercentage}</td>
            {sheet.color ? <td>{sheet.color.name}</td> : <td>-</td>}
            <td>{sheet.thickness}</td>
            <td>{sheet.faces}</td>
            {sheet.materialId ? <td>{material?.name}</td> : <td>-</td>}
            
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(sheet.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(sheet.code)}/></td>

            <SheetModal sheet={sheet} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default SheetRow;