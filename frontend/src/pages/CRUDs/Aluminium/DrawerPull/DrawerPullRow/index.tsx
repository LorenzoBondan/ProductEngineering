import { DDrawerPull } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as drawerPullService from 'services/Aluminium/drawerPullService';
import DrawerPullModal from "../DrawerPullModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    drawerPull: DDrawerPull;
    onDeleteOrEdit: Function;
}

const DrawerPullRow = ({drawerPull, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        drawerPullService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        drawerPullService.deleteById(objectId)
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
            <td>{drawerPull.code}</td>
            <td>{drawerPull.description}</td>
            <td>{drawerPull.measure1}</td>
            <td>{drawerPull.measure2}</td>
            <td>{drawerPull.measure3}</td>
            <td>{drawerPull.measurementUnit}</td>
            {drawerPull.implementation ? <td>{drawerPull.implementation.toString()}</td> : <td>-</td>}
            
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(drawerPull.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(drawerPull.code)}/></td>

            <DrawerPullModal drawerPull={drawerPull} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default DrawerPullRow;