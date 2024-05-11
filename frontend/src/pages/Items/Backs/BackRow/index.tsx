import { DBack } from "models/entities";
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { useState } from "react";
import * as BackService from 'services/MDF/backService';
import BackModal from "../BackModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    Back: DBack;
    onDeleteOrEdit: Function;
    handleRowClick: (code: string) => void; // Adicionado handleRowClick como uma propriedade
}

const BackRow = ({ Back, onDeleteOrEdit, handleRowClick }: Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja mudar a situação esse item?")) {
            return;
        }

        BackService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        BackService.deleteById(objectId)
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

    return (
        <tr className="item-row">
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.code}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.description}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.measure1}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.measure2}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.thickness}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.family}</td>
            {Back.implementation ? <td onClick={() => handleRowClick(Back.code.toString())}>{convertDateTime(Back.implementation.toString())}</td> : <td>-</td>}
            <td onClick={() => handleRowClick(Back.code.toString())}>{Back.suffix}</td>
            <td onClick={() => handleRowClick(Back.code.toString())}>R$ {Back.value}</td>
            <td><EditSvg onClick={() => openModal()} /></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Back.code)} /></td>
            <td><DeleteSvg onClick={() => deleteObject(Back.code)} /></td>

            <BackModal Back={Back} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default BackRow;
