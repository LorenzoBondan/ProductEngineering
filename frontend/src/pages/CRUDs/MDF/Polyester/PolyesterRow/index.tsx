import { DPolyester } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as polyesterService from 'services/MDF/polyesterService';
import PolyesterModal from "../PolyesterModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    polyester: DPolyester;
    onDeleteOrEdit: Function;
}

const PolyesterRow = ({polyester, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        polyesterService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        polyesterService.deleteById(objectId)
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
            <td>{polyester.code}</td>
            <td>{polyester.description}</td>
            <td>{polyester.family}</td>
            {polyester.implementation ? <td>{polyester.implementation.toString()}</td> : <td>-</td>}
            <td>{polyester.lostPercentage}</td>
            
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(polyester.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(polyester.code)}/></td>

            <PolyesterModal polyester={polyester} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PolyesterRow;