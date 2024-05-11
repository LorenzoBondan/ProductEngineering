import { DCornerBracket } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as CornerBracketService from 'services/Packaging/cornerBracketService';
import CornerBracketModal from "../CornerBracketModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    CornerBracket: DCornerBracket;
    onDeleteOrEdit: Function;
}

const CornerBracketRow = ({CornerBracket, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        CornerBracketService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        CornerBracketService.deleteById(objectId)
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
            <td>{CornerBracket.code}</td>
            <td>{CornerBracket.description}</td>
            <td>{CornerBracket.family}</td>
            {CornerBracket.implementation ? <td>{convertDateTime(CornerBracket.implementation.toString())}</td> : <td>-</td>}
            <td>{CornerBracket.lostPercentage}</td>
            <td>R$ {CornerBracket.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(CornerBracket.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(CornerBracket.code)}/></td>

            <CornerBracketModal CornerBracket={CornerBracket} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default CornerBracketRow;