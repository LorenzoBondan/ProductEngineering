import { DGuide } from "models/entities";
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { useState } from "react";
import * as GuideService from 'services/Guides/guideService';
import GuideModal from "../GuideModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    Guide: DGuide;
    onDeleteOrEdit: Function;
    handleRowClick: (code: string) => void; // Adicionado handleRowClick como uma propriedade
}

const GuideRow = ({ Guide, onDeleteOrEdit, handleRowClick }: Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja mudar a situação esse roteiro?")) {
            return;
        }

        GuideService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse roteiro?")) {
            return;
        }

        GuideService.deleteById(objectId)
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
            <td onClick={() => handleRowClick(Guide.id.toString())}>{Guide.id}</td>
            <td onClick={() => handleRowClick(Guide.id.toString())}>{Guide.description}</td>
            <td onClick={() => handleRowClick(Guide.id.toString())}>{convertDateTime(Guide.implementation.toString())}</td>
            <td onClick={() => handleRowClick(Guide.id.toString())}>{convertDateTime(Guide.finalDate.toString())}</td>
            <td onClick={() => handleRowClick(Guide.id.toString())}>R$ {Guide.value}</td>
            <td><EditSvg onClick={() => openModal()} /></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Guide.id)} /></td>
            <td><DeleteSvg onClick={() => deleteObject(Guide.id)} /></td>

            <GuideModal Guide={Guide} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default GuideRow;
