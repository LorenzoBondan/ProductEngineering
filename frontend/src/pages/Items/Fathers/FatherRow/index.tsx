import { DFather } from "models/entities";
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { useState } from "react";
import * as fatherService from 'services/public/fatherService';
import FatherModal from "../FatherModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    Father: DFather;
    onDeleteOrEdit: Function;
    handleRowClick: (code: string) => void; // Adicionado handleRowClick como uma propriedade
}

const FatherRow = ({ Father, onDeleteOrEdit, handleRowClick }: Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja mudar a situação esse item?")) {
            return;
        }

        fatherService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        fatherService.deleteById(objectId)
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
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.code}</td>
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.description}</td>
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.measure1}</td>
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.measure2}</td>
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.measure3}</td>
            <td onClick={() => handleRowClick(Father.code.toString())}>{Father.measurementUnit}</td>
            {Father.implementation ? <td onClick={() => handleRowClick(Father.code.toString())}>{convertDateTime(Father.implementation.toString())}</td> : <td>-</td>}
            {Father.color ? <td onClick={() => handleRowClick(Father.code.toString())}>{Father.color.name}</td> : <td>-</td>}
            <td onClick={() => handleRowClick(Father.code.toString())}>R$ {Father.value}</td>
            <td><EditSvg onClick={() => openModal()} /></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Father.code)} /></td>
            <td><DeleteSvg onClick={() => deleteObject(Father.code)} /></td>

            <FatherModal father={Father} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default FatherRow;
