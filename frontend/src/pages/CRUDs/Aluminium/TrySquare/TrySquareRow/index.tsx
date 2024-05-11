import { DTrySquare } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as TrySquareService from 'services/Aluminium/trySquareService';
import TrySquareModal from "../TrySquareModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    TrySquare: DTrySquare;
    onDeleteOrEdit: Function;
}

const TrySquareRow = ({TrySquare, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        TrySquareService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        TrySquareService.deleteById(objectId)
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
            <td>{TrySquare.code}</td>
            <td>{TrySquare.description}</td>
            <td>{TrySquare.measure1}</td>
            <td>{TrySquare.measure2}</td>
            <td>{TrySquare.measure3}</td>
            <td>{TrySquare.measurementUnit}</td>
            {TrySquare.implementation ? <td>{convertDateTime(TrySquare.implementation.toString())}</td> : <td>-</td>}
            <td>R$ {TrySquare.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(TrySquare.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(TrySquare.code)}/></td>

            <TrySquareModal TrySquare={TrySquare} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default TrySquareRow;