import { SheetDTO } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";

type Props = {
    sheet: SheetDTO;
}

const SheetRow = ({sheet} : Props) => {
    return(
        <tr>
            <td>{sheet.code}</td>
            <td>{sheet.description}</td>
            <td>{sheet.family}</td>
            <td>{sheet.faces}</td>
            <td>{sheet.thickness}</td>
            {sheet.implementation ? <td>{sheet.implementation.toUTCString()}</td> : <td>-</td>}
            <td>{sheet.lostPercentage}</td>
            {sheet.materialId ? <td>{sheet.materialId}</td> : <td>-</td>}
            {sheet.color ? <td>{sheet.color.name}</td> : <td>-</td>}
            <td style={{alignItems:"center"}}><EditSvg/></td>
            <td style={{alignItems:"center"}}><DeleteSvg/></td>
        </tr>
    );
}

export default SheetRow;