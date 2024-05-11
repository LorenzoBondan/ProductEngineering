import { DScrew } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as ScrewService from 'services/Aluminium/screwService';
import ScrewModal from "../ScrewModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    Screw: DScrew;
    onDeleteOrEdit: Function;
}

const ScrewRow = ({Screw, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        ScrewService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        ScrewService.deleteById(objectId)
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
            <td>{Screw.code}</td>
            <td>{Screw.description}</td>
            <td>{Screw.measure1}</td>
            <td>{Screw.measure2}</td>
            <td>{Screw.measure3}</td>
            <td>{Screw.measurementUnit}</td>
            {Screw.implementation ? <td>{convertDateTime(Screw.implementation.toString())}</td> : <td>-</td>}
            <td>R$ {Screw.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Screw.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Screw.code)}/></td>

            <ScrewModal Screw={Screw} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default ScrewRow;