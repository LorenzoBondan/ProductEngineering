import { DEdgeBanding } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as EdgeBandingService from 'services/MDP/edgeBandingService';
import EdgeBandingModal from "../EdgeBandingModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    edgeBanding: DEdgeBanding;
    onDeleteOrEdit: Function;
}

const EdgeBandingRow = ({edgeBanding, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        EdgeBandingService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        EdgeBandingService.deleteById(objectId)
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
            <td>{edgeBanding.code}</td>
            <td>{edgeBanding.description}</td>
            <td>{edgeBanding.family}</td>
            {edgeBanding.implementation ? <td>{convertDateTime(edgeBanding.implementation.toString())}</td> : <td>-</td>}
            <td>{edgeBanding.lostPercentage}</td>
            {edgeBanding.color ? <td>{edgeBanding.color.name}</td> : <td>-</td>}
            <td>{edgeBanding.height}</td>
            <td>{edgeBanding.thickness}</td>
            <td>R$ {edgeBanding.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(edgeBanding.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(edgeBanding.code)}/></td>

            <EdgeBandingModal edgeBanding={edgeBanding} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default EdgeBandingRow;