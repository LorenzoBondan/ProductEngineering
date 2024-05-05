import { DMDPSon, DPaintingSon, DSon } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import * as mdpSonService from 'services/MDP/mdpSonService';
import * as paintingSonService from 'services/MDF/paintingSonService';
import * as aluminiumSonService from 'services/Aluminium/aluminiumSonService';
import plusIcon from 'assets/images/plus.png';
import './styles.css';
import Row from "./MDPMaterials/Sheets/UsedSheetRow";
import UsedSheetModal from "./MDPMaterials/Sheets/UsedSheetModal";
import { IoMdAdd } from "react-icons/io";
import UsedSheetRow from "./MDPMaterials/Sheets/UsedSheetRow";
import UsedEdgeBandingRow from "./MDPMaterials/EdgeBandings/UsedEdgeBandingRow";
import UsedEdgeBandingModal from "./MDPMaterials/EdgeBandings/UsedEdgeBandingModal";

type UrlParams = {
    sonId: string;
}

const SonDetails = () => {

    const { sonId } = useParams<UrlParams>();
    const [son, setSon] = useState<DMDPSon>();
    const [ isMdpSon, setIsMdpSon] = useState<boolean>(false);
    const [ isPaintingSon, setIsPaintingSon] = useState<boolean>(false);
    const [ isAluminiumSon, setIsAluminiumSon] = useState<boolean>(false);

    const getSon = useCallback(() => {
        mdpSonService.findById(parseInt(sonId))
            .then(response => {
                setSon(response.data);
                setIsMdpSon(true);
            })
            .catch(() => {
                paintingSonService.findById(parseInt(sonId))
                    .then(response => {
                        setSon(response.data);
                        setIsPaintingSon(true);
                    })
                    .catch(() => {
                        aluminiumSonService.findById(parseInt(sonId))
                        .then(response => {
                            setSon(response.data);
                            setIsAluminiumSon(true);
                        })
                        .catch(error => console.log("Error: " + error));
                    });
            });
    }, [sonId]);

    useEffect(() => {
        getSon();
    }, [getSon]);

    // modal functions

    const [modalIsOpen, setIsOpen] = useState(false);

    const openModal = () => {
        setIsOpen(true);
    }
        
    const closeModal = () => {
        setIsOpen(false);
    }

    return(
        <div className="father-details-page">
            <div className='father-details-container base-card'>
                <div className='father-title crud-modal-title'>
                    <h4>{son?.code} - {son?.description}</h4>
                </div>
                
                {isMdpSon && 
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Chapas</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {son?.sheets.map(sheet => 
                                <>
                                    <UsedSheetRow usedSheet={sheet} onDeleteOrEdit={getSon} key={sheet.id} />
                                    <UsedSheetModal usedSheet={sheet} mdpSonCode={son.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            )}
                        </ul>
                    </div>
                }

                {isMdpSon && 
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Fitas Borda</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {son?.edgeBandings.map(edgeBanding => (
                                <>
                                <UsedEdgeBandingRow usedEdgeBanding={edgeBanding} onDeleteOrEdit={getSon} key={edgeBanding.id} />
                                <UsedEdgeBandingModal usedEdgeBanding={edgeBanding} mdpSonCode={son.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                            </>
                            ))}
                        </ul>
                    </div>
                }

                {isMdpSon && 
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Colas</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {son?.glues.map(glue => (
                                <li key={glue.id} className='son-list-item'>
                                    {glue.glueCode} - {glue.netQuantity} {glue.measurementUnit}
                                </li>
                            ))}
                        </ul>
                    </div>
                }

            </div>
        </div>
    );
}

export default SonDetails;