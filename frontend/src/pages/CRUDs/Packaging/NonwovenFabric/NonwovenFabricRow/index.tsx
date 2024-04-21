import { DNonwovenFabric } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as NonwovenFabricService from 'services/Packaging/nonwovenFabricService';
import NonwovenFabricModal from "../NonwovenFabricModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    NonwovenFabric: DNonwovenFabric;
    onDeleteOrEdit: Function;
}

const NonwovenFabricRow = ({NonwovenFabric, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        NonwovenFabricService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        NonwovenFabricService.deleteById(objectId)
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
            <td>{NonwovenFabric.code}</td>
            <td>{NonwovenFabric.description}</td>
            <td>{NonwovenFabric.family}</td>
            {NonwovenFabric.implementation ? <td>{NonwovenFabric.implementation.toString()}</td> : <td>-</td>}
            <td>{NonwovenFabric.lostPercentage}</td>

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(NonwovenFabric.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(NonwovenFabric.code)}/></td>

            <NonwovenFabricModal NonwovenFabric={NonwovenFabric} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default NonwovenFabricRow;