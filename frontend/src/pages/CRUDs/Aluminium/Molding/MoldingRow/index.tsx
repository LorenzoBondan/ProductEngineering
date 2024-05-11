import { DMolding } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as MoldingService from 'services/Aluminium/moldingService';
import MoldingModal from "../MoldingModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    Molding: DMolding;
    onDeleteOrEdit: Function;
}

const MoldingRow = ({Molding, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        MoldingService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        MoldingService.deleteById(objectId)
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
            <td>{Molding.code}</td>
            <td>{Molding.description}</td>
            <td>{Molding.measure1}</td>
            <td>{Molding.measure2}</td>
            <td>{Molding.measure3}</td>
            <td>{Molding.measurementUnit}</td>
            {Molding.implementation ? <td>{convertDateTime(Molding.implementation.toString())}</td> : <td>-</td>}
            <td>R$ {Molding.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Molding.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Molding.code)}/></td>

            <MoldingModal Molding={Molding} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default MoldingRow;