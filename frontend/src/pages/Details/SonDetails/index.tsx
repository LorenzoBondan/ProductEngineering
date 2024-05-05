import { DAluminiumSon, DMDPSon, DPaintingSon, DSon } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import * as mdpSonService from 'services/MDP/mdpSonService';
import * as paintingSonService from 'services/MDF/paintingSonService';
import * as aluminiumSonService from 'services/Aluminium/aluminiumSonService';
import checkedImg from 'assets/images/checked.png';
import uncheckedImg from 'assets/images/unchecked.png';
import './styles.css';
import UsedSheetModal from "./MDPMaterials/Sheets/UsedSheetModal";
import { IoMdAdd } from "react-icons/io";
import UsedSheetRow from "./MDPMaterials/Sheets/UsedSheetRow";
import UsedEdgeBandingRow from "./MDPMaterials/EdgeBandings/UsedEdgeBandingRow";
import UsedEdgeBandingModal from "./MDPMaterials/EdgeBandings/UsedEdgeBandingModal";
import UsedGlueRow from "./MDPMaterials/Glues/UsedGlueRow";
import UsedGlueModal from "./MDPMaterials/Glues/UsedGlueModal";
import UsedPaintingRow from "./MDFMaterials/Paintings/UsedPaintingRow";
import UsedPaintingModal from "./MDFMaterials/Paintings/UsedPaintingModal";

type UrlParams = {
    sonId: string;
}

const SonDetails = () => {

    const { sonId } = useParams<UrlParams>();
    const [ mdpSon, setMdpSon] = useState<DMDPSon>();
    const [ paintingSon, setPaintingSon] = useState<DPaintingSon>();
    const [ aluminiumSon, setAluminiumSon] = useState<DAluminiumSon>();
    const [ isMdpSon, setIsMdpSon] = useState<boolean>(false);
    const [ isPaintingSon, setIsPaintingSon] = useState<boolean>(false);
    const [ isAluminiumSon, setIsAluminiumSon] = useState<boolean>(false);

    const getSon = useCallback(() => {
        mdpSonService.findById(parseInt(sonId))
            .then(response => {
                setMdpSon(response.data);
                setIsMdpSon(true);
            })
            .catch(() => {
                paintingSonService.findById(parseInt(sonId))
                    .then(response => {
                        setPaintingSon(response.data);
                        setIsPaintingSon(true);
                    })
                    .catch(() => {
                        aluminiumSonService.findById(parseInt(sonId))
                        .then(response => {
                            setAluminiumSon(response.data);
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
                { /* MDP */}
                {isMdpSon && 
                    <>
                    <div className='father-title crud-modal-title'>
                        <h4>{mdpSon?.code} - {mdpSon?.description}</h4>
                    </div>

                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Chapas</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.sheets.map(sheet => 
                                <>
                                    <UsedSheetRow usedSheet={sheet} onDeleteOrEdit={getSon} key={sheet.id} />
                                    <UsedSheetModal usedSheet={sheet} mdpSonCode={mdpSon.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            )}
                        </ul>
                    </div>
                
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Fitas Borda</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.edgeBandings.map(edgeBanding => (
                                <>
                                    <UsedEdgeBandingRow usedEdgeBanding={edgeBanding} onDeleteOrEdit={getSon} key={edgeBanding.id} />
                                    <UsedEdgeBandingModal usedEdgeBanding={edgeBanding} mdpSonCode={mdpSon.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Colas</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.glues.map(glue => (
                                <>
                                    <UsedGlueRow usedGlue={glue} onDeleteOrEdit={getSon} key={glue.id} />
                                    <UsedGlueModal usedGlue={glue} mdpSonCode={mdpSon.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>

                    { /* Roteiro */}
                    </>
                }

                { /* MDF */}
                {isPaintingSon && 
                <>
                    <div className='father-title crud-modal-title'>
                        <h4>{paintingSon?.code} - {paintingSon?.description}</h4>
                    </div>
                    <div className="father-details">
                    {
                        paintingSon?.satin ? "Acetinada" :
                        paintingSon?.highShine ? "Alto Brilho" :
                        paintingSon?.satinGlass ? "Acetinada vidros" :
                        ""
                    }
                        <span>Faces: {paintingSon?.faces}</span>
                        <span className="special-span">Especial: {paintingSon?.special ? <img src={checkedImg} alt="" /> : <img src={uncheckedImg} alt="" />}</span>
                    </div>

                    { /* Fundo */}

                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Pinturas</h5>
                            <IoMdAdd onClick={openModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {paintingSon?.paintings.map(painting => (
                                <>
                                    <UsedPaintingRow usedPainting={painting} onDeleteOrEdit={getSon} key={painting.id} />
                                    <UsedPaintingModal usedPainting={painting} paintingSonCode={paintingSon.code} isOpen={modalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                </>
                }
                

            </div>
        </div>
    );
}

export default SonDetails;