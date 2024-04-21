import { DPlastic } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useState } from "react";
import * as PlasticService from 'services/Packaging/plasticService';
import PlasticModal from "../PlasticModal";
import { toast } from "react-toastify";
import { RiShutDownLine } from "react-icons/ri";

type Props = {
    Plastic: DPlastic;
    onDeleteOrEdit: Function;
}

const PlasticRow = ({Plastic, onDeleteOrEdit} : Props) => {

    // inactivate and delete methods 

    const inactivateObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja mudar a situação esse item?")){
          return;
        }

        PlasticService.inactivate(objectId)
            .then(() => {
                onDeleteOrEdit();
                toast.success("Situação alterada com sucesso!");
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        PlasticService.deleteById(objectId)
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
            <td>{Plastic.code}</td>
            <td>{Plastic.description}</td>
            <td>{Plastic.family}</td>
            {Plastic.implementation ? <td>{Plastic.implementation.toString()}</td> : <td>-</td>}
            <td>{Plastic.lostPercentage}</td>
            <td>{Plastic.grammage}</td>

            <td><EditSvg onClick={() => openModal()}/></td>
            <td><RiShutDownLine onClick={() => inactivateObject(Plastic.code)}/></td>
            <td><DeleteSvg onClick={() => deleteObject(Plastic.code)}/></td>

            <PlasticModal Plastic={Plastic} isOpen={modalIsOpen} isEditing={true} onClose={closeModal} onDeleteOrEdit={() => onDeleteOrEdit()} />
        </tr>
    );
}

export default PlasticRow;