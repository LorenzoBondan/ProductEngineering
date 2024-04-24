import { DMaterial } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as MaterialService from 'services/public/materialService';
import MaterialModal from "../MaterialModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    Material: DMaterial;
    onDeleteOrEdit: Function;
}

const MaterialRow = ({Material, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        MaterialService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        MaterialService.deleteById(objectId)
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
        <tr>
            <td>{Material.id}</td>
            <td>{Material.name}</td>

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Material.id)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Material.id)}/></td>

            <MaterialModal Material={Material} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default MaterialRow;