import { DPolyethylene } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as PolyethyleneService from 'services/Packaging/polyethyleneService';
import PolyethyleneModal from "../PolyethyleneModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    Polyethylene: DPolyethylene;
    onDeleteOrEdit: Function;
}

const PolyethyleneRow = ({Polyethylene, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        PolyethyleneService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        PolyethyleneService.deleteById(objectId)
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
            <td>{Polyethylene.code}</td>
            <td>{Polyethylene.description}</td>
            <td>{Polyethylene.family}</td>
            {Polyethylene.implementation ? <td>{Polyethylene.implementation.toString()}</td> : <td>-</td>}
            <td>{Polyethylene.lostPercentage}</td>

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Polyethylene.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Polyethylene.code)}/></td>

            <PolyethyleneModal Polyethylene={Polyethylene} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PolyethyleneRow;