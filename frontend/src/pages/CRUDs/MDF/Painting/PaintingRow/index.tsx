import { DPainting } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as paintingService from 'services/MDF/paintingService';
import PaintingModal from "../PaintingModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    painting: DPainting;
    onDeleteOrEdit: Function;
}

const PaintingRow = ({painting, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        paintingService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        paintingService.deleteById(objectId)
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
            <td>{painting.code}</td>
            <td>{painting.description}</td>
            <td>{painting.family}</td>
            {painting.implementation ? <td>{painting.implementation.toString()}</td> : <td>-</td>}
            <td>{painting.lostPercentage}</td>
            {painting.color ? <td>{painting.color.name}</td> : <td>-</td>}
            <td>{painting.paintingType.description}</td>
            <td>R$ {painting.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(painting.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(painting.code)}/></td>

            <PaintingModal painting={painting} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PaintingRow;