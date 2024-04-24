import { DMachineGroup } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as MachineGroupService from 'services/Guides/machineGroupService';
import MachineGroupModal from "../MachineGroupModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    MachineGroup: DMachineGroup;
    onDeleteOrEdit: Function;
}

const MachineGroupRow = ({MachineGroup, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        MachineGroupService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        MachineGroupService.deleteById(objectId)
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
            <td>{MachineGroup.id}</td>
            <td>{MachineGroup.name}</td>

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(MachineGroup.id)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(MachineGroup.id)}/></td>

            <MachineGroupModal MachineGroup={MachineGroup} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default MachineGroupRow;