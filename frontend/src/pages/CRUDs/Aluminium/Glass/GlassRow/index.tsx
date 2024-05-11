import { DGlass } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as glassService from 'services/Aluminium/glassService';
import GlassModal from "../GlassModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";
import { convertDateTime } from "helpers";

type Props = {
    glass: DGlass;
    onDeleteOrEdit: Function;
}

const GlassRow = ({glass, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        glassService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        glassService.deleteById(objectId)
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
            <td>{glass.code}</td>
            <td>{glass.description}</td>
            <td>{glass.measure1}</td>
            <td>{glass.measure2}</td>
            <td>{glass.measure3}</td>
            <td>{glass.measurementUnit}</td>
            {glass.implementation ? <td>{convertDateTime(glass.implementation.toString())}</td> : <td>-</td>}
            {glass.color ? <td>{glass.color.name}</td> : <td>-</td>}
            <td>R$ {glass.value}</td>
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(glass.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(glass.code)}/></td>

            <GlassModal glass={glass} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default GlassRow;