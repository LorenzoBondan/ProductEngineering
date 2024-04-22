import { DMachine, DMachineGroup } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useEffect, useState } from "react";
import * as machineService from 'services/Guides/machineService';
import * as machineGroupService from 'services/Guides/machineGroupService';
import MachineModal from "../MachineModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    machine: DMachine;
    onDeleteOrEdit: Function;
}

const MachineRow = ({machine, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        machineService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        machineService.deleteById(objectId)
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

    // machineGroupId

    const [machineGroup, setMachineGroup] = useState<DMachineGroup>();

    const getMachineGroup = (id: number) => {
        machineGroupService.findById(id)
            .then(response => {
                setMachineGroup(response.data)
        })
    }
    
    useEffect(() => {
        getMachineGroup(machine.machineGroupId);
    }, [machine.machineGroupId, onDeleteOrEdit]);

    return(
        <tr>
            <td>{machine.id}</td>
            <td>{machine.name}</td>
            <td>{machine.formula}</td>
            {machine.machineGroupId ? <td>{machineGroup?.name}</td> : <td></td>}

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(machine.id)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(machine.id)}/></td>

            <MachineModal machine={machine} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default MachineRow;