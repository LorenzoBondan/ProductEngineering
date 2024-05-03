import { DFather } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as fatherService from 'services/public/fatherService';
import FatherModal from "../FatherModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    Father: DFather;
    onDeleteOrEdit: Function;
}

const FatherRow = ({Father, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        fatherService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
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

    return(
        <tr>
            <td>{Father.code}</td>
            <td>{Father.description}</td>
            <td>{Father.measure1}</td>
            <td>{Father.measure2}</td>
            <td>{Father.measure3}</td>
            <td>{Father.measurementUnit}</td>
            {Father.implementation ? <td>{Father.implementation.toString()}</td> : <td>-</td>}
            {Father.color ? <td>{Father.color.name}</td> : <td>-</td>}
            <td>R$ {Father.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Father.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Father.code)}/></td>

            <FatherModal father={Father} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default FatherRow;