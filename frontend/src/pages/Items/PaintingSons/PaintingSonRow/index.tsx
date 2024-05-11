import { DPaintingSon } from "models/entities";
import { ReactComponent as EditSvg } from "assets/images/edit.svg";
import { ReactComponent as DeleteSvg } from "assets/images/delete.svg";
import { useState } from "react";
import * as PaintingSonService from 'services/MDF/paintingSonService';
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";
import PaintingSonModal from "../PaintingSonModal";


type Props = {
    PaintingSon: DPaintingSon;
    onDeleteOrEdit: Function;
    handleRowClick: (code: string) => void; 
}

const PaintingSonRow = ({ PaintingSon, onDeleteOrEdit, handleRowClick }: Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja mudar a situação esse item?")) {
            return;
        }

        PaintingSonService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId: number) => {
        if (!window.confirm("Tem certeza que deseja excluir esse item?")) {
            return;
        }

        PaintingSonService.deleteById(objectId)
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
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.code}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.description}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.measure1}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.measure2}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.measure3}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.measurementUnit}</td>
            {PaintingSon.implementation ? <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{convertDateTime(PaintingSon.implementation.toString())}</td> : <td>-</td>}
            {PaintingSon.color ? <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.color.name}</td> : <td>-</td>}
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>R$ {PaintingSon.value}</td>
            {PaintingSon.fatherCode ? <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.fatherCode}</td> : <td></td>}
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>
            {
                PaintingSon?.satin ? "Acetinada" :
                PaintingSon?.highShine ? "Alto Brilho" :
                PaintingSon?.satinGlass ? "Acetinada vidros" :
                ""
            }
            </td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.faces}</td>
            <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.special ? "Sim" : "Não"}</td>
            {PaintingSon.back ? <td onClick={() => handleRowClick(PaintingSon.code.toString())}>{PaintingSon.back.code}</td> : <td></td>}
            <td><EditSvg onClick={() => openModal()} /></td>
            <td><RiShutDownLine onClick={() => inactivateObject(PaintingSon.code)} /></td>
            <td><DeleteSvg onClick={() => deleteObject(PaintingSon.code)} /></td>

            <PaintingSonModal PaintingSon={PaintingSon} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PaintingSonRow;
