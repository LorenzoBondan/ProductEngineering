import { DPaintingBorderBackground } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as paintingBorderBackgroundService from 'services/MDF/paintingBorderBackgroundService';
import PaintingBorderBackgroundModal from "../PaintingBorderBackgroundModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    paintingBorderBackground: DPaintingBorderBackground;
    onDeleteOrEdit: Function;
}

const PaintingBorderBackgroundRow = ({paintingBorderBackground, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        paintingBorderBackgroundService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        paintingBorderBackgroundService.deleteById(objectId)
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
            <td>{paintingBorderBackground.code}</td>
            <td>{paintingBorderBackground.description}</td>
            <td>{paintingBorderBackground.family}</td>
            {paintingBorderBackground.implementation ? <td>{paintingBorderBackground.implementation.toString()}</td> : <td>-</td>}
            <td>{paintingBorderBackground.lostPercentage}</td>
            <td>R$ {paintingBorderBackground.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(paintingBorderBackground.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(paintingBorderBackground.code)}/></td>

            <PaintingBorderBackgroundModal paintingBorderBackground={paintingBorderBackground} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PaintingBorderBackgroundRow;