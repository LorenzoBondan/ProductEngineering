import { DMDPSon } from "models/entities";
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { useState } from "react";
import * as MDPSonService from 'services/MDP/mdpSonService';
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";
import MDPSonModal from "../MDPSonModal";

type Props = {
    MDPSon: DMDPSon;
    onDeleteOrEdit: Function;
    handleRowClick: (code: string) => void; 
}

const MDPSonRow = ({ MDPSon, onDeleteOrEdit, handleRowClick }: Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja mudar a situação esse item?")) {
            return;
        }

        MDPSonService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        MDPSonService.deleteById(objectId)
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
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.code}</td>
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.description}</td>
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.measure1}</td>
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.measure2}</td>
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.measure3}</td>
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.measurementUnit}</td>
            {MDPSon.implementation ? <td onClick={() => handleRowClick(MDPSon.code.toString())}>{convertDateTime(MDPSon.implementation.toString())}</td> : <td>-</td>}
            {MDPSon.color ? <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.color.name}</td> : <td>-</td>}
            <td onClick={() => handleRowClick(MDPSon.code.toString())}>R$ {MDPSon.value}</td>
            {MDPSon.fatherCode ? <td onClick={() => handleRowClick(MDPSon.code.toString())}>{MDPSon.fatherCode}</td> : <td></td>}
            <td><EditSvg onClick={() => openModal()} /></td>
            <td><RiShutDownLine onClick={() => inactivateObject(MDPSon.code)} /></td>
            <td><DeleteSvg onClick={() => deleteObject(MDPSon.code)} /></td>

            <MDPSonModal MDPSon={MDPSon} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default MDPSonRow;
